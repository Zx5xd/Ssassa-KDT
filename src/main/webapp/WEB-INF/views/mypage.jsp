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

<table border="1">
    <tr><th>ID</th><th>상품명</th><th>금액</th><th>상태</th><th>환불</th></tr>
    <c:forEach var="p" items="${payments}">
        <tr>
            <td>${p.id}</td>
            <td>${p.itemName}</td>
            <td>${p.amount}</td>
            <td>${p.status}</td>
            <td>
                <c:if test="${p.status == 'SUCCESS'}">
                    <form action="/refund" method="post">
                        <input type="hidden" name="paymentId" value="${p.id}" />
                        <button type="submit">환불 요청</button>
                    </form>
                </c:if>
            </td>
        </tr>
    </c:forEach>
</table>
<br>
<a href="/logout">로그아웃</a>

<form method="post" action="/withdraw" onsubmit="return confirm('정말 탈퇴하시겠습니까?')">
  <button type="submit">회원 탈퇴</button>
</form>
</body>
</html>
