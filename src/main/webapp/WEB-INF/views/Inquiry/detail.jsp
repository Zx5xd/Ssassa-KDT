<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>문의 상세</title>
    <style>
        body { font-family: 'Noto Sans KR', sans-serif; background: #f4f6f9; padding: 40px; }
        .container { max-width: 700px; background: white; padding: 30px; margin: auto; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        h2 { margin-bottom: 20px; }
        .info { margin-bottom: 15px; }
        textarea, input[type="text"] { width: 100%; padding: 10px; margin-bottom: 15px; }
        button { padding: 10px 20px; }
    </style>
</head>
<body>
<div class="container">
    <h2>${inquiry.title}</h2>
    <div class="info"><strong>작성자:</strong> ${inquiry.username}</div>
    <div class="info"><strong>작성일:</strong> ${inquiry.createdAt}</div>
    <div class="info"><strong>내용:</strong><br> ${inquiry.content}</div>

    <c:if test="${inquiry.fileName != null}">
        <div class="info">
            <strong>첨부파일:</strong>
            <a href="/uploads/${inquiry.fileName}" download>${inquiry.fileName}</a>
        </div>
    </c:if>

    <c:if test="${inquiry.adminComment != null}">
        <div style="margin-top:20px;">
            <strong>📢 관리자 답변:</strong><br>
            <div style="background:#f0f0f0; padding:10px;">${inquiry.adminComment}</div>
        </div>
    </c:if>

    <c:if test="${sessionScope.loginUser != null && sessionScope.loginUser.role eq 'ADMIN'}">
        <form action="/inquiry/reply/${inquiry.id}" method="post">
            <textarea name="adminComment" placeholder="관리자 답변 작성..." required></textarea>
            <button type="submit">답변 등록</button>
        </form>
    </c:if>

    <!-- 🔧 여기만 수정 -->
    <c:if test="${sessionScope.loginUser.name eq inquiry.username}">
        <a href="/inquiry/edit/${inquiry.id}">수정</a> |
        <a href="/inquiry/delete/${inquiry.id}" onclick="return confirm('정말 삭제하시겠습니까?');">삭제</a>
    </c:if>
</div>
</body>
</html>