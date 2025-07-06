<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>싸싸</title>

    <!--  폰트 및 아이콘 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">

    <!--  외부 CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/slide.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/index-content.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/profile.css">

    <style>
        main {
            height: 100%;
        }
        
        /* 카테고리별 추천 아이템 레이아웃 */
        .product-card {
            cursor: pointer;
            transition: transform 0.2s ease;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            height: 100%;
            width: 100%;
        }
        
        .product-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }
        
        .product-layout {
            display: flex;
            align-items: center;
            height: 100%;
            width: 100%;
        }
        
        .product-image-container {
            flex-shrink: 0;
            margin-right: 16px;
        }
        
        .product-image {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 6px;
        }
        
        .product-info-container {
            flex: 1;
            min-width: 0;
        }
        
        .product-info {
            display: flex;
            flex-direction: column;
            gap: 4px;
        }
        
        .product-name {
            font-size: 15px;
            font-weight: 600;
            color: #333;
            margin: 0;
            line-height: 1.3;
        }
        
        .product-price {
            font-size: 16px;
            font-weight: 700;
            color: #e74c3c;
            margin: 0;
        }
        
        .no-products {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 104px;
            background: #f8f9fa;
            border-radius: 8px;
            color: #6c757d;
            font-size: 14px;
        }
    </style>
</head>
<body class="noto-sans-kr-regular">

<!--  메뉴바 -->
<nav>
    <a href="${pageContext.request.contextPath}/index" style="text-decoration: none;">
        <div id="logo" style="background-image: url('${pageContext.request.contextPath}/resources/Ssa-Front/assets/logo_main.png');"></div>
    </a>

    <div id="searchBox">
<%--        <form action="/pd/list" method="get">--%>
        <input type="text" id="searchInput" placeholder="검색할 제품을 입력해주세요">
        <div class="searchBtn" id="searchBtn">
            <span class="material-symbols-outlined">search</span>
        </div>
<%--        </form>--%>
    </div>

    <div id="user-interface">
        <c:choose>
            <c:when test="${not empty sessionScope.loginUser}">
                <!--  로그인 상태 -->
                <div class="profile-wrapper" onclick="toggleProfileMenu(event)">
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

                    <!--  드롭다운 메뉴 (초기 숨김) -->
                    <div id="profile-menu" class="hidden">
                        <div class="menu-header">
                            <strong>${sessionScope.loginUser.nickname}님</strong>
                        </div>
                        <ul class="menu-links">
                            <li><a href="/mypage">마이페이지</a></li>
                            <li><a href="/logout">로그아웃</a></li>
                        </ul>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <!--  비로그인 상태 -->
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

<!--  슬라이드 배너 -->
<header>
    <div class="carousel-wrapper">
        <div class="blur-left"></div>
        <div class="blur-right"></div>
        <div class="carousel-track">
            <div class="slide" style="background-image: url('https://coolenjoy.net/data/editor/2412/9352b0f646c40322d19302cde950c9e2a50c9309.png');"></div>
            <div class="slide" style="background-image: url('https://img2.quasarzone.com/editor/2024/10/31/ba37e8077553fb3828bf7d96c6040ef5.png');"></div>
            <div class="slide" style="background-image: url('https://storage.tweak.dk/processor/amd-9800X3D/AMD%209800X3D%20Specs.png');"></div>
        </div>
        <div class="pagination"></div>
        <button class="prev-btn">‹</button>
        <button class="next-btn">›</button>
    </div>
</header>

