<%-- Created by IntelliJ IDEA. User: kwonm Date: 25. 6. 24. Time: 오후 3:01 To change this template use File | Settings |
    File Templates. --%>
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
                <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
                <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
                <link rel="stylesheet" href="/css/index.css">
                <link rel="stylesheet" href="/css/product_list.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/profile.css">
                <style>
                    /* 필터 폼 스타일 */
                    #filterForm {
                        background: var(--surface-2);
                        border: 1px solid var(--surface-4);
                        border-radius: var(--radius-2);
                        padding: var(--size-2);
                        margin-bottom: var(--size-2);
                    }

                    .filter-group {
                        margin-bottom: var(--size-2);
                        padding-bottom: var(--size-2);
                        border-bottom: 1px solid var(--surface-3);
                    }

                    .filter-group:last-child {
                        border-bottom: none;
                        margin-bottom: 0;
                    }

                    .filter-label {
                        display: block;
                        font-weight: bold;
                        margin-bottom: var(--size-1);
                        color: var(--text-1);
                    }

                    .filter-options {
                        display: flex;
                        flex-wrap: wrap;
                        gap: var(--size-1);
                    }

                    .filter-options label {
                        display: flex;
                        align-items: center;
                        gap: 4px;
                        cursor: pointer;
                        padding: var(--size-1) var(--size-2);
                        background: var(--surface-1);
                        border-radius: var(--radius-1);
                        font-size: var(--font-size-0);
                    }

                    .filter-options label:hover {
                        background: var(--surface-3);
                    }

                    .filter-checkbox {
                        margin: 0;
                    }

                    /* 하위 카테고리 선택 필드 스타일 */
                    #subcategory-selector {
                        margin-bottom: var(--size-3);
                    }

                    #subcategory-selector.hidden {
                        display: none;
                    }

                    .subcategory-container {
                        display: flex;
                        align-items: center;
                        background: var(--surface-2);
                        border: 1px solid var(--surface-4);
                        border-radius: var(--radius-2);
                        padding: var(--size-3);
                        gap: var(--size-3);
                    }

                    .subcategory-label {
                        font-weight: var(--font-weight-7);
                        color: var(--text-1);
                        font-size: var(--font-size-1);
                        min-width: 100px;
                        flex-shrink: 0;
                    }

                    .subcategory-options {
                        display: flex;
                        flex-wrap: wrap;
                        gap: var(--size-2);
                        flex: 1;
                    }

                    .subcategory-btn {
                        padding: var(--size-2) var(--size-3);
                        background: var(--surface-1);
                        border: 1px solid var(--surface-3);
                        border-radius: var(--radius-2);
                        font-size: var(--font-size-0);
                        color: var(--text-1);
                        cursor: pointer;
                        transition: all 0.2s ease;
                        font-family: inherit;
                        outline: none;
                    }

                    .subcategory-btn:hover {
                        background: var(--surface-3);
                        border-color: var(--surface-4);
                    }

                    .subcategory-btn.active {
                        background: var(--blue-6);
                        color: white;
                        border-color: var(--blue-6);
                    }

                    .subcategory-btn.active:hover {
                        background: var(--blue-7);
                    }
                </style>
            </head>
            <script>
                window.categoryMapData = JSON.parse('${categoryJson}');
            </script>

            <body class="noto-sans-kr-regular">
                <nav>
                    <a href="${pageContext.request.contextPath}/index" style="text-decoration: none;">
                        <div id="logo"
                            style="background-image: url('${pageContext.request.contextPath}/resources/Ssa-Front/assets/logo_main.png');">
                        </div>
                    </a>

                    <div id="searchBox">
                        <input type="text" id="searchInput" placeholder="검색할 제품을 입력해주세요">
                        <div class="searchBtn" id="searchBtn">
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
                                                src="${pageContext.request.contextPath}${sessionScope.loginUser.profileImage}"
                                                alt="프로필">
                                        </c:when>
                                        <c:otherwise>
                                            <div class="login"
                                                style="display: flex; align-items: center; cursor: pointer;">
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
                                <a href="${pageContext.request.contextPath}/login"
                                    style="text-decoration: none; color: inherit;">
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
                    <div class="category-container">
                        <category-elem id="categoryBar">
                            <category-item data-cid="1" value="에어프라이어" bgColor="#FF6B6B" textColor="white">
                                <span class="material-icons">microwave</span>
                            </category-item>

                            <category-item data-cid="2" value="건조기" bgColor="#4ECDC4" textColor="white">
                                <span class="material-icons">dry_cleaning</span>
                            </category-item>

                            <category-item data-cid="3" value="세탁기" bgColor="#45B7D1" textColor="white">
                                <span class="material-icons">local_laundry_service</span>
                            </category-item>
                            <category-item data-cid="4" value="올인원 세탁기" bgColor="#96CEB4" textColor="white">
                                <span class="material-icons">cleaning_services</span>
                            </category-item>

                            <category-item data-cid="5" value="세탁+건조기" bgColor="#FFEAA7" textColor="#2D3436">
                                <span class="material-icons">local_laundry_service</span>
                            </category-item>

                            <category-item data-cid="6" value="냉장고" bgColor="#DDA0DD" textColor="white">
                                <span class="material-icons">kitchen</span>
                            </category-item>
                            <category-item data-cid="7" value="김치냉장고" bgColor="#98D8C8" textColor="white">
                                <span class="material-icons">kitchen</span>
                            </category-item>

                            <category-item data-cid="8" value="PC주변기기" bgColor="#F7DC6F" textColor="#2D3436">
                                <span class="material-icons">keyboard</span>
                            </category-item>

                            <category-item data-cid="9" value="PC부품" bgColor="#BB8FCE" textColor="white">
                                <span class="material-icons">memory</span>
                            </category-item>
                            <category-item data-cid="10" value="핸드폰" bgColor="#85C1E9" textColor="white">
                                <span class="material-icons">smartphone</span>
                            </category-item>

                            <category-item data-cid="11" value="테블릿PC" bgColor="#F8C471" textColor="white">
                                <span class="material-icons">tablet</span>
                            </category-item>

                            <category-item data-cid="12" value="노트북" bgColor="#82E0AA" textColor="white">
                                <span class="material-icons">laptop</span>
                            </category-item>
                            <category-item data-cid="13" value="TV" bgColor="#F1948A" textColor="white">
                                <span class="material-icons">tv</span>
                            </category-item>
                        </category-elem>
                    </div>
                    <div id="content-center">
                        <!-- 하위 카테고리 선택 필드 (cid가 5, 8, 9일 때만 표시) -->
                        <div id="subcategory-selector" class="hidden">
                            <div class="subcategory-container">
                                <div class="subcategory-label">하위 카테고리</div>
                                <div class="subcategory-options">
                                    <button class="subcategory-btn active" data-value="all">전체</button>
                                    <!-- 동적으로 하위 카테고리 옵션들이 여기에 추가됩니다 -->
                                </div>
                            </div>
                        </div>

                        <!-- filter-panel에 JS로 데이터 전달 -->
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
                const filterPanel = document.querySelector("filter-panel");
                const subcategorySelector = document.querySelector("#subcategory-selector");

                list.basketSideEl = basket;

                // catFilter 데이터를 JS로 변환하여 filter-panel에 전달
                const catFilterData = JSON.parse('<%= new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(request.getAttribute("catFilter")) %>'.replace(/'/g, "\\'"));

                if (filterPanel && catFilterData) {
                    // filter-panel의 filterRows 속성에 데이터 전달
                    filterPanel.filterRows = Object.entries(catFilterData).map(([displayName, valueList]) => ({
                        displayName: displayName,
                        attributeKey: displayName,
                        valueList: valueList.map(value => ({ value: value, weight: 1 })),
                        isFilterable: true,
                        displayOrder: 0,
                        unit: null,
                        tooltip: null
                    }));
                }

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
                list.products = JSON.parse('<%= new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(request.getAttribute("products")) %>'.replace(/'/g, "\\'"));

                // 상품 데이터에 variants 필드가 없는 경우 기본값 설정
                if (list.products) {
                    list.products = list.products.map(product => ({
                        ...product,
                        variants: product.variants || [] // variants가 없으면 빈 배열로 설정
                    }));
                }

                // Page 정보도 JS 객체로 사용
                const pageInfo = JSON.parse('<%= new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(request.getAttribute("page")) %>'.replace(/'/g, "\\'"));

                console.log("페이지 정보:", pageInfo);
                console.log("필터 데이터:", catFilterData);

                // 하위 카테고리 데이터 (실제로는 서버에서 가져와야 함)
                let subcategoryData = JSON.parse('<%= request.getAttribute("subCategoryJson") %>'.replace(/'/g, "\\'"));

                // 서버에서 전달된 childId
                const serverChildId = '<%= request.getAttribute("childId") != null ? request.getAttribute("childId") : "" %>';

                // URL에서 cid 파라미터 추출
                function getCidFromUrl() {
                    const urlParams = new URLSearchParams(window.location.search);
                    const cid = urlParams.get('cid');
                    return cid ? parseInt(cid) : null;
                }

                function getChildIdFromUrl() {
                    const urlParams = new URLSearchParams(window.location.search);
                    const childId = urlParams.get('childId');
                    return childId ? parseInt(childId) : null;
                }

                // URL에서 cid 추출하여 사용
                const currentCid = getCidFromUrl();
                console.log("현재 카테고리 ID:", currentCid);

                // 현재 카테고리가 있으면 해당 카테고리의 상품을 로드
                if (currentCid) {
                    loadCategoryData(currentCid);
                } else {
                    // 현재 카테고리가 없으면 하위 카테고리 선택 필드 숨김
                    toggleSubcategorySelector(null);
                }

                // 카테고리 클릭 이벤트 리스너 추가
                document.addEventListener('DOMContentLoaded', function () {
                    // DOM이 로드된 후 요소들 다시 찾기
                    const subcategorySelector = document.querySelector("#subcategory-selector");
                    console.log('DOMContentLoaded - subcategorySelector:', subcategorySelector);

                    const categoryItems = document.querySelectorAll('category-item');

                    categoryItems.forEach(item => {
                        item.addEventListener('click', function (e) {
                            e.preventDefault(); // 기본 링크 동작 방지

                            const cid = this.getAttribute('data-cid');
                            if (cid) {
                                console.log('카테고리 클릭:', cid);
                                loadCategoryData(cid);

                                // URL 업데이트 (브라우저 히스토리에 추가)
                                const newUrl = new URL(window.location);
                                newUrl.searchParams.set('cid', cid);
                                window.history.pushState({}, '', newUrl);
                            }
                        });
                    });

                    // 현재 카테고리가 있으면 해당 카테고리의 상품을 로드
                    const currentCid = getCidFromUrl();
                    console.log("DOMContentLoaded - 현재 카테고리 ID:", currentCid);

                    if (currentCid) {
                        loadCategoryData(currentCid);
                    } else {
                        // 현재 카테고리가 없으면 하위 카테고리 선택 필드 숨김
                        if (subcategorySelector) {
                            toggleSubcategorySelector(null);
                        }
                    }
                });

                // 카테고리 클릭 시 Ajax로 데이터 로드
                function loadCategoryData(cid) {
                    // console.log('카테고리 로드:', cid);

                    // DOM에서 subcategorySelector 찾기
                    const subcategorySelector = document.querySelector("#subcategory-selector");

                    // 하위 카테고리 선택 필드 토글
                    toggleSubcategorySelector(cid, subcategorySelector);

                    // 로딩 상태 표시
                    if (list) {
                        list.products = [];
                    }

                    const ajaxUrl = '/pdr/list/ajax?cid=' + cid;

                    // 카테고리별 상품 데이터 로드
                    fetch(ajaxUrl)
                        .then(res => res.json())
                        .then(data => {
                            if (data.error) {
                                console.error('카테고리 로드 오류:', data.error);
                                return;
                            }

                            // 상품 목록 업데이트
                            if (data.products) {
                                const productsWithVariants = data.products.map(product => ({
                                    ...product,
                                    variants: product.variants || []
                                }));
                                list.products = productsWithVariants;
                                console.log('카테고리 상품 수:', data.totalCount);
                            }

                            // 하위 카테고리 데이터 업데이트
                            // console.log('하위 카테고리 데이터 업데이트:', data.subCategoryJson);
                            subcategoryData = JSON.parse(data.subCategoryJson);

                            // 하위 카테고리 선택 필드 토글
                            toggleSubcategorySelector(cid, subcategorySelector);


                            // 필터 데이터 업데이트
                            if (data.catFilter && filterPanel) {
                                filterPanel.filterRows = Object.entries(data.catFilter).map(([displayName, valueList]) => ({
                                    displayName: displayName,
                                    attributeKey: displayName,
                                    valueList: valueList.map(value => ({ value: value, weight: 1 })),
                                    isFilterable: true,
                                    displayOrder: 0,
                                    unit: null,
                                    tooltip: null
                                }));
                            }

                            // URL 업데이트 (히스토리 관리)
                            const newUrl = new URL(window.location);
                            newUrl.searchParams.set('cid', cid);
                            window.history.pushState({}, '', newUrl);

                            // 현재 선택된 카테고리 하이라이트
                            highlightCurrentCategory();
                        })
                        .catch(error => {
                            console.error('카테고리 로드 오류:', error);
                        });
                }

                // 카테고리 아이템 클릭 이벤트 추가
                function addCategoryClickEvents() {
                    const categoryItems = document.querySelectorAll('category-item');
                    categoryItems.forEach(item => {
                        item.addEventListener('click', function (e) {
                            e.preventDefault();
                            const cid = this.getAttribute('data-cid');
                            if (cid) {
                                loadCategoryData(parseInt(cid));
                            }
                        });
                    });
                }

                // 현재 선택된 카테고리 하이라이트
                function highlightCurrentCategory() {
                    const currentCid = getCidFromUrl();
                    const categoryItems = document.querySelectorAll('category-item');

                    categoryItems.forEach(item => {
                        const itemCid = item.getAttribute('data-cid');
                        if (itemCid === currentCid?.toString()) {
                            item.style.opacity = '1';
                            item.style.transform = 'scale(1.05)';
                        } else {
                            item.style.opacity = '0.8';
                            item.style.transform = 'scale(1)';
                        }
                    });
                }

                // 페이지 로드 시 카테고리 클릭 이벤트 추가
                document.addEventListener('DOMContentLoaded', function () {
                    addCategoryClickEvents();

                    // 현재 카테고리가 있으면 해당 데이터 로드
                    if (currentCid) {
                        loadCategoryData(currentCid);
                    }

                    // 현재 카테고리 하이라이트
                    highlightCurrentCategory();
                });

                // 필터 변경 시 상품 검색 함수 (현재 카테고리 고려)
                function searchProducts() {
                    const form = document.getElementById('filterForm');
                    if (!form) return;

                    const formData = new FormData(form);
                    const params = new URLSearchParams();

                    // 체크된 체크박스만 수집
                    const checkedBoxes = form.querySelectorAll('input[type="checkbox"]:checked');
                    checkedBoxes.forEach(checkbox => {
                        params.append(checkbox.name, checkbox.value);
                    });

                    // REST API 호출
                    fetch('/pdr/search/filter?' + params.toString())
                        .then(res => res.json())
                        .then(data => {
                            if (data.error) {
                                console.error('검색 오류:', data.error);
                                return;
                            }

                            // 상품 목록 업데이트
                            if (data.products) {
                                // variants 필드가 없는 경우 기본값 설정
                                const productsWithVariants = data.products.map(product => ({
                                    ...product,
                                    variants: product.variants || []
                                }));
                                list.products = productsWithVariants;
                                console.log('검색된 상품 수:', data.totalCount);
                            }
                        })
                        .catch(error => {
                            console.error('API 호출 오류:', error);
                        });
                }

                // filter-panel의 change 이벤트 연결
                if (filterPanel) {
                    filterPanel.addEventListener('change', (event) => {
                        console.log('필터 변경:', event.detail);
                        // filter-panel에서 변경된 필터 정보로 검색
                        const params = new URLSearchParams();

                        // 현재 카테고리 ID 추가
                        const currentCid = getCidFromUrl();
                        if (currentCid) {
                            params.append('cid', currentCid);
                        }

                        Object.entries(event.detail).forEach(([key, values]) => {
                            if (Array.isArray(values)) {
                                values.forEach(value => params.append(key, value));
                            }
                        });

                        fetch('/pdr/search/filter?' + params.toString())
                            .then(res => res.json())
                            .then(data => {
                                if (data.products) {
                                    // variants 필드가 없는 경우 기본값 설정
                                    const productsWithVariants = data.products.map(product => ({
                                        ...product,
                                        variants: product.variants || []
                                    }));
                                    list.products = productsWithVariants;
                                    console.log('필터 검색 결과:', data.totalCount);
                                }
                            })
                            .catch(error => {
                                console.error('필터 검색 오류:', error);
                            });
                    });
                }

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

                document.getElementById("searchBtn").addEventListener('click', function () {
                    const keyword = document.getElementById('searchInput').value;
                    searchFetch(keyword)
                })

                document.getElementById("searchInput").addEventListener('keydown', function (e) {
                    if (e.key === 'Enter') {
                        e.preventDefault();

                        const keyword = document.getElementById('searchInput').value;
                        searchFetch(keyword)
                    }
                })

                function searchFetch(keyword) {
                    fetch('/pdr/list?search=' + keyword)
                        .then(res => res.json())
                        .then(data => {
                            if (data.error) {
                                console.error('검색 오류:', data.error);
                                return;
                            }

                            // 전달된 product 리스트를 JS로 렌더링할 수 있도록 JSON 문자열로 출력
                            list.products = data.content;
                        });
                }



                // 하위 카테고리 선택 필드 표시/숨김 처리
                function toggleSubcategorySelector(cid, selector = null) {
                    const subcategorySelector = selector || document.querySelector("#subcategory-selector");
                    // console.log('toggleSubcategorySelector 호출됨, cid:', cid);
                    // console.log('subcategorySelector:', subcategorySelector);

                    if (!subcategorySelector) {
                        // console.error('subcategorySelector를 찾을 수 없습니다.');
                        return;
                    }

                    if (cid !== -1) {
                        const childId = getChildIdFromUrl() || (serverChildId ? parseInt(serverChildId) : null);
                        if (cid === 5 || cid === 8 || cid === 9) {
                            // console.log('하위 카테고리 표시:', cid, subcategoryData[cid]);
                            subcategorySelector.classList.remove('hidden');
                            renderSubcategoryOptions(cid, subcategorySelector);
                        } else {
                            // console.log('하위 카테고리 숨김');
                            subcategorySelector.classList.add('hidden');
                        }
                    }
                }

                // 하위 카테고리 옵션 렌더링
                function renderSubcategoryOptions(cid, selector = null) {
                    const subcategorySelector = selector || document.querySelector("#subcategory-selector");
                    // console.log('renderSubcategoryOptions 호출됨, cid:', cid);

                    if (!subcategorySelector) {
                        console.error('subcategorySelector가 null입니다.');
                        return;
                    }

                    const optionsContainer = subcategorySelector.querySelector('.subcategory-options');
                    // console.log('optionsContainer:', optionsContainer);

                    if (!optionsContainer) {
                        console.error('.subcategory-options를 찾을 수 없습니다.');
                        return;
                    }

                    // 기존 옵션들 제거 (전체 버튼 제외)
                    const existingButtons = optionsContainer.querySelectorAll('.subcategory-btn:not(:first-child)');
                    console.log('기존 버튼 수:', existingButtons.length);
                    existingButtons.forEach(button => button.remove());

                    // 새로운 버튼들 추가
                    if (subcategoryData) {
                        console.log('subcategoryData:', subcategoryData);
                        Object.entries(subcategoryData).forEach(([key, subcat]) => {
                            console.log('버튼 추가:', key, subcat);
                            const button = document.createElement('button');
                            button.className = 'subcategory-btn';
                            button.setAttribute('data-value', subcat.id);
                            button.textContent = subcat.name;
                            optionsContainer.appendChild(button);
                        });
                    }

                    console.log('버튼 추가 완료, 총 버튼 수:', optionsContainer.children.length);

                    // 이벤트 리스너 추가
                    addSubcategoryEventListeners(subcategorySelector);
                }

                // 하위 카테고리 선택 이벤트 리스너
                function addSubcategoryEventListeners(selector = null) {
                    const subcategorySelector = selector || document.querySelector("#subcategory-selector");
                    const buttons = subcategorySelector.querySelectorAll('.subcategory-btn');
                    buttons.forEach(button => {
                        button.addEventListener('click', function () {
                            // 모든 버튼에서 active 클래스 제거
                            buttons.forEach(btn => btn.classList.remove('active'));

                            // 클릭된 버튼에 active 클래스 추가
                            this.classList.add('active');

                            const selectedValue = this.getAttribute('data-value');
                            const currentCid = getCidFromUrl();

                            if (currentCid) {
                                // 하위 카테고리 필터링 적용
                                loadCategoryDataWithSubcategory(currentCid, selectedValue);
                            }
                        });
                    });
                }

                // 하위 카테고리 필터링이 적용된 데이터 로드
                function loadCategoryDataWithSubcategory(cid, subcategoryId) {
                    if (list) {
                        list.products = [];
                    }

                    console.log('loadCategoryDataWithSubcategory 호출됨, cid:', cid, 'subcategoryId:', subcategoryId);

                    let ajaxUrl = `/pdr/list/ajax?cid=` + cid;
                    if (subcategoryId && subcategoryId !== 'all') {
                        ajaxUrl += `&childId=` + subcategoryId;
                    }

                    fetch(ajaxUrl)
                        .then(res => res.json())
                        .then(data => {
                            if (data.error) {
                                console.error('카테고리 로드 오류:', data.error);
                                return;
                            }

                            // 상품 목록 업데이트
                            if (data.products) {
                                const productsWithVariants = data.products.map(product => ({
                                    ...product,
                                    variants: product.variants || []
                                }));
                                list.products = productsWithVariants;
                                console.log('카테고리 상품 수:', data.totalCount);
                            }

                            // 필터 데이터 업데이트
                            if (data.catFilter && filterPanel) {
                                filterPanel.filterRows = Object.entries(data.catFilter).map(([displayName, valueList]) => ({
                                    displayName: displayName,
                                    attributeKey: displayName,
                                    valueList: valueList.map(value => ({ value: value, weight: 1 })),
                                    isFilterable: true,
                                    displayOrder: 0,
                                    unit: null,
                                    tooltip: null
                                }));
                            }

                            // URL 업데이트 (히스토리 관리)
                            const newUrl = new URL(window.location);
                            newUrl.searchParams.set('childId', subcategoryId);
                            window.history.pushState({}, '', newUrl);
                        })
                        .catch(error => {
                            console.error('카테고리 로드 오류:', error);
                        });
                }
            </script>