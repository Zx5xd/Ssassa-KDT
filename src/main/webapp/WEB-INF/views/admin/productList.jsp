<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>상품 관리</title>
  <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h2 class="mb-4">상품 목록</h2>
<a href="/admin" class="btn btn-secondary mb-3">← 관리자 홈</a>

<!-- 카테고리 선택 및 상품 등록 버튼 -->
<form method="get" action="/admin/products" class="mb-3 d-flex align-items-center">
  <c:if test="${not empty category}">
    <div class="category-links">
      <c:forEach items="${category}" var="cat">
        <a href="/products/admin/list?categoryId=${cat.id}">${cat.name}</a>
      </c:forEach>
    </div>
  </c:if>
  <a href="/admin/products/new" class="btn btn-success">+ 상품 등록</a>
</form>

<table class="table table-striped table-bordered">
  <thead class="table-light">
  <tr>
    <th>상품 ID</th>
    <th>상품명</th>
    <th>가격</th>
    <th>등록일</th>
    <th>관리</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="product" items="${productPage.content}">
    <tr>
      <td>${product.id}</td>
      <td>${product.name}</td>
      <td>${product.price}원</td>
      <td>${product.reg}</td>
      <td>
        <a href="/admin/products/edit/${product.id}" class="btn btn-sm btn-warning">수정</a>
        <form action="/admin/products/delete/${product.id}" method="post" style="display:inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
          <input type="hidden" name="categoryId" value="${selectedCategoryId}"/>
          <input type="hidden" name="page" value="${productPage.number + 1}"/>
          <button type="submit" class="btn btn-sm btn-danger">삭제</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<!-- 페이지네이션 -->
<nav>
  <ul class="pagination justify-content-center">
    <c:if test="${productPage.hasPrevious()}">
      <li class="page-item">
        <a class="page-link" href="?categoryId=${selectedCategoryId}&page=${productPage.number}" aria-label="Previous">&laquo;</a>
      </li>
    </c:if>
    <c:forEach begin="1" end="${productPage.totalPages}" var="i">
      <li class="page-item <c:if test='${i == productPage.number + 1}'>active</c:if>'">
        <a class="page-link" href="?categoryId=${selectedCategoryId}&page=${i}">${i}</a>
      </li>
    </c:forEach>
    <c:if test="${productPage.hasNext()}">
      <li class="page-item">
        <a class="page-link" href="?categoryId=${selectedCategoryId}&page=${productPage.number + 2}" aria-label="Next">&raquo;</a>
      </li>
    </c:if>
  </ul>
</nav>

</body>
</html>
