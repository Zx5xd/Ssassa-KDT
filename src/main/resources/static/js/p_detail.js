const hiddenInput = document.querySelector('input[name="price"]');
const price = Number(hiddenInput.value);

let amount = 1;
const addAmountBtn = document.getElementById("add_amount");
const removeAmountBtn = document.getElementById("remove_amount");
const amountInput = document.getElementById("amount_input");
const totalPriceElem = document.getElementById("buy-total-price");

const reviewInput = document.getElementById('reviewInput');
const comp = document.getElementById('reviewPanel');

// ✅ 총 가격을 계산하고 표시하는 함수
const updateTotalPrice = () => {
    const total = price * amount;
    // 한국식 통화 형식으로 포맷
    totalPriceElem.textContent = total.toLocaleString('ko-KR') + "원";
};

// 버튼: 수량 증가
// 버튼: 수량 증가
addAmountBtn.addEventListener("click", () => {
    if (amount < 100) {
        amount++;
        amountInput.value = amount;
        updateTotalPrice();
    }
});

// 버튼: 수량 감소
removeAmountBtn.addEventListener("click", () => {
    if (amount > 1) {
        amount--;
        amountInput.value = amount;
        updateTotalPrice();
    }
});

// 직접 입력
amountInput.addEventListener("input", (e) => {
    let value = Number(e.target.value);

    if (isNaN(value) || value < 1) {
        value = 1;
    } else if (value > 100) {
        value = 100;
    }

    amountInput.value = value;
    amount = value;
    updateTotalPrice();
});

// 초기 표시
updateTotalPrice();

/** Review JS **/

// 현재 로그인한 사용자 정보를 가져오는 함수
const getCurrentUser = async () => {
    try {
        const response = await axios.get('/api/me');
        const userData = response.data;

        // 리뷰 입력 컴포넌트에 사용자 정보 설정
        if (reviewInput) {
            reviewInput.author = userData.nickname || userData.name || '사용자';
            reviewInput.userId = userData.id || userData.email; // ID가 있으면 ID, 없으면 이메일
        }
        // 리뷰 뷰어 컴포넌트에 현재 사용자 ID 설정 (수정/삭제 버튼 표시용)
        if (comp) {
            comp.currentUserId = userData.id || userData.email;
        }

        console.log('현재 사용자 정보:', userData);
        return userData;
    } catch (error) {
        if (error.response && error.response.status === 401) {
            console.log('로그인이 필요합니다.');
            // 로그인이 필요하지만 바로 리다이렉트하지 않고 무시
            return null;
        } else {
            console.error('사용자 정보 조회 중 오류 발생:', error);
        }
        return null;
    }
};

// 리뷰 목록을 불러오는 함수
const loadReviews = async (page = 0, size = 10) => {
    try {
        // URL에서 제품 ID와 변형 ID 추출
        const urlParts = window.location.pathname.split('/');
        const lastPart = urlParts[urlParts.length - 1] || '1';

        let pid, pvid;

        // 언더바가 존재하는지 확인
        if (lastPart.includes('_')) {
            const [pidPart, pvidPart] = lastPart.split('_');
            pid = parseInt(pidPart) || 1;
            pvid = parseInt(pvidPart) || -1;
        } else {
            // 언더바가 없으면 전체가 pid
            pid = parseInt(lastPart) || 1;
            pvid = -1; // 기본값
        }

        const response = await axios.get('/review/list', {
            params: {
                pid: pid,
                pvid: pvid,
                page: page,
                size: size
            }
        });

        if (response.status === 200) {
            const reviews = response.data;
            console.log('리뷰 목록 로드 성공:', reviews);

            // 항상 최신 사용자 정보로 comp.currentUserId를 갱신
            await getCurrentUser();

            // 리뷰 컴포넌트에 데이터 전달
            if (comp && reviews.content) {
                comp.comments = reviews.content;
                // 페이지네이션 정보도 설정 (필요한 경우)
                if (reviews.totalPages !== undefined) {
                    comp.totalPages = reviews.totalPages;
                }
                if (reviews.number !== undefined) {
                    comp.currentPage = reviews.number;
                }
            }

            return reviews;
        }
    } catch (error) {
        console.error('리뷰 목록 로드 중 오류 발생:', error);
        if (error.response && error.response.status === 404) {
            console.log('리뷰가 없습니다.');
        }
        return null;
    }
};

// 리뷰 입력 컴포넌트 설정 (기본값)
// reviewInput.author = '현재 사용자';
// reviewInput.userId = 1;

