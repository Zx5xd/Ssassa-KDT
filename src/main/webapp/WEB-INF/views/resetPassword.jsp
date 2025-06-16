<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <title>새 비밀번호 설정</title>
</head>
<body>
<h2>새 비밀번호 입력</h2>

<form method="post" action="/reset-password">
  <input type="hidden" name="email" value="${email}" />
  <label>새 비밀번호: <input type="password" name="newPassword" required /></label><br />
  <button type="submit">비밀번호 변경</button>
</form>

<p><a href="/login">로그인으로 돌아가기</a></p>
</body>
</html>
