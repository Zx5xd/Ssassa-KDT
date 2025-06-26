<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>

<html>
<head>
    <title>문의 작성</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />"></head>
<body>

<div class="form-container">
    <h2>문의 작성</h2>
    <form action="/inquiry/write" method="post">
        <div class="form-group">
            <label class="form-label" for="title">제목</label>
            <input class="form-input" type="text" id="title" name="title" required>
        </div>
        <div class="form-group">
            <label class="form-label" for="username">작성자</label>
            <input class="form-input" type="text" id="username" name="username" required>
        </div>
        <div class="form-group">
            <label class="form-label" for="content">내용</label>
            <textarea class="form-textarea" id="content" name="content" required></textarea>
        </div>
        <button type="submit" class="btn-submit">등록</button>
    </form>
</div>

</body>
</html>