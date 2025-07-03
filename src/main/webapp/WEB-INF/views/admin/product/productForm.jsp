<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="ko">

        <head>
            <meta charset="UTF-8">
            <title>상품 등록 - 싸싸</title>
            <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
            <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
            <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/admin.css">
            <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/common.css">
        </head>

        <body class="noto-sans-kr-regular">
            <main>
                <div class="admin-container">
                    <a href="/pd/get/products" class="back-link">← 상품 관리로 돌아가기</a>

                    <h2 class="admin-title">
                        <span class="material-symbols-outlined">add_shopping_cart</span>
                        상품 등록
                    </h2>

                    <div class="form-container">
                        <!-- 등록 방식 선택 -->
                        <div class="registration-type-selector">
                            <label class="registration-type-label">등록 방식 선택:</label>
                            <div class="radio-group">
                                <label class="radio-label">
                                    <input type="radio" name="registrationType" value="single" checked>
                                    <span class="radio-text">단개 등록</span>
                                </label>
                                <label class="radio-label">
                                    <input type="radio" name="registrationType" value="multiple">
                                    <span class="radio-text">여러개 등록</span>
                                </label>
                            </div>
                        </div>

                        <form action="/pd/set/product/create" method="post" enctype="multipart/form-data"
                            class="product-form">
                            <!-- 등록 방식 히든 필드 -->
                            <input type="hidden" id="registrationTypeField" name="registrationType" value="single">
                            
                            <div class="form-group">
                                <label for="name">상품명 *</label>
                                <input type="text" id="name" name="name" required class="form-input">
                            </div>

                            <div class="form-group">
                                <label for="categoryId">카테고리 *</label>
                                <select id="categoryId" name="strCategoryId" required class="form-select">
                                    <c:if test="${product == null}">
                                        <option value="">카테고리를 선택하세요</option>
                                        <c:forEach items="${category}" var="cat">
                                            <option value="${cat.id}">${cat.name}</option>
                                        </c:forEach>
                                    </c:if>
                                </select>
                            </div>

                            <div class="form-group" id="categoryChildGroup" style="display: none;">
                                <label for="categoryChildId">세부 카테고리 *</label>
                                <select id="categoryChildId" name="strCategoryChildId" class="form-select">
                                    <option value="">세부 카테고리를 선택하세요</option>
                                </select>
                            </div>

                            <div class="form-group">
                                <label for="price">가격 *</label>
                                <input type="number" id="price" name="price" required class="form-input" min="0">
                            </div>

                            <div class="form-group" id="amountGroup">
                                <label for="amount">재고 수량 *</label>
                                <input type="number" id="amount" name="amount" required class="form-input" min="0">
                            </div>

                            <div class="form-group">
                                <label for="simpleImg">상품 이미지</label>
                                <input type="file" id="simpleImg" name="simpleImg" accept="image/*" class="form-input">
                            </div>

                            <div class="form-group" id="detailImgGroup">
                                <label for="detailImg">상세 이미지</label>
                                <input type="file" id="detailImg" name="detailImg" accept="image/*" class="form-input">
                            </div>

                            <div class="form-group">
                                <label for="reg">상품 등록일 *</label>
                                <input type="date" id="reg" name="reg" required class="form-input">
                            </div>

                            <div class="form-group" id="detailGroup">
                                <label for="detail">상품 상세 정보</label>
                                <textarea id="detail" name="detail" rows="5" class="form-textarea"
                                    placeholder="상품 상세 정보를 JSON 형식으로 입력하세요"></textarea>
                            </div>

                            <!-- 여러개 등록 시 추가 버튼 -->
                            <div class="form-group" id="addVariantGroup" style="display: none;">
                                <button type="button" id="addVariantBtn" class="add-variant-btn">
                                    <span class="material-symbols-outlined">add</span>
                                    상품 변형 추가
                                </button>
                            </div>

                            <!-- 상품 변형 컨테이너 -->
                            <div id="variantsContainer"></div>

                            <div class="form-actions">
                                <button type="submit" class="submit-btn">
                                    <span class="material-symbols-outlined">save</span>
                                    상품 등록
                                </button>
                                <a href="/pd/get/products" class="cancel-btn">취소</a>
                            </div>
                        </form>
                    </div>
                </div>
            </main>

            <style>
                .form-container {
                    background-color: #2a2a2a;
                    border-radius: 8px;
                    padding: 30px;
                    margin-top: 20px;
                }

                .registration-type-selector {
                    margin-bottom: 30px;
                    padding: 20px;
                    background-color: #333;
                    border-radius: 6px;
                    border: 1px solid #444;
                }

                .registration-type-label {
                    display: block;
                    margin-bottom: 15px;
                    font-weight: bold;
                    color: #fff;
                    font-size: 16px;
                }

                .radio-group {
                    display: flex;
                    gap: 30px;
                }

                .radio-label {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    cursor: pointer;
                }

                .radio-label input[type="radio"] {
                    width: 18px;
                    height: 18px;
                    accent-color: #007bff;
                }

                .radio-text {
                    color: #fff;
                    font-size: 14px;
                }

                .product-form {
                    max-width: 600px;
                }

                .form-group {
                    margin-bottom: 20px;
                }

                .form-group label {
                    display: block;
                    margin-bottom: 8px;
                    font-weight: bold;
                    color: #fff;
                }

                .form-input,
                .form-select,
                .form-textarea {
                    width: 100%;
                    padding: 12px;
                    border: 1px solid #444;
                    border-radius: 6px;
                    background-color: #333;
                    color: #fff;
                    font-size: 14px;
                }

                .form-input:focus,
                .form-select:focus,
                .form-textarea:focus {
                    outline: none;
                    border-color: #007bff;
                    box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
                }

                .form-input:read-only {
                    background-color: #555;
                    color: #ccc;
                    cursor: not-allowed;
                }

                .form-textarea {
                    resize: vertical;
                    min-height: 100px;
                }

                .add-variant-btn {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    padding: 12px 20px;
                    background-color: #007bff;
                    color: white;
                    border: none;
                    border-radius: 6px;
                    cursor: pointer;
                    font-size: 14px;
                    transition: background-color 0.3s;
                }

                .add-variant-btn:hover {
                    background-color: #0056b3;
                }

                .variant-group {
                    background-color: #333;
                    border: 1px solid #555;
                    border-radius: 6px;
                    padding: 20px;
                    margin-bottom: 20px;
                    position: relative;
                }

                .variant-header {
                    display: flex;
                    justify-content: space-between;
                    align-items: center;
                    margin-bottom: 15px;
                    padding-bottom: 10px;
                    border-bottom: 1px solid #555;
                }

                .variant-title {
                    color: #fff;
                    font-weight: bold;
                    font-size: 16px;
                }

                .remove-variant-btn {
                    background-color: #dc3545;
                    color: white;
                    border: none;
                    border-radius: 4px;
                    padding: 6px 12px;
                    cursor: pointer;
                    font-size: 12px;
                    transition: background-color 0.3s;
                }

                .remove-variant-btn:hover {
                    background-color: #c82333;
                }

                .form-actions {
                    display: flex;
                    gap: 15px;
                    margin-top: 30px;
                }

                .submit-btn {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    padding: 12px 24px;
                    background-color: #28a745;
                    color: white;
                    border: none;
                    border-radius: 6px;
                    cursor: pointer;
                    font-size: 16px;
                    transition: background-color 0.3s;
                }

                .submit-btn:hover {
                    background-color: #218838;
                }

                .cancel-btn {
                    display: flex;
                    align-items: center;
                    gap: 8px;
                    padding: 12px 24px;
                    background-color: #6c757d;
                    color: white;
                    text-decoration: none;
                    border-radius: 6px;
                    font-size: 16px;
                    transition: background-color 0.3s;
                }

                .cancel-btn:hover {
                    background-color: #5a6268;
                    color: white;
                }

                .form-input[type="file"] {
                    padding: 8px;
                }

                .hidden {
                    display: none !important;
                }
            </style>

            <script>
                let variantCount = 0;
                
                // 페이지 로드 시 초기화
                document.addEventListener('DOMContentLoaded', function() {
                    // 기본 등록 방식 설정
                    const defaultType = document.querySelector('input[name="registrationType"]:checked').value;
                    const hiddenField = document.getElementById('registrationTypeField');
                    if (hiddenField) {
                        hiddenField.value = defaultType;
                    }
                });

                // 등록 방식 변경 이벤트
                document.querySelectorAll('input[name="registrationType"]').forEach(radio => {
                    radio.addEventListener('change', function() {
                        const isMultiple = this.value === 'multiple';
                        toggleRegistrationMode(isMultiple);
                        
                        // 히든 필드 업데이트
                        const hiddenField = document.getElementById('registrationTypeField');
                        if (hiddenField) {
                            hiddenField.value = this.value;
                        }
                    });
                });

                // 폼 제출 시 처리
                document.querySelector('.product-form').addEventListener('submit', function(e) {
                    const registrationType = document.querySelector('input[name="registrationType"]:checked').value;
                    
                    if (registrationType === 'multiple') {
                        // 여러개 등록 시 숨겨진 필드들 비활성화
                        const amountInput = document.getElementById('amount');
                        const detailImgInput = document.getElementById('detailImg');
                        const detailTextarea = document.getElementById('detail');
                        
                        if (amountInput) amountInput.disabled = true;
                        if (detailImgInput) detailImgInput.disabled = true;
                        if (detailTextarea) detailTextarea.disabled = true;
                        
                        // 여러개 등록 시 최소 하나의 변형이 필요
                        const variants = document.querySelectorAll('.variant-group');
                        if (variants.length === 0) {
                            e.preventDefault();
                            alert('여러개 등록 시에는 최소 하나의 상품 변형을 추가해야 합니다.');
                            return;
                        }
                        
                        // 변형들의 필수 필드 검증
                        let isValid = true;
                        variants.forEach((variant, index) => {
                            const nameInput = variant.querySelector('input[name^="variants"][name$=".name"]');
                            const priceInput = variant.querySelector('input[name^="variants"][name$=".price"]');
                            const amountInput = variant.querySelector('input[name^="variants"][name$=".amount"]');
                            
                            if (!nameInput.value.trim()) {
                                alert(`상품 변형 ${index + 1}의 상세 상품명을 입력해주세요.`);
                                isValid = false;
                                return;
                            }
                            
                            if (!priceInput.value || parseInt(priceInput.value) <= 0) {
                                alert(`상품 변형 ${index + 1}의 가격을 입력해주세요.`);
                                isValid = false;
                                return;
                            }
                            
                            if (!amountInput.value || parseInt(amountInput.value) < 0) {
                                alert(`상품 변형 ${index + 1}의 재고 수량을 입력해주세요.`);
                                isValid = false;
                                return;
                            }
                        });
                        
                        if (!isValid) {
                            e.preventDefault();
                            return;
                        }
                    } else {
                        // 단개 등록 시 variants 필드들 제거 (Spring 바인딩 오류 방지)
                        const variantInputs = document.querySelectorAll('input[name^="variants"], textarea[name^="variants"]');
                        variantInputs.forEach(input => {
                            input.disabled = true;
                        });
                    }
                });

                function toggleRegistrationMode(isMultiple) {
                    const amountGroup = document.getElementById('amountGroup');
                    const detailImgGroup = document.getElementById('detailImgGroup');
                    const detailGroup = document.getElementById('detailGroup');
                    const addVariantGroup = document.getElementById('addVariantGroup');
                    const priceInput = document.getElementById('price');
                    const variantsContainer = document.getElementById('variantsContainer');
                    
                    // 필드들 가져오기
                    const amountInput = document.getElementById('amount');
                    const detailImgInput = document.getElementById('detailImg');
                    const detailTextarea = document.getElementById('detail');

                    if (isMultiple) {
                        // 여러개 등록 모드
                        amountGroup.classList.add('hidden');
                        detailImgGroup.classList.add('hidden');
                        detailGroup.classList.add('hidden');
                        addVariantGroup.style.display = 'block';
                        
                        // 가격을 0으로 고정
                        priceInput.value = '0';
                        priceInput.readOnly = true;
                        
                        // 숨겨진 필드들의 required 속성 제거
                        if (amountInput) amountInput.required = false;
                        if (detailImgInput) detailImgInput.required = false;
                        if (detailTextarea) detailTextarea.required = false;
                        
                        // 기존 변형들 제거
                        variantsContainer.innerHTML = '';
                        variantCount = 0;
                        
                        // 기존 variants 필드들 비활성화
                        const existingVariantInputs = document.querySelectorAll('input[name^="variants"], textarea[name^="variants"]');
                        existingVariantInputs.forEach(input => {
                            input.disabled = true;
                        });
                    } else {
                        // 단개 등록 모드
                        amountGroup.classList.remove('hidden');
                        detailImgGroup.classList.remove('hidden');
                        detailGroup.classList.remove('hidden');
                        addVariantGroup.style.display = 'none';
                        
                        // 가격 입력 가능하게
                        priceInput.readOnly = false;
                        
                        // 숨겨진 필드들의 required 속성 복원
                        if (amountInput) amountInput.required = true;
                        if (detailImgInput) detailImgInput.required = false; // 상세 이미지는 선택사항
                        if (detailTextarea) detailTextarea.required = false; // 상세 정보는 선택사항
                        
                        // 변형들 제거
                        variantsContainer.innerHTML = '';
                        variantCount = 0;
                        
                        // 기존 variants 필드들 비활성화
                        const existingVariantInputs = document.querySelectorAll('input[name^="variants"], textarea[name^="variants"]');
                        existingVariantInputs.forEach(input => {
                            input.disabled = true;
                        });
                    }
                }

                // 상품 변형 추가 버튼
                document.getElementById('addVariantBtn').addEventListener('click', function() {
                    addVariantGroup();
                });

                function addVariantGroup() {
                    variantCount++;
                    console.log("variantCount : " + variantCount);
                    console.log("variants[" + (variantCount-1) + "] : variants[" + (variantCount-1) + "]");
                    const container = document.getElementById('variantsContainer');
                    
                    const variantGroup = document.createElement('div');
                    variantGroup.className = 'variant-group';
                    variantGroup.innerHTML = 
                        '<div class="variant-header">' +
                            '<span class="variant-title">상품 변형 ' + variantCount + '</span>' +
                            '<button type="button" class="remove-variant-btn" onclick="removeVariant(this)">' +
                                '<span class="material-symbols-outlined">delete</span>' +
                            '</button>' +
                        '</div>' +
                        '<div class="form-group">' +
                            '<label for="variantName' + variantCount + '">상세 상품명 *</label>' +
                            '<input type="text" id="variantName' + variantCount + '" name="variants[' + (variantCount-1) + '].name" required class="form-input">' +
                        '</div>' +
                        '<div class="form-group">' +
                            '<label for="variantPrice' + variantCount + '">가격 *</label>' +
                            '<input type="number" id="variantPrice' + variantCount + '" name="variants[' + (variantCount-1) + '].price" required class="form-input" min="0">' +
                        '</div>' +
                        '<div class="form-group">' +
                            '<label for="variantAmount' + variantCount + '">재고 수량 *</label>' +
                            '<input type="number" id="variantAmount' + variantCount + '" name="variants[' + (variantCount-1) + '].amount" required class="form-input" min="0">' +
                        '</div>' +
                        '<div class="form-group">' +
                            '<label for="variantDetailImg' + variantCount + '">상세 이미지</label>' +
                            '<input type="file" id="variantDetailImg' + variantCount + '" name="variants[' + (variantCount-1) + '].detailImgFile" accept="image/*" class="form-input">' +
                        '</div>' +
                        '<div class="form-group">' +
                            '<label for="variantDetail' + variantCount + '">상품 상세 정보</label>' +
                            '<textarea id="variantDetail' + variantCount + '" name="variants[' + (variantCount-1) + '].detailString" rows="3" class="form-textarea" placeholder="상품 상세 정보를 JSON 형식으로 입력하세요"></textarea>' +
                        '</div>';
                    
                    container.appendChild(variantGroup);
                }

                function removeVariant(button) {
                    const variantGroup = button.closest('.variant-group');
                    variantGroup.remove();
                    
                    // 번호 재정렬 및 배열 인덱스 업데이트
                    const variantGroups = document.querySelectorAll('.variant-group');
                    variantGroups.forEach((group, index) => {
                        const title = group.querySelector('.variant-title');
                        title.textContent = `상품 변형 ${index + 1}`;
                        
                        // 배열 인덱스 업데이트
                        const inputs = group.querySelectorAll('input, textarea');
                        inputs.forEach(input => {
                            const name = input.name;
                            if (name && name.includes('variants[')) {
                                input.name = name.replace(/variants\[\d+\]/, `variants[${index}]`);
                            }
                        });
                    });
                    variantCount = variantGroups.length;
                }

                // 카테고리 선택 시 세부 카테고리 로드
                document.getElementById('categoryId').addEventListener('change', function () {
                    const categoryId = parseInt(this.value);
                    const childGroup = document.getElementById('categoryChildGroup');
                    const childSelect = document.getElementById('categoryChildId');

                    console.log('카테고리 선택됨:', categoryId, '타입:', typeof categoryId, '원본값:', this.value);

                    // 세부 카테고리 필드 숨기기
                    childGroup.style.display = 'none';
                    childSelect.innerHTML = '<option value="">세부 카테고리를 선택하세요</option>';

                    // categoryId가 유효한지 확인
                    if (!categoryId || isNaN(categoryId)) {
                        console.log('유효하지 않은 categoryId:', categoryId);
                        childSelect.required = false;
                        return;
                    }

                    // 카테고리 5, 8, 9번인 경우에만 세부 카테고리 표시
                    if (categoryId === 5 || categoryId === 8 || categoryId === 9) {
                        console.log('세부 카테고리 로드 시작:', categoryId);

                        const apiUrl = '/admin/api/categories/' + categoryId + '/children';
                        console.log('API URL:', apiUrl);

                        // AJAX로 세부 카테고리 로드
                        fetch(apiUrl)
                            .then(response => {
                                console.log('API 응답 상태:', response.status);
                                if (!response.ok) {
                                    throw new Error(`HTTP error! status: ${response.status}`);
                                }
                                return response.json();
                            })
                            .then(data => {
                                console.log('받은 데이터:', data);
                                if (data && data.length > 0) {

                                    data.forEach(child => {
                                        const option = document.createElement('option');
                                        option.value = child.id;
                                        option.textContent = child.name;
                                        childSelect.appendChild(option);
                                    });


                                    // 세부 카테고리 필드 표시
                                    childGroup.style.display = 'block';
                                    childSelect.required = true;
                                    console.log('세부 카테고리 로드 완료');
                                } else {
                                    console.log('세부 카테고리 데이터가 없습니다');
                                }
                            })
                            .catch(error => {
                                console.error('세부 카테고리 로드 실패:', error);
                            });


                    } else {
                        console.log('세부 카테고리가 필요하지 않은 카테고리:', categoryId);
                        // 다른 카테고리의 경우 세부 카테고리 필수 해제
                        childSelect.required = false;
                    }
                });
            </script>
        </body>

        </html>