<!--  본문 영역 -->
<main>
    <div class="category-container">
        <category-elem id="categoryBar">
            <c:forEach var="category" items="${categories}" varStatus="status">
                <category-item data-cid="${category.id}" value="${category.name}" bgColor="#FF6B6B" textColor="white">
                    <span class="material-icons">
                        <c:choose>
                            <c:when test="${category.id == 1}">microwave</c:when>
                            <c:when test="${category.id == 2}">dry_cleaning</c:when>
                            <c:when test="${category.id == 3}">local_laundry_service</c:when>
                            <c:when test="${category.id == 4}">cleaning_services</c:when>
                            <c:when test="${category.id == 5}">local_laundry_service</c:when>
                            <c:when test="${category.id == 6}">kitchen</c:when>
                            <c:when test="${category.id == 7}">kitchen</c:when>
                            <c:when test="${category.id == 8}">keyboard</c:when>
                            <c:when test="${category.id == 9}">memory</c:when>
                            <c:when test="${category.id == 10}">smartphone</c:when>
                            <c:when test="${category.id == 11}">tablet</c:when>
                            <c:when test="${category.id == 12}">laptop</c:when>
                            <c:when test="${category.id == 13}">tv</c:when>
                            <c:otherwise>category</c:otherwise>
                        </c:choose>
                    </span>
                </category-item>
            </c:forEach>
        </category-elem>
    </div>

    <div id="content">
        <c:forEach var="category" items="${categories}" varStatus="status">
            <section id="section-${category.id}" class="category-section">
                <div class="col">
                    <div class="row all-cat-item">
                        <h2>${category.name}</h2>
                        <a href="/pd/list?cid=${category.id}">전체보기&gt;</a>
                    </div>
                    <div class="col-item">
                        <c:set var="cid" value="${category.id}" />
                        <c:set var="categoryProduct" value="${categoryProducts[cid]}" />

                        
                        <c:if test="${not empty categoryProduct}">
                            <c:forEach var="product" items="${categoryProduct}" varStatus="productStatus">

                                <c:if test="${productStatus.index < 1}">
                                    <div class="product-card" data-product-id="${product.id}">
                                        <div class="product-layout">
                                            <div class="product-image-container">
                                                <c:set var="productImgUrl" value="${productImgCache.getImageUrl(product.simpleImg)}" />
                                                <img src="${productImgUrl}" 
                                                     alt="${product.name}" class="product-image">
                                            </div>
                                            <div class="product-info-container">
                                                <div class="product-info">
                                                    <h3 class="product-name">${product.name}</h3>
                                                    <p class="product-price">${product.price}원</p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty categoryProduct}">
                            <div class="no-products">
                                <p>상품이 없습니다.</p>
                            </div>
                        </c:if>
                    </div>
                </div>
                <div class="banner-img" style="background-image: url('${categoryBanners[category.id]}');"></div>
            </section>
        </c:forEach>
    </div>
</main>
<footer></footer>

<!--  JS -->
<script src="${pageContext.request.contextPath}/resources/Ssa-Front/js/slide.js"></script>
<script src="${pageContext.request.contextPath}/resources/Ssa-Front/js/SsaComponent.js"></script>

<script>
    //  드롭다운 토글
    function toggleProfileMenu(event) {
        event.stopPropagation();
        const menu = document.getElementById("profile-menu");
        menu.classList.toggle("hidden");
    }

    //  다른 영역 클릭 시 메뉴 닫기
    document.addEventListener("click", function (e) {
        const menu = document.getElementById("profile-menu");
        const wrapper = document.querySelector(".profile-wrapper");
        if (wrapper && !wrapper.contains(e.target)) {
            menu.classList.add("hidden");
        }
    });

    //  카테고리 스크롤 시 sticky
    const categoryBar = document.getElementById('categoryBar');
    const threshold = 500;
    window.addEventListener('scroll', () => {
        if (window.scrollY >= threshold) {
            categoryBar.classList.add('scrolled');
        } else {
            categoryBar.classList.remove('scrolled');
        }
    });

    // 카테고리 클릭 시 해당 섹션으로 스크롤
    document.addEventListener('DOMContentLoaded', function() {
        const categoryItems = document.querySelectorAll('category-item');
        
        categoryItems.forEach(item => {
            item.addEventListener('click', function(e) {
                e.preventDefault(); // 기본 링크 동작 방지
                
                const cid = this.getAttribute('data-cid');
                if (cid) {
                    const targetSection = document.getElementById(`section-`+cid);
                    if (targetSection) {
                        // 부드러운 스크롤 애니메이션
                        targetSection.scrollIntoView({
                            behavior: 'smooth',
                            block: 'start'
                        });
                        
                        // 현재 선택된 카테고리 하이라이트
                        categoryItems.forEach(cat => cat.style.opacity = '0.8');
                        this.style.opacity = '1';
                        this.style.transform = 'scale(1.05)';
                    }
                }
            });
        });

        // 상품 카드 클릭 시 상세 페이지로 이동
        document.querySelectorAll('.product-card').forEach(card => {
            card.addEventListener('click', function() {
                const productId = this.getAttribute('data-product-id');
                if (productId) {
                    window.location.href = '/pd/detail/' + productId;
                }
            });
        });
    });

    function performSearch() {
        const keyword = document.getElementById('searchInput').value;
        window.location.href = '/pd/list?search=' + encodeURIComponent(keyword);
    }

    document.getElementById("searchBtn").addEventListener('click', performSearch);

    document.getElementById("searchInput").addEventListener('keydown', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            performSearch();
        }
    });
</script>

</body>
</html>
