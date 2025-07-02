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
</head>
<body class="noto-sans-kr-regular">

<!--  메뉴바 -->
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
                            <li><a href="#">장바구니</a></li>
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
            <category-item href="/laptop" value="노트북" color="white">
                <span class="material-icons">computer</span>
            </category-item>
            <category-item href="/gpu" value="그래픽카드" color="#eef">
                <span class="material-icons">sports_esports</span>
            </category-item>
        </category-elem>
    </div>

    <div id="content">
        <c:forEach var="i" begin="1" end="4">
            <section id="laptop">
                <div class="col">
                    <div class="row all-cat-item">
                        <h2>노트북</h2>
                        <a href="">전체보기&gt;</a>
                    </div>
                    <div class="col-item"></div>
                    <div class="col-item"></div>
                </div>
                <div class="banner-img"></div>
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
</script>

</body>
</html>
