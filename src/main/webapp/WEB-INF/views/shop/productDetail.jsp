<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${product.name} - 싸싸</title>
    
    <!-- 폰트 및 아이콘 -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
    
    <!-- 스타일 시트 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/common.css">
</head>
<body class="noto-sans-kr-regular">
    <!-- 네비게이션 -->
    <nav>
        <a href="${pageContext.request.contextPath}/index" style="text-decoration: none;">
            <div id="logo" style="background-image: url('${pageContext.request.contextPath}/resources/Ssa-Front/assets/logo_main.png');"></div>
        </a>

        <div id="searchBox">
            <input type="text" id="searchInput" placeholder="검색할 제품을 입력해주세요">
            <div class="searchBtn">
                <span class="material-symbols-outlined">search</span>
            </div>
        </div>

        <div id="user-interface">
            <c:choose>
                <c:when test="${not empty sessionScope.loginUser}">
                    <div class="profile-wrapper" onclick="toggleProfileMenu()">
                        <c:choose>
                            <c:when test="${not empty sessionScope.loginUser.profileImage}">
                                <img class="navbar-profile-img"
                                     src="${pageContext.request.contextPath}${sessionScope.loginUser.profileImage}" alt="프로필">
                            </c:when>
                            <c:otherwise>
                                <div class="login" style="display: flex; align-items: center; cursor: pointer;">
                                    <span class="material-symbols-outlined">account_circle</span>
                                    <span class="label">${sessionScope.loginUser.nickname}님</span>
                                </div>
                            </c:otherwise>
                        </c:choose>
                        <div id="profile-menu" class="hidden">
                            <div class="menu-header">
                                <strong>${sessionScope.loginUser.nickname}님</strong>
                            </div>
                            <ul class="menu-links">
                                <li><a href="/mypage">마이페이지</a></li>
                                <li><a href="#">장바구니</a></li>
                                <li><a href="/logout">로그아웃</a></li>
                            </ul>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <a href="${pageContext.request.contextPath}/login" style="text-decoration: none; color: inherit;">
                        <div class="login">
                            <span class="material-symbols-outlined">account_box</span>
                            <span class="label">로그인</span>
                        </div>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </nav>

    <!-- 상품 상세 내용 -->
    <main>
        <div class="product-detail-container">
                            <a href="/pd/get/products" class="back-link">← 상품 목록으로 돌아가기</a>
            
            <div class="product-detail-card">
                <div class="product-header">
                    <h1 class="product-title">${product.name}</h1>
                    <div class="product-category">
                        카테고리: 
                        <c:choose>
                            <c:when test="${not empty categoryName}">
                                ${categoryName}
                            </c:when>
                            <c:otherwise>
                                카테고리 정보 없음
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                
                <div class="product-image-section">
                    <div class="image-container">
                        <c:choose>
                            <c:when test="${not empty productService.getProductSimpleImg(product)}">
                                <img src="${pageContext.request.contextPath}${productService.getProductSimpleImg(product)}" 
                                     alt="${product.name}" class="product-image"
                                     onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';">
                                <div class="product-image" style="display: none; align-items: center; justify-content: center; background: #f8f9fa; color: #6c757d;">
                                    <span>이미지 없음</span>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="product-image" style="display: flex; align-items: center; justify-content: center; background: #f8f9fa; color: #6c757d;">
                                    <span>이미지 없음</span>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    
                    <div class="product-info">
                        <div class="product-price">
                            <c:choose>
                                <c:when test="${not empty productService.getProductPrice(product.id)}">
                                    ₩<fmt:formatNumber value="${productService.getProductPrice(product.id)}" type="number" groupingUsed="true" />
                                </c:when>
                                <c:otherwise>
                                    가격 정보 없음
                                </c:otherwise>
                            </c:choose>
                        </div>
                        
                        <div class="product-description">
                            <c:choose>
                                <c:when test="${not empty product.detail}">
                                    ${product.detail}
                                </c:when>
                                <c:otherwise>
                                    상품 설명이 없습니다.
                                </c:otherwise>
                            </c:choose>
                        </div>
                        
                        <div class="product-actions">
                            <div class="quantity-control">
                                <label for="quantity">수량:</label>
                                <input type="number" id="quantity" class="quantity-input" value="1" min="1" max="99">
                            </div>
                            
                            <a href="/pay/ready?productId=${product.id}&quantity=1" class="btn btn-primary" id="buyButton">
                                <span class="material-symbols-outlined">shopping_cart</span>
                                구매하기
                            </a>
                            
                            <button class="btn btn-secondary" onclick="addToCart()">
                                <span class="material-symbols-outlined">add_shopping_cart</span>
                                장바구니
                            </button>
                        </div>
                    </div>
                </div>
                
                <div class="product-specs">
                    <h2 class="specs-title">상품 상세 정보</h2>
                    <div class="specs-grid">
                        <div class="spec-item">
                            <div class="spec-label">상품 ID</div>
                            <div class="spec-value">${product.id}</div>
                        </div>
                        <div class="spec-item">
                            <div class="spec-label">상품명</div>
                            <div class="spec-value">${product.name}</div>
                        </div>
                        <div class="spec-item">
                            <div class="spec-label">카테고리</div>
                            <div class="spec-value">
                                <c:choose>
                                    <c:when test="${not empty categoryName}">
                                        ${categoryName}
                                    </c:when>
                                    <c:otherwise>
                                        카테고리 정보 없음
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        <c:if test="${not empty product.defaultVariantId}">
                            <div class="spec-item">
                                <div class="spec-label">기본 옵션 ID</div>
                                <div class="spec-value">${product.defaultVariantId}</div>
                            </div>
                        </c:if>
                        <div class="spec-item">
                            <div class="spec-label">재고 수량</div>
                            <div class="spec-value">${product.amount}개</div>
                        </div>
                        <div class="spec-item">
                            <div class="spec-label">방문 횟수</div>
                            <div class="spec-value">${product.count}회</div>
                        </div>
                        <div class="spec-item">
                            <div class="spec-label">카테고리 ID</div>
                            <div class="spec-value">${product.categoryId}</div>
                        </div>
                        <div class="spec-item">
                            <div class="spec-label">하위 카테고리 ID</div>
                            <div class="spec-value">${product.categoryChildId}</div>
                        </div>
                        <c:if test="${not empty product.reg}">
                            <div class="spec-item">
                                <div class="spec-label">등록일</div>
                                <div class="spec-value">
                                    <fmt:formatDate value="${product.reg}" pattern="yyyy년 MM월 dd일" />
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </main>

    <script>
        // 상품 ID를 JavaScript 변수로 정의
        const productId = parseInt('${product.id}');
        
        function toggleProfileMenu() {
            const menu = document.getElementById("profile-menu");
            menu.classList.toggle("hidden");
        }

        document.addEventListener("click", function (e) {
            const menu = document.getElementById("profile-menu");
            const wrapper = document.querySelector(".profile-wrapper");
            if (wrapper && !wrapper.contains(e.target)) {
                menu.classList.add("hidden");
            }
        });

        function addToCart() {
            const quantity = document.getElementById('quantity').value;
            // 장바구니 추가 로직 구현
            alert('장바구니에 추가되었습니다!');
        }

        // 수량 변경 시 구매하기 링크 업데이트
        document.getElementById('quantity').addEventListener('change', function() {
            const quantity = this.value;
            const buyButton = document.getElementById('buyButton');
            if (buyButton) {
                buyButton.href = `/pay/ready?productId=${productId}&quantity=${quantity}`;
            }
        });
    </script>
    
    <script src="${pageContext.request.contextPath}/resources/Ssa-Front/js/slide.js"></script>
    <script src="${pageContext.request.contextPath}/resources/Ssa-Front/js/SsaComponent.js"></script>
</body>
</html> 