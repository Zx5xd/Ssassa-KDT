<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<script>
  if(resultMsg !== ""){
    alert('${resultMsg}')
  }
</script>
<html>
<head>
  <title>카테고리 필드 목록</title>
</head>
<body>
<h1>카테고리 필드 목록</h1>
<c:if test="${not empty category}">
  <c:forEach items="${category}" var="cat">
    <a href="/cat/displayOrder-All/${cat.id}">${cat.name}</a>
  </c:forEach>
</c:if>

<!-- 값이 없을 때 -->
<c:if test="${empty dtoList}">
  <p>데이터가 없습니다.</p>
</c:if>

<!-- 값이 있을 때 -->

<c:if test="${not empty dtoList}">
<form action="/cat/set/displayOrder-All" method="post">
  <table border="1">
    <thead>
    <tr>
      <th>ID</th>
      <th>Category ID</th>
      <th>Category Child ID</th>
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
        <input type="hidden" name="fieldId" value="${item.id}"/>
        <td>${item.categoryId}</td>
        <td>${item.categoryChildId}</td>
        <td>${item.attributeKey}</td>
        <td>${item.displayName}</td>
        <td>${item.dataType}</td>
        <td>${item.unit}</td>
        <td><label>
          <input type="text" name="orderNum" value="${item.displayOrder}"/>
        </label></td>
        <td>${item.tooltip}</td>
        <td>${item.valueList}</td>
      </tr>
    </c:forEach>
      <tr>
        <td colspan="8"></td>
        <td colspan="2">
          <input type="submit" value="변경" style="width: 100%">
        </td>
      </tr>
    </tbody>
  </table>
</form>
</c:if>

</body>
</html>
