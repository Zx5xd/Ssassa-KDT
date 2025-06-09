<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
    <h2>회원가입</h2>

    <c:if test="${not empty error}">
        <p style="color:red">${error}</p>
    </c:if>

    <form method="post" action="/member/register">
        이메일: <input type="email" name="email" required><br>
        비밀번호: <input type="password" name="password" required><br>
        이름: <input type="text" name="name" required><br>
        닉네임: <input type="text" name="nickname" required><br>
        전화번호: <input type="text" name="phone" required><br>
        <input type="hidden" name="role" value="USER">
        <button type="submit">가입</button>
    </form>
</body>
</html>
