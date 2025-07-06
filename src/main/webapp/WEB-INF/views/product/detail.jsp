<%--
  Created by IntelliJ IDEA.
  User: kwonm
  Date: 25. 6. 26.
  Time: 오전 10:12
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>싸싸</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons"
          rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined"
          rel="stylesheet">

    <link rel="stylesheet" href="/css/index.css">
    <link rel="stylesheet" href="/css/product_detail.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/profile.css">

    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>

    <style>

    </style>
</head>
<body class="noto-sans-kr-regular">
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
<main>
    <section id="header">
        <img src="/pdr/img/${product.img()}" alt="" onerror="this.src='https://placehold.co/600x600/png'">
        <div id="header-content">
            <input type="hidden" name="id" value="1">
            <input type="hidden" name="price" value="${product.price()}">
            <div id="header-product-name">
                ${product.name()}
            </div>
            <div id="header-review">
              <span id="review-star">
                  <span class="material-icons">star</span>
                  <span id="review-star-count">4.9</span>
              </span>
                <a id="review-count" href="#review">리뷰 9개</a>
            </div>
            <div id="header-price">
                판매가 <fmt:formatNumber value="${product.price()}" type="number"/>원
            </div>
            <div id="header-dco">
                <div id="header-delivery-cost">
                    배송비 2,500원
                </div>
                <div id="header-other">
                    <span class="material-icons">share</span>
                    <span class="material-icons">favorite</span>
                </div>
            </div>
            <div id="header-buy-amount-container">
                <label id="buy-amount">
                    수량
                    <span class="material-icons" id="remove_amount">remove_circle</span>
                    <input type="number" value="1" min="1" max="100" id="amount_input">
                    <span class="material-icons" id="add_amount">add_circle</span>
                </label>
                <span id="buy-total-price"></span>
            </div>
            <div id="header-buttons">
                <div id="buy-button">구매</div>
<%--                <div id="basket-button">장바구니</div>--%>
            </div>
        </div>
    </section>
    <section id="spec">
        <h5>상세정보</h5>
        <div id="spec-container">
            <!-- specData가 여기에 테이블로 렌더링됩니다 -->
        </div>
    </section>
    <section id="review">
        <h5>리뷰</h5>
        <div id="review-container">
            <review-input id="reviewInput"></review-input>
            <review-viewer id="reviewPanel"></review-viewer>
        </div>
    </section>
</main>
</body>
</html>

<script>
    const specData = ${product.detail()}

        function performSearch() {
            const keyword = document.getElementById('searchInput').value;
            window.location.href = '/pd/list?search=' + encodeURIComponent(keyword);
        }

    document.getElementById("searchBtn").addEventListener('click', performSearch);

    document.getElementById("searchInput").addEventListener('keydown', function (e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            performSearch();
        }
    });

    //  드롭다운 토글
    function toggleProfileMenu(event) {
        event.stopPropagation();
        const menu = document.getElementById("profile-menu");
        menu.classList.toggle("hidden");
    }

    document.getElementById("buy-button").addEventListener('click',function(){
        const productId = ${product.id};

        const amount = document.getElementById("amount_input").value;

        location.href='/pay/ready?productId=' + productId + '&quantity=' + amount;
    })
</script>
<script type="module" src="/js/p_detail.js"></script>
<script src="/js/SsaComponent.js"></script>
