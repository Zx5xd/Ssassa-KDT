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
  <c:forEach var="product" items="${productList}">
    <tr>
      <td>${product.id}</td>
      <td>${product.name}</td>
      <td>${product.price}원</td>
      <td>${product.createdAt}</td>
      <td>
        <a href="/admin/products/edit/${product.id}" class="btn btn-sm btn-warning">수정</a>
        <a href="/admin/products/delete/${product.id}" class="btn btn-sm btn-danger"
           onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>

<div class="text-end">
  <a href="/admin/products/new" class="btn btn-primary">+ 상품 등록</a>
</div>
</body>
</html>
