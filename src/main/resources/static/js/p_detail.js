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
        reviewInput.author = userData.nickname;
        reviewInput.userId = userData.email; // 또는 적절한 사용자 ID 필드

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

window.addEventListener("DOMContentLoaded", () => {
    // 페이지 로드 시 현재 사용자 정보 가져오기 (로그인 안되어있어도 무시)
    getCurrentUser();

    // 페이지 로드 시 리뷰 목록 불러오기
    loadReviews();

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

        // 로그인이 되어있으면 리뷰 또는 질문 추가
        let newId;
        if (type === 'review') {
            newId = comp.addReview(author, content, imageUrls, userId);
        } else {
            newId = comp.addQuestion(author, content, imageUrls, userId);
        }

        alert('성공적으로 등록되었습니다!');
        console.log('새로운 ' + (type === 'review' ? '리뷰' : '질문') + ' 등록:', { newId, content, imageUrls });
    } catch (error) {
        console.error('리뷰 제출 중 오류 발생:', error);
        alert('리뷰 등록 중 오류가 발생했습니다.');
    }
});

// 초기 댓글 데이터 (변수로 분리하여 수정 가능하게 함)
let commentsData = [
    {
        id: 1,
        author: '홍길동',
        content: '이 제품 너무 좋아요!',
        type: 'review',
        createdAt: '2024-01-15T10:30:00Z',
        images: [],
        replies: []
    },
    {
        id: 2,
        author: '이몽룡',
        content: '이거 호환되나요? mawkldml awdawdawdawdawwadwadwadwadwadawdawdwadawdwadawdwadawdawawmdlkm awlkmd alwkwmalkmwa dmalkwmd dla',
        type: 'question',
        createdAt: '2024-01-14T15:45:00Z',
        images: [],
        replies: [
            {
                id: 3,
                author: '성춘향',
                content: '네 호환 잘 됩니다.',
                type: 'answer',
                createdAt: '2024-01-14T16:20:00Z',
                images: [],
                replies: []
            }
        ]
    }
];

// 댓글 데이터를 컴포넌트에 설정
comp.comments = commentsData;
comp.currentUserId = 1;

comp.addEventListener('request-reply-open', async e => {
    const { commentId } = e.detail;

    try {
        // 서버에 로그인 상태 확인 요청
        const response = await axios.get('/api/auth/check-login', {
            withCredentials: true // 쿠키 포함
        });

        if (response.data.isLoggedIn) {
            comp.allowReply(commentId);
        } else {
            if (confirm('로그인이 필요합니다. 로그인 페이지로 이동하시겠습니까?')) {
                window.location.href = '/login';
            }
        }
    } catch (error) {
        console.error('로그인 상태 확인 중 오류:', error);
        alert('로그인 상태를 확인할 수 없습니다. 다시 시도해주세요.');
    }
});

comp.addEventListener('request-reply-close', e => {
    const { commentId } = e.detail;
    comp.closeReply(commentId);
});

comp.addEventListener('reply-submitted', e => {
    const { commentId, content, images } = e.detail;

    // 새로운 답글 ID 생성 (실제로는 서버에서 생성)
    const newReplyId = Date.now();

    // 이미지 URL 배열 생성 (실제로는 서버에 업로드 후 URL을 받아와야 함)
    const imageUrls = images.map((file, index) => {
        // 실제로는 서버에 업로드하고 URL을 받아와야 함
        // 여기서는 임시로 File URL을 사용
        return URL.createObjectURL(file);
    });

    // 답글 데이터 생성
    const newReply = {
        id: newReplyId,
        author: '현재 사용자', // 실제로는 로그인된 사용자 정보
        userId: comp.currentUserId,
        content: content,
        type: 'answer',
        createdAt: new Date().toISOString(),
        images: imageUrls,
        replies: []
    };

    // 해당 댓글에 답글 추가
    const commentIndex = comp.comments.findIndex(comment => comment.id === commentId);
    if (commentIndex !== -1) {
        comp.comments[commentIndex].replies.push(newReply);

        // 컴포넌트 데이터 업데이트 (새로운 배열로 설정하여 리렌더링 트리거)
        comp.comments = [...comp.comments];

        // 성공 메시지
        alert('답글이 성공적으로 등록되었습니다!');
        console.log('답글 등록 완료:', {
            commentId,
            content,
            images,
            newReply
        });
    } else {
        alert('댓글을 찾을 수 없습니다.');
        console.error('댓글을 찾을 수 없음:', { commentId, comments: comp.comments });
    }
});