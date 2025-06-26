<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>회원 목록</title>
  <link rel="stylesheet" href="/resources/css/bootstrap.min.css">
</head>
<body class="container mt-5">
<h2 class="mb-4">회원 목록</h2>
<a href="/admin" class="btn btn-secondary mb-3">← 관리자 홈</a>

<table class="table table-bordered table-hover">
  <thead class="table-light">
  <tr>
    <th>ID</th>
    <th>이메일</th>
    <th>이름</th>
    <th>닉네임</th>
    <th>전화번호</th>
    <th>가입일</th>
    <th>권한</th>
    <th>관리</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="user" items="${userList}">
    <tr>
      <td>${user.id}</td>
      <td>${user.email}</td>
      <td>${user.name}</td>
      <td>${user.nickname}</td>
      <td>${user.phone}</td>
      <td>${user.createdAt}</td>
      <td>${user.role}</td>
      <td>
        <a href="/admin/users/edit/${user.id}" class="btn btn-sm btn-warning">수정</a>
        <form action="/admin/users/delete/${user.id}" method="post" style="display:inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
          <button type="submit" class="btn btn-sm btn-danger">삭제</button>
        </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
