<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>문의 수정</title>
</head>
<body>
<h2>문의 수정</h2>
<form action="/inquiry/edit/${inquiry.id}" method="post" enctype="multipart/form-data">
    제목: <input type="text" name="title" value="${inquiry.title}" required><br>
    내용: <textarea name="content" required>${inquiry.content}</textarea><br>
    현재 첨부파일: <c:if test="${inquiry.fileName != null}"><a href="/uploads/${inquiry.fileName}" download>${inquiry.fileName}</a></c:if><br>
    새 첨부파일: <input type="file" name="file"><br>
    <button type="submit">수정 완료</button>
</form>
</body>
</html>