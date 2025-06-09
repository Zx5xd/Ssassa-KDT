<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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