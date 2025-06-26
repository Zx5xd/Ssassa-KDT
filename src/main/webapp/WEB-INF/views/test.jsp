<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
  <title>메인 페이지</title>
</head>
<body>

<h2>메인 페이지</h2>

<c:choose>
  <c:when test="${not empty nickname}">
    <p><b>${nickname}</b>님 안녕하세요 | <a href="/mypage">마이페이지</a></p>
  </c:when>
  <c:otherwise>
    <p>로그인 해주세요</p>
  </c:otherwise>
</c:choose>

</body>
</html>
