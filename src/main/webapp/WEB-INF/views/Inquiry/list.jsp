<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>문의 목록</title>
</head>
<body>
<h2>문의 목록</h2>
<a href="/inquiry/write">문의 작성</a>
<table border="1">
    <tr>
        <th>번호</th>
        <th>제목</th>
        <th>작성자</th>
        <th>상태</th>
        <th>작성일</th>
    </tr>
    <c:forEach var="inq" items="${inquiries}">
        <tr>
            <td>${inq.id}</td>
            <td><a href="/inquiry/detail/${inq.id}">${inq.title}</a></td>
            <td>${inq.username}</td>
            <td>${inq.status}</td>
            <td>${inq.createdAt}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>