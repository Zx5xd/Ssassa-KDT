<%@ page contentType="text/html;charset=UTF-8" language="java" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

      <!DOCTYPE html>
      <html lang="ko">

      <head>
        <meta charset="UTF-8">
        <title>상품 관리 - 싸싸</title>
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
            <a href="/admin" class="back-link">← 관리자 메인으로 돌아가기</a>

            <h2 class="admin-title">
              <span class="material-symbols-outlined">inventory_2</span>
              상품 관리
            </h2>

            <!-- 검색 기능 -->
            <div class="search-container">
              <form method="get" action="/get/products" class="search-form">
                <div class="search-input-group">
                  <input type="text" name="search" value="${searchKeyword}" placeholder="상품명을 입력하세요..."
                    class="search-input">
                  <button type="submit" class="search-btn">
                    <span class="material-symbols-outlined">search</span>
                  </button>
                </div>
                <c:if test="${not empty searchKeyword}">
                  <a href="/get/products" class="clear-search-btn">
                    <span class="material-symbols-outlined">clear</span>
                    검색 초기화
                  </a>
                </c:if>
              </form>
            </div>

            <!-- 카테고리 필터링 및 상품 등록 -->
            <div class="product-controls">
              <div class="category-filter">
                <a href="/get/products" class="category-link ${selectedCategoryId == null ? 'active' : ''}">
                  전체 상품
                </a>
                <c:forEach items="${category}" var="cat">
                  <a href="/get/products?categoryId=${cat.id}"
                    class="category-link ${selectedCategoryId == cat.id ? 'active' : ''}">
                    ${cat.name}
                  </a>
                </c:forEach>
              </div>
              <a href="/set/product/new" class="add-product-btn">
                <span class="material-symbols-outlined">add</span>

              </a>
            </div>
            <c:if test="${selectedChildId != null}">
              <div class="product-controls">
                <div class="category-child-filter">
                  <c:forEach items="${categoryChild}" var="childCat">
                    <a href="/get/products?categoryId=${selectedCategoryId}&categoryChildId=${childCat.id}"
                       class="category-link ${selectedChildId == childCat.id ? 'active' : ''}">
                        ${childCat.name}
                    </a>
                  </c:forEach>
                </div>
              </div>
            </c:if>

            <!-- 상품 목록 테이블 -->
            <div class="product-table-container">
              <table class="admin-table">
                <thead>
                  <tr>
                    <th>상품 ID</th>
                    <th>상품명</th>
                    <th>카테고리</th>
                    <th>가격</th>
                    <th>재고</th>
                    <th>등록일</th>
                    <th>관리</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach var="product" items="${productPage.content}">
                    <tr>
                      <td>${product.id}</td>
                      <td class="product-name" title="${product.name}">
                        <c:choose>
                          <c:when test="${fn:length(product.name) > 24}">
                            ${fn:substring(product.name, 0, 24)}...
                          </c:when>
                          <c:otherwise>
                            ${product.name}
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td class="category-name">
                        <c:forEach items="${category}" var="cat">
                          <c:if test="${cat.id == product.categoryId}">
                            ${cat.name}
                          </c:if>
                        </c:forEach>
                      </td>
                      <td class="price-format">
                        <c:choose>
                          <c:when test="${product.price == 0}">
                            <c:set var="variants" value="${productVariantService.getVariantsForProduct(product.id)}" />
                            <c:choose>
                              <c:when test="${not empty variants}">
                                <div class="variant-prices">
                                  <c:forEach var="variant" items="${variants}" varStatus="status">
                                    <div class="variant-price-item">
                                      ${variant.name} : ${formatUtil.formatKoreanPrice(variant.price)}
                                    </div>
                                  </c:forEach>
                                </div>
                              </c:when>
                              <c:otherwise>
                                <span class="no-price">가격 정보 없음</span>
                              </c:otherwise>
                            </c:choose>
                          </c:when>
                          <c:otherwise>
                            ${formatUtil.formatKoreanPrice(product.price)}
                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td>
                        <c:choose>
                          <c:when test="${product.amount == -1}">
                            <span class="deleted-status">삭제됨</span>
                          </c:when>
                          <c:otherwise>
                            <!-- ${product.amount}개 -->

                            <c:choose>
                              <c:when test="${product.price == 0}">
                                <c:set var="variants"
                                  value="${productVariantService.getVariantsForProduct(product.id)}" />
                                <c:choose>
                                  <c:when test="${not empty variants}">
                                    <div class="variant-prices">
                                      <c:forEach var="variant" items="${variants}" varStatus="status">
                                        <div class="variant-price-item">
                                          ${variant.amount}개
                                        </div>
                                      </c:forEach>
                                    </div>
                                  </c:when>
                                  <c:otherwise>
                                    <span class="no-price">[Error] 갯수 확인 불가가</span>
                                  </c:otherwise>
                                </c:choose>
                              </c:when>
                              <c:otherwise>
                                0개
                              </c:otherwise>
                            </c:choose>

                          </c:otherwise>
                        </c:choose>
                      </td>
                      <td>${formatUtil.formatKoreanDate(product.reg)}</td>
                      <td>
                        <div class="action-buttons">
                          <a href="/set/product/edit/${product.id}" class="edit-btn">
                            <span class="material-symbols-outlined">
                              edit
                            </span></a>
                          <c:if test="${product.amount != -1}">
                            <form action="/set/product/delete/${product.id}" method="post" style="display:inline;"
                              onsubmit="return confirm('정말 삭제하시겠습니까?');">
                              <input type="hidden" name="categoryId" value="${selectedCategoryId}" />
                              <input type="hidden" name="page" value="${productPage.number + 1}" />
                              <button type="submit" class="danger-btn">
                                <span class="material-symbols-outlined">
                                  delete
                                </span>
                              </button>
                            </form>
                          </c:if>
                        </div>
                      </td>
                    </tr>
                  </c:forEach>
                  <c:if test="${empty productPage.content}">
                    <tr>
                      <td colspan="7" class="no-data">등록된 상품이 없습니다.</td>
                    </tr>
                  </c:if>
                </tbody>
              </table>
            </div>

            <!-- 페이지네이션 -->
            <c:if test="${productPage.totalPages > 1}">
              <nav class="pagination-container">
                <ul class="pagination">
                  <!-- 이전 페이지 -->
                  <c:if test="${productPage.hasPrevious()}">
                    <li class="page-item">
                      <a class="page-link"
                        href="?categoryId=${selectedCategoryId}&search=${searchKeyword}&page=${productPage.number}"
                        aria-label="Previous">&laquo;</a>
                    </li>
                  </c:if>

                  <!-- 페이지 번호 계산 -->
                  <c:set var="currentPage" value="${productPage.number + 1}" />
                  <c:set var="totalPages" value="${productPage.totalPages}" />

                  <!-- 시작 페이지 계산 -->
                  <c:set var="startPage" value="1" />
                  <c:if test="${currentPage - 4 > 1}">
                    <c:set var="startPage" value="${currentPage - 4}" />
                  </c:if>

                  <!-- 끝 페이지 계산 -->
                  <c:set var="endPage" value="${totalPages}" />
                  <c:if test="${currentPage + 5 < totalPages}">
                    <c:set var="endPage" value="${currentPage + 5}" />
                  </c:if>

                  <!-- 시작 페이지가 1보다 크면 첫 페이지 링크 추가 -->
                  <c:if test="${startPage > 1}">
                    <li class="page-item">
                      <a class="page-link" href="?categoryId=${selectedCategoryId}&search=${searchKeyword}&page=1">1</a>
                    </li>
                    <c:if test="${startPage > 2}">
                      <li class="page-item disabled">
                        <span class="page-link">...</span>
                      </li>
                    </c:if>
                  </c:if>

                  <!-- 페이지 번호들 -->
                  <c:forEach begin="${startPage}" end="${endPage}" var="i">
                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                      <a class="page-link"
                        href="?categoryId=${selectedCategoryId}&search=${searchKeyword}&page=${i}">${i}</a>
                    </li>
                  </c:forEach>

                  <!-- 끝 페이지가 전체 페이지보다 작으면 마지막 페이지 링크 추가 -->
                  <c:if test="${endPage < totalPages}">
                    <c:if test="${endPage < totalPages - 1}">
                      <li class="page-item disabled">
                        <span class="page-link">...</span>
                      </li>
                    </c:if>
                    <li class="page-item">
                      <a class="page-link"
                        href="?categoryId=${selectedCategoryId}&search=${searchKeyword}&page=${totalPages}">${totalPages}</a>
                    </li>
                  </c:if>

                  <!-- 다음 페이지 -->
                  <c:if test="${productPage.hasNext()}">
                    <li class="page-item">
                      <a class="page-link"
                        href="?categoryId=${selectedCategoryId}&search=${searchKeyword}&page=${currentPage + 1}"
                        aria-label="Next">&raquo;</a>
                    </li>
                  </c:if>
                </ul>
              </nav>
            </c:if>
          </div>
        </main>

        <style>
          .admin-title {
            margin-top: 15px;
          }

          .product-controls {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding: 20px;
            background-color: #2a2a2a;
            border-radius: 8px;
          }

          .category-filter {
            display: grid;
            grid-template-columns: repeat(5, 2fr);
            row-gap: 10px;
            column-gap: 20px;
          }

          .category-child-filter {
            display: grid;
            grid-template-columns: repeat(6, 1fr);
            row-gap: 10px;
            column-gap: 20px;
          }

          .category-link {
            padding: 8px 16px;
            background-color: #444;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            transition: background-color 0.3s;
          }

          .category-link:hover {
            background-color: #555;
          }

          .category-link.active {
            background-color: #007bff;
          }

          .add-product-btn {
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 10px 20px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            transition: background-color 0.3s;
            margin-left: 48px;
            margin-right: 30px;
          }

          .add-product-btn:hover {
            background-color: #218838;
            color: white;
          }

          .product-table-container {
            margin-bottom: 30px;
          }

          .deleted-status {
            color: #dc3545;
            font-weight: bold;
          }

          .action-buttons {
            display: flex;
            gap: 5px;
            justify-content: center;
          }

          /* 상품명 글자 수 제한 */
          .product-name {
            max-width: 200px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
            position: relative;
            cursor: help;
          }

          /* 상품명 툴팁 스타일 - 더 확실한 방법 */
          .product-name:hover::before {
            content: attr(title);
            position: absolute;
            bottom: 100%;
            left: 50%;
            transform: translateX(-50%);
            background-color: #333;
            color: white;
            padding: 8px 12px;
            border-radius: 6px;
            font-size: 12px;
            white-space: nowrap;
            z-index: 1000;
            box-shadow: 0 2px 8px rgba(0, 0, 0, 0.3);
            margin-bottom: 5px;
          }

          /* 카테고리 no-wrap */
          .category-name {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            max-width: 120px;
          }

          /* 가격 포맷 스타일 */
          .price-format {
            text-align: right;
            font-weight: bold;
          }

          /* Variant 가격 표시 스타일 */
          .variant-prices {
            text-align: center;
            font-size: 15px;
          }

          .variant-price-item {
            padding: 2px 0;
            border-bottom: 1px solid #444;
          }

          .variant-price-item:last-child {
            border-bottom: none;
          }

          .no-price {
            color: #6c757d;
            font-style: italic;
          }

          .pagination-container {
            display: flex;
            justify-content: center;
            margin-top: 30px;
          }

          .pagination {
            display: flex;
            list-style: none;
            padding: 0;
            margin: 0;
            gap: 5px;
          }

          .page-item {
            margin: 0;
          }

          .page-link {
            padding: 8px 12px;
            background-color: #444;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s;
          }

          .page-link:hover {
            background-color: #555;
          }

          .page-item.active .page-link {
            background-color: #007bff;
          }

          .page-item.disabled .page-link {
            background-color: #333;
            color: #666;
            cursor: not-allowed;
          }

          .no-data {
            text-align: center;
            padding: 40px;
            color: #6c757d;
            font-style: italic;
          }

          .admin-name {
            font-weight: bold;
            color: #4da6ff;
          }

          /* 검색 기능 스타일 */
          .search-container {
            margin-bottom: 30px;
            padding: 20px;
            background-color: #2a2a2a;
            border-radius: 8px;
          }

          .search-form {
            display: flex;
            align-items: center;
            gap: 15px;
          }

          .search-input-group {
            display: flex;
            align-items: center;
            flex: 1;
            max-width: 400px;
          }

          .search-input {
            flex: 1;
            padding: 12px 16px;
            border: 1px solid #444;
            border-radius: 6px 0 0 6px;
            background-color: #333;
            color: white;
            font-size: 14px;
          }

          .search-input:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
          }

          .search-btn {
            padding: 12px 16px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 0 6px 6px 0;
            cursor: pointer;
            transition: background-color 0.3s;
          }

          .search-btn:hover {
            background-color: #0056b3;
          }

          .clear-search-btn {
            display: flex;
            align-items: center;
            gap: 5px;
            padding: 8px 12px;
            background-color: #6c757d;
            color: white;
            text-decoration: none;
            border-radius: 6px;
            font-size: 12px;
            transition: background-color 0.3s;
          }

          .clear-search-btn:hover {
            background-color: #5a6268;
            color: white;
          }

          .admin-card {}
        </style>
      </body>

      </html>