<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>상품 목록</title></head>
<body>
<h2>상품 목록</h2>
<table border="1" cellpadding="10">
    <tr>
        <th>상품명</th>
        <th>가격</th>
        <th>결제</th>
    </tr>
    <c:forEach var="product" items="${products}">
        <tr>
            <td>${product[0]}</td>
            <td>${product[1]} 원</td>
            <td>
                <a href="/pay/ready?productId=${product[0] eq '테스트 상품1' ? 1 : 2}">카카오페이 결제</a>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