// specData를 테이블로 렌더링하는 함수
const renderSpecTable = (specData) => {
    const specContainer = document.getElementById('spec-container');
    if (!specContainer) return;

    let html = `
        <table class="spec-table">
            <tbody>
    `;

    // 모든 데이터를 하나의 테이블로 생성
    Object.entries(specData).forEach(([category, items]) => {
        // 카테고리 헤더 행 추가
        html += `
            <tr class="spec-category-row">
                <td class="spec-category-header" colspan="4">${category}</td>
            </tr>
        `;

        const itemsArray = Object.entries(items);

        // 2개씩 묶어서 행 생성
        for (let i = 0; i < itemsArray.length; i += 2) {
            const [key1, value1] = itemsArray[i];
            const [key2, value2] = itemsArray[i + 1] || ['', ''];

            html += `
                <tr class="spec-row">
                    <td class="spec-key">${key1}</td>
                    <td class="spec-value">${value1}</td>
                    <td class="spec-key">${key2}</td>
                    <td class="spec-value">${value2}</td>
                </tr>
            `;
        }
    });

    html += `
            </tbody>
        </table>
    `;

    specContainer.innerHTML = html;
};

window.addEventListener("DOMContentLoaded", async () => {
    // 페이지 로드 시 현재 사용자 정보 가져오기 (로그인 안되어있어도 무시)
    await getCurrentUser();

    // 페이지 로드 시 리뷰 목록 불러오기
    await loadReviews();

    // specData 렌더링 (전역 변수로 선언된 specData 사용)
    if (typeof specData !== 'undefined') {
        renderSpecTable(specData);
    }
})

// 리뷰 제출 이벤트 처리
reviewInput.addEventListener('review-submitted', async (e) => {
    const { type, content, imageUrls, author, userId } = e.detail;

    // 리뷰 제출 시 로그인 상태 확인
    try {
        const userData = await getCurrentUser();
        if (!userData) {
            // 로그인이 되어있지 않으면 로그인 페이지로 이동
            alert('리뷰를 작성하려면 로그인이 필요합니다.');
            const currentUrl = encodeURIComponent(window.location.href);
            window.location.href = `/login?redirect=${currentUrl}`;
            return;
        }

        // URL에서 제품 ID와 변형 ID 추출
        const urlParts = window.location.pathname.split('/');
        const lastPart = urlParts[urlParts.length - 1] || '1';

        let pid, pvid;
        if (lastPart.includes('_')) {
            const [pidPart, pvidPart] = lastPart.split('_');
            pid = parseInt(pidPart) || 1;
            pvid = parseInt(pvidPart) || -1;
        } else {
            pid = parseInt(lastPart) || 1;
            pvid = -1;
        }

        // FormData 생성
        const formData = new FormData();
        formData.append('type', type);
        formData.append('content', content);
        formData.append('pid', pid);
        formData.append('pvid', pvid);

        // 이미지 파일 추가
        if (imageUrls && imageUrls.length > 0) {
            const files = await convertImagesToFiles(imageUrls);
            if (files.length > 0) {
                files.forEach((file, index) => {
                    formData.append('images', file);
                });
            } else {
                console.warn('유효한 이미지 파일이 없습니다.');
            }
        }

        // 서버에 리뷰 제출
        const response = await axios.post('/review/submit', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });

        if (response.status === 200) {
            alert('성공적으로 등록되었습니다!');
            console.log('리뷰 등록 성공:', response.data);
            
            // 리뷰 목록 새로고침
            await loadReviews();
        }
    } catch (error) {
        console.error('리뷰 제출 중 오류 발생:', error);
        if (error.response && error.response.status === 401) {
            alert('로그인이 필요합니다.');
            const currentUrl = encodeURIComponent(window.location.href);
            window.location.href = `/login?redirect=${currentUrl}`;
        } else {
            alert('리뷰 등록 중 오류가 발생했습니다.');
        }
    }
});

// 초기 댓글 데이터 (서버에서 로드하므로 제거)
// let commentsData = [
//     {
//         id: 1,
//         author: '홍길동',
//         content: '이 제품 너무 좋아요!',
//         type: 'review',
//         createdAt: '2024-01-15T10:30:00Z',
//         images: [],
//         replies: []
//     },
//     {
//         id: 2,
//         author: '이몽룡',
//         content: '이거 호환되나요? mawkldml awdawdawdawdawwadwadwadwadwadawdawdwadawdwadawdwadawdawawmdlkm awlkmd alwkwmalkmwa dmalkwmd dla',
//         type: 'question',
//         createdAt: '2024-01-14T15:45:00Z',
//         images: [],
//         replies: [
//             {
//                 id: 3,
//                 author: '성춘향',
//                 content: '네 호환 잘 됩니다.',
//                 type: 'answer',
//                 createdAt: '2024-01-14T16:20:00Z',
//                 images: [],
//                 replies: []
//             }
//         ]
//     }
// ];

