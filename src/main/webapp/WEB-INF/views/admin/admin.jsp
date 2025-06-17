<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>관리자 페이지</title>
  <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h2 class="mb-4">관리자님 환영합니다, <span style="color:blue">${adminName}</span>!</h2>

<ul class="list-group">
  <li class="list-group-item"><a href="/admin/users">회원 목록 관리</a></li>
  <li class="list-group-item"><a href="/admin/products">상품 관리</a></li>
  <li class="list-group-item"><a href="/admin/inquiries">환불 및 문의사항 관리</a></li>
  <li class="list-group-item"><a href="/logout">로그아웃</a></li>
</ul>
</body>
</html>
