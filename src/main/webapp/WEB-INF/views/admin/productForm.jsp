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
                        <form action="/pd/set/product/create" method="post" enctype="multipart/form-data"
                            class="product-form">
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

                            <div class="form-group">
                                <label for="amount">재고 수량 *</label>
                                <input type="number" id="amount" name="amount" required class="form-input" min="0">
                            </div>

                            <div class="form-group">
                                <label for="simpleImg">상품 이미지</label>
                                <input type="file" id="simpleImg" name="simpleImg" accept="image/*" class="form-input">
                            </div>

                            <div class="form-group">
                                <label for="detailImg">상세 이미지</label>
                                <input type="file" id="detailImg" name="detailImg" accept="image/*" class="form-input">
                            </div>

                            <div class="form-group">
                                <label for="reg">상품 등록일 *</label>
                                <input type="date" id="reg" name="reg" required class="form-input">
                            </div>

                            <div class="form-group">
                                <label for="detail">상품 상세 정보</label>
                                <textarea id="detail" name="detail" rows="5" class="form-textarea"
                                    placeholder="상품 상세 정보를 JSON 형식으로 입력하세요"></textarea>
                            </div>

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

                .form-textarea {
                    resize: vertical;
                    min-height: 100px;
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
            </style>

            <script>
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