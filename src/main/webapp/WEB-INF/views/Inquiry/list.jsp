<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>문의사항 목록</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>

<h2>문의사항 목록</h2>
<a href="/inquiry/write" class="write-button">+ 문의 작성</a>

<table>
    <tr>
        <th>ID</th>
        <th>제목</th>
        <th>작성자</th>
        <th>상세보기</th>
    </tr>
    <c:forEach var="inq" items="${inquiryList}">
        <tr>
            <td>${inq.id}</td>
            <td>${inq.title}</td>
            <td>${inq.username}</td>
            <td><a class="detail-link" href="/inquiry/detail/${inq.id}">보기</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>