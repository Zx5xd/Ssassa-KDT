<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="ko">

        <head>
            <meta charset="UTF-8">
            <title>상품 수정 - 싸싸</title>
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
                        <span class="material-symbols-outlined">edit</span>
                        상품 수정
                    </h2>

                    <div class="form-container">
                        <form action="/pd/set/product/update/${product.id}" method="post" enctype="multipart/form-data"
                            class="product-form">
                            
                            <div class="form-group">
                                <label for="name">상품명 *</label>
                                <input type="text" id="name" name="name" required class="form-input"
                                    value="<c:out value='${product.name}'/>">
                            </div>

                            <div class="form-group">
                                <label for="categoryId">카테고리 *</label>
                                <select id="categoryId" name="strCategoryId" required class="form-select">
                                    <c:forEach items="${category}" var="cat">
                                        <option value="${cat.id}" <c:if test='${cat.id == product.categoryId}'>selected
                                            </c:if>>${cat.name}</option>
                                    </c:forEach>
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
                                <input type="number" id="price" name="price" required class="form-input" min="0"
                                    value="<c:out value='${product.price}'/>">
                            </div>

                            <div class="form-group" id="amountGroup">
                                <label for="amount">재고 수량 *</label>
                                <input type="number" id="amount" name="amount" required class="form-input" min="0"
                                    value="<c:out value='${product.amount}'/>">
                            </div>

                            <div class="form-group">
                                <label for="simpleImg">상품 이미지</label>
                                <input type="file" id="simpleImg" name="simpleImg" accept="image/*" class="form-input">
                                <c:if test="${product.simpleImg != 0}">
                                    <img src="${productImgCache.getImageUrl(product.simpleImg)}"
                                        alt="${product.simpleImg}" style="width: 100px; height: 100px;">
                                </c:if>
                            </div>

                            <div class="form-group" id="detailImgGroup">
                                <label for="detailImg">상세 이미지</label>
                                <input type="file" id="detailImg" name="detailImg" accept="image/*" class="form-input">
                                <input type="hidden" name="existingDetailImg" value="${product.detailImg}">
                                <c:if test="${product.detailImg != 0}">
                                    <img src="${productImgCache.getImageUrl(product.detailImg)}"
                                        alt="${product.detailImg}" style="width: 100px; height: 100px;">
                                </c:if>
                            </div>

                            <div class="form-group">
                                <label for="reg">상품 등록일 *</label>
                                <input type="date" id="reg" name="reg" required class="form-input" value="${regStr}">
                            </div>

                            <div class="form-group">
                                <label for="detail">상품 상세 정보</label>
                                <textarea id="detail" name="detail" rows="5" class="form-textarea"
                                    placeholder="상품 상세 정보를 JSON 형식으로 입력하세요"><c:out value='${product.detail}'/></textarea>
                            </div>

                            <!-- 상품 변형 섹션 (price가 0이거나 detailImg가 0일 때만 표시) -->
                            <c:if test="${product.price == 0 || product.detailImg == 0}">
                                <!-- 변형 추가 버튼 -->
                                <div class="form-group" id="addVariantGroup">
                                    <button type="button" id="addVariantBtn" class="add-variant-btn">
                                        <span class="material-symbols-outlined">add</span>
                                        상품 변형 추가
                                    </button>
                                </div>

                                <!-- 상품 변형 컨테이너 -->
                                <div id="variantsContainer">
                                    <!-- 기존 변형들 표시 -->
                                    <c:forEach items="${existingVariants}" var="variant" varStatus="status">
                                        <div class="variant-group">
                                            <input type="hidden" name="variants[${status.index}].id" value="${variant.id}">
                                            <div class="variant-header">
                                                <span class="variant-title">상품 변형 ${status.index + 1}</span>
                                                <button type="button" class="remove-variant-btn" onclick="removeVariant(this)">
                                                    <span class="material-symbols-outlined">delete</span>
                                                </button>
                                            </div>
                                            <div class="form-group">
                                                <label for="variantName${status.index + 1}">상세 상품명 *</label>
                                                <input type="text" id="variantName${status.index + 1}" name="variants[${status.index}].name" required class="form-input" value="<c:out value='${variant.name}'/>">
                                            </div>
                                            <div class="form-group">
                                                <label for="variantPrice${status.index + 1}">가격 *</label>
                                                <input type="number" id="variantPrice${status.index + 1}" name="variants[${status.index}].price" required class="form-input" min="0" value="<c:out value='${variant.price}'/>">
                                            </div>
                                            <div class="form-group">
                                                <label for="variantAmount${status.index + 1}">재고 수량 *</label>
                                                <input type="number" id="variantAmount${status.index + 1}" name="variants[${status.index}].amount" required class="form-input" min="0" value="<c:out value='${variant.amount}'/>">
                                            </div>
                                            <div class="form-group">
                                                <label for="variantDetailImg${status.index + 1}">상세 이미지</label>
                                                <input type="file" id="variantDetailImg${status.index + 1}" name="variants[${status.index}].detailImgFile" accept="image/*" class="form-input">
                                                <c:if test="${variant.detailImg != 0}">
                                                    <input type="hidden" name="variants[${status.index}].existingDetailImg" value="${variant.detailImg}">
                                                    <img src="${productImgCache.getImageUrl(variant.detailImg)}"
                                                        alt="${variant.detailImg}" style="width: 100px; height: 100px;">
                                                </c:if>
                                            </div>
                                            <div class="form-group">
                                                <label for="variantDetail${status.index + 1}">상품 상세 정보</label>
                                                <textarea id="variantDetail${status.index + 1}" name="variants[${status.index}].detailString" rows="3" class="form-textarea" placeholder="상품 상세 정보를 JSON 형식으로 입력하세요"><c:out value='${variant.detail}'/></textarea>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                            </c:if>

                            <div class="form-actions">
                                <button type="submit" class="submit-btn">
                                    <span class="material-symbols-outlined">save</span>
                                    상품 수정
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
            </style>

            <script>
                let variantCount = 0;
                
                // JSP에서 전달된 기존 변형 개수 설정
                <c:if test="${existingVariants != null}">
                    variantCount = ${existingVariants.size()};
                </c:if>
                
                // 페이지 로드 시 초기화
                document.addEventListener('DOMContentLoaded', function() {
                    // 변형 추가 버튼 이벤트 리스너 추가
                    const addVariantBtn = document.getElementById('addVariantBtn');
                    if (addVariantBtn) {
                        addVariantBtn.addEventListener('click', function() {
                            addVariantGroup();
                        });
                    }
                });

                // 상품 변형 추가 함수
                function addVariantGroup() {
                    variantCount++;
                    console.log("variantCount : " + variantCount);
                    console.log("variants[" + (variantCount-1) + "] : variants[" + (variantCount-1) + "]");
                    const container = document.getElementById('variantsContainer');
                    
                    const variantGroup = document.createElement('div');
                    variantGroup.className = 'variant-group';
                    variantGroup.innerHTML = 
                        '<input type="hidden" name="variants[' + (variantCount-1) + '].id" value="0">' +
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

                // 상품 변형 삭제 함수
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
                    childGroup.style.display = 'none';
                    childSelect.innerHTML = '<option value="">세부 카테고리를 선택하세요</option>';
                    if (!categoryId || isNaN(categoryId)) {
                        childSelect.required = false;
                        return;
                    }
                    if (categoryId === 5 || categoryId === 8 || categoryId === 9) {
                        const apiUrl = '/admin/api/categories/' + categoryId + '/children';
                        fetch(apiUrl)
                            .then(response => {
                                if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
                                return response.json();
                            })
                            .then(data => {
                                if (data && data.length > 0) {
                                    data.forEach(child => {
                                        const option = document.createElement('option');
                                        option.value = child.id;
                                        option.textContent = child.name;
                                        if (child.id == '${product.categoryChildId}') option.selected = true;
                                        childSelect.appendChild(option);
                                    });
                                    childGroup.style.display = 'block';
                                    childSelect.required = true;
                                }
                            })
                            .catch(error => {
                                console.error('세부 카테고리 로드 실패:', error);
                            });
                    } else {
                        childSelect.required = false;
                    }
                });
                // 페이지 진입 시 카테고리 자동 선택 및 세부 카테고리 로드
                window.addEventListener('DOMContentLoaded', function () {
                    document.getElementById('categoryId').dispatchEvent(new Event('change'));
                });
            </script>
        </body>

        </html>