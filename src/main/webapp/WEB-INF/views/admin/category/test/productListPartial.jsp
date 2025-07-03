<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:choose>
    <c:when test="${not empty products}">
        <table class="data-table">
            <thead>
                <tr>
                    <th>상품명</th>
                    <c:forEach var="key" items="${productKeys}">
                        <th>${key}</th>
                    </c:forEach>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.name}</td>
                        <c:forEach var="key" items="${productKeys}">
                            <td>
                                <c:choose>
                                    <c:when test="${not empty product.detail[key]}">
                                        ${product.detail[key].value}
                                    </c:when>
                                    <c:otherwise>-</c:otherwise>
                                </c:choose>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:when>
    <c:otherwise>
        <div class="empty-data">검색 결과가 없습니다.</div>
    </c:otherwise>
</c:choose> 