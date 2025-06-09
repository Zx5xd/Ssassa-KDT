<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head><title>문의 작성</title></head>
<body>
<h2>문의 작성</h2>
<form action="/inquiry/write" method="post">
    제목: <input type="text" name="title"/><br/>
    작성자: <input type="text" name="username"/><br/>
    내용: <textarea name="content"></textarea><br/>
    <input type="submit" value="등록"/>
</form>
</body>
</html>