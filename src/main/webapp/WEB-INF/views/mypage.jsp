<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <title>마이페이지</title>
</head>
<body>
<h2>마이페이지</h2>

<p>이메일: ${user.email}</p>
<p>이름: ${user.name}</p>
<p>닉네임: ${user.nickname}</p>
<p>전화번호: ${user.phone}</p>
<p>가입일: ${user.createdAt}</p>
<p>이메일 인증 여부: ${user.emailVerified}</p>

<br>
<a href="/logout">로그아웃</a>

<form method="post" action="/withdraw" onsubmit="return confirm('정말 탈퇴하시겠습니까?')">
  <button type="submit">회원 탈퇴</button>
</form>
</body>
</html>
