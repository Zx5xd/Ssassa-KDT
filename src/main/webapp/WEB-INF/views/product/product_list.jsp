<%--
  Created by IntelliJ IDEA.
  User: kwonm
  Date: 25. 6. 24.
  Time: 오후 3:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <link rel="stylesheet" href="/css/product_list.css">
</head>
<script>
    window.categoryMapData = JSON.parse('${categoryJson}');
</script>
<body class="noto-sans-kr-regular">
<nav>
    <div id="logo"></div>
    <div id="searchBox">
        <input type="text" id="searchInput" placeholder="검색할 제품을 입력해주세요">
        <div class="searchBtn">
            <span class="material-symbols-outlined">search</span>
        </div>
    </div>
    <div id="user-interface">
        <div class="login">
            <span class="material-symbols-outlined">account_box</span>
            <span class="label">로그인</span>
        </div>
    </div>
</nav>
<main>
    <div class="category-container">
        <category-elem id="categoryBar">
            <category-item href="/laptop" value="노트북" color="white">
                <span class="material-icons">computer</span>
            </category-item>

            <category-item href="/laptop" value="노트북" color="white">
                <span class="material-icons">computer</span>
            </category-item>

            <category-item bgColor="red" textColor="white" href="/gpu" value="그래픽카드" color="#eef">
                <span class="material-icons">sports_esports</span>
            </category-item>
        </category-elem>
    </div>
    <div id="content-center">
        <filter-panel id="filter-panel"></filter-panel>
        <product-list id="plist"></product-list>
    </div>
    <div id="bc">
        <basket-side></basket-side>
    </div>
</main>
</body>
<script src="/js/SsaComponent.js" type="text/javascript"></script>
</html>

<script>
    const list = document.querySelector("product-list");
    const basket = document.querySelector("basket-side");

    list.basketSideEl = basket;

    if (list && window.categoryMapData) {
        const map = new Map();
        for (const [key, value] of Object.entries(window.categoryMapData)) {
            map.set(parseInt(key), {
                id: value.id,
                code: value.code,
                name: value.name,
                variants: new Map(
                    Object.entries(value.variants).map(([k, v]) => [parseInt(k), v])
                )
            });
        }
        list.categoryMap = map;
    }

    // 전달된 product 리스트를 JS로 렌더링할 수 있도록 JSON 문자열로 출력
    list.products = <%= new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(request.getAttribute("products")) %>;

    // Page 정보도 JS 객체로 사용
    const pageInfo = {
        number: <%= ((org.springframework.data.domain.Page<?>) request.getAttribute("page")).getNumber() %>,
        size: <%= ((org.springframework.data.domain.Page<?>) request.getAttribute("page")).getSize() %>,
        totalPages: <%= ((org.springframework.data.domain.Page<?>) request.getAttribute("page")).getTotalPages() %>,
        totalElements: <%= ((org.springframework.data.domain.Page<?>) request.getAttribute("page")).getTotalElements() %>
    };

    console.log("페이지 정보:", pageInfo);
</script>
