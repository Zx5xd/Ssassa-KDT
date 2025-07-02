<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<html>
<head>
    <title>문의 상세</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>

<div class="detail-container">
    <div class="detail-title">${inquiry.title}</div>
    <div class="detail-meta">작성자: ${inquiry.username}</div>
    <hr>
    <div class="detail-content">${inquiry.content}</div>
    <a class="back-link" href="/inquiry/list">← 목록으로</a>
</div>

</body>
</html>