// 댓글 데이터를 컴포넌트에 설정 (서버에서 로드하므로 제거)
// comp.comments = commentsData;
// comp.currentUserId = 1;

comp.addEventListener('request-reply-open', async e => {
    const { commentId } = e.detail;

    try {
        // 서버에 로그인 상태 확인 요청
        const response = await axios.get('/api/me', {
            withCredentials: true // 쿠키 포함
        });

        if (response.data) {
            comp.allowReply(commentId);
        } else {
            if (confirm('로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?')) {
                window.location.href = '/login';
            }
        }
    } catch (error) {
        console.error('로그인 상태 확인 중 오류:', error);
        if (confirm('로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?')) {
            window.location.href = '/login';
        }
    }
});

comp.addEventListener('request-reply-close', e => {
    const { commentId } = e.detail;
    comp.closeReply(commentId);
});

comp.addEventListener('reply-submitted', async (e) => {
    const { commentId, content, images } = e.detail;

    try {
        // 로그인 상태 확인
        const userData = await getCurrentUser();
        if (!userData) {
            alert('답변을 작성하려면 로그인이 필요합니다.');
            const currentUrl = encodeURIComponent(window.location.href);
            window.location.href = `/login?redirect=${currentUrl}`;
            return;
        }

        // URL에서 제품 ID와 변형 ID 추출
        const urlParts = window.location.pathname.split('/');
        const lastPart = urlParts[urlParts.length - 1] || '1';

        let pid, pvid;
        if (lastPart.includes('_')) {
            const [pidPart, pvidPart] = lastPart.split('_');
            pid = parseInt(pidPart) || 1;
            pvid = parseInt(pvidPart) || -1;
        } else {
            pid = parseInt(lastPart) || 1;
            pvid = -1;
        }

        // FormData 생성 (답변용)
        const formData = new FormData();
        formData.append('type', 'answer');
        formData.append('content', content);
        formData.append('pid', pid);
        formData.append('pvid', pvid);
        formData.append('parentReviewId', commentId); // 부모 리뷰 ID 추가

        // 이미지 파일 추가
        if (images && images.length > 0) {
            const files = await convertImagesToFiles(images);
            if (files.length > 0) {
                files.forEach((file, index) => {
                    formData.append('images', file);
                });
            } else {
                console.warn('유효한 이미지 파일이 없습니다.');
            }
        }

        // 서버에 답변 제출
        const response = await axios.post('/review/submit', formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });

        if (response.status === 200) {
            alert('답글이 성공적으로 등록되었습니다!');
            console.log('답변 등록 성공:', response.data);
            
            // 리뷰 목록 새로고침
            await loadReviews();
        }
    } catch (error) {
        console.error('답변 제출 중 오류 발생:', error);
        if (error.response && error.response.status === 401) {
            alert('로그인이 필요합니다.');
            const currentUrl = encodeURIComponent(window.location.href);
            window.location.href = `/login?redirect=${currentUrl}`;
        } else {
            alert('답변 등록 중 오류가 발생했습니다.');
        }
    }
});

// blob URL을 File 객체로 변환하는 함수
const blobUrlToFile = async (blobUrl) => {
    try {
        const response = await fetch(blobUrl);
        const blob = await response.blob();
        return new File([blob], 'image.jpg', { type: blob.type || 'image/jpeg' });
    } catch (error) {
        console.error('blob URL을 File로 변환 중 오류:', error);
        return null;
    }
};

// 이미지 배열을 File 객체 배열로 변환하는 함수
const convertImagesToFiles = async (imageUrls) => {
    if (!imageUrls || imageUrls.length === 0) {
        return [];
    }
    
    const files = [];
    for (const imageUrl of imageUrls) {
        if (imageUrl instanceof File) {
            files.push(imageUrl);
        } else if (typeof imageUrl === 'string' && imageUrl.startsWith('blob:')) {
            const file = await blobUrlToFile(imageUrl);
            if (file) {
                files.push(file);
            }
        }
    }
    return files;
};