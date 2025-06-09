<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>환불 관리</title>
</head>
<body>
<h2>환불 요청 목록</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>상품명</th>
        <th>금액</th>
        <th>상태</th>
        <th>환불</th>
    </tr>
    <c:forEach var="payment" items="${refunds}">
        <tr>
            <td>${payment.id}</td>
            <td>${payment.itemName}</td>
            <td>${payment.amount}</td>
            <td>${payment.status}</td>
            <td>
                <form method="post" action="/pay/refund">
                    <input type="hidden" name="id" value="${payment.id}">
                    <button type="submit">환불</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>