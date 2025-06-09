<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>문의 상세</title></head>
<body>
<h2>문의 상세</h2>
<p><strong>제목:</strong> ${inquiry.title}</p>
<p><strong>작성자:</strong> ${inquiry.username}</p>
<p><strong>내용:</strong><br/> ${inquiry.content}</p>
<p><strong>작성일:</strong> ${inquiry.createdAt}</p>

<a href="/inquiry/list">목록으로</a>
</body>
</html>