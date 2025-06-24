<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <title>카테고리 필드 목록</title>
</head>
<body>
<h1>카테고리 필드 목록</h1>
<%

%>
<a href="/cat/displayOrder-All?categoryId="></a>

<!-- 값이 없을 때 -->
<c:if test="${empty dtoList}">
  <p>데이터가 없습니다.</p>
</c:if>

<!-- 값이 있을 때 -->
<c:if test="${not empty dtoList}">
  <table border="1">
    <thead>
    <tr>
      <th>ID</th>
      <th>Category ID</th>
      <th>Attribute Key</th>
      <th>Display Name</th>
      <th>Data Type</th>
      <th>Unit</th>
      <th>Display Order</th>
      <th>Tooltip</th>
      <th>Value List</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="item" items="${dtoList}">
      <tr>
        <td>${item.id}</td>
        <td>${item.categoryId}</td>
        <td>${item.attributeKey}</td>
        <td>${item.displayName}</td>
        <td>${item.dataType}</td>
        <td>${item.unit}</td>
        <td>${item.displayOrder}</td>
        <td>${item.tooltip}</td>
        <td>${item.valueList}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>
</body>
</html>
