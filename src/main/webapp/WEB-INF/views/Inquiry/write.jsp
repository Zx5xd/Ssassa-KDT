<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>문의 작성</title>
</head>
<body>
<h2>문의 작성</h2>
<form action="/inquiry/write" method="post" enctype="multipart/form-data">
    <input type="text" name="title" placeholder="제목" required>
    <textarea name="content" placeholder="내용" required></textarea>
    <input type="file" name="file">
    <button type="submit">등록</button>
</form>
</body>
</html>