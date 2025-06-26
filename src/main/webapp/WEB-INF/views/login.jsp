<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>로그인</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .login-container {
            width: 300px;
            margin: 50px auto;
        }
        .error-message {
            color: red;
            font-weight: bold;
        }
        label {
            display: block;
            margin-top: 10px;
        }
        input[type="email"], input[type="password"] {
            width: 100%;
            padding: 6px;
            margin-top: 5px;
        }
        button {
            margin-top: 15px;
            padding: 8px 16px;
        }
        .link-group {
            margin-top: 15px;
        }
        .link-group a {
            margin-right: 10px;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h2>로그인</h2>

    <!-- ✅ 에러 메시지 출력 -->
    <c:if test="${not empty error}">
        <p class="error-message" id="errorMessage">${error}</p>
    </c:if>

    <!-- ✅ 로그인 폼 -->
    <form method="post" action="/login">
        <label>
            이메일:
            <input type="email" name="email"
                   value="${cookie.rememberEmail.value}" required />
        </label>

        <label>
            비밀번호:
            <input type="password" name="password" required />
        </label>

        <!-- ✅ 아이디 저장 체크박스 -->
        <label>
            <input type="checkbox" name="rememberEmail"
                   <c:if test="${not empty cookie.rememberEmail}">checked</c:if> />
            아이디 저장
        </label>

        <button type="submit">로그인</button>
    </form>

    <!-- ✅ 회원가입 및 아이디/비밀번호 찾기 링크 -->
    <div class="link-group">
        <p>
            <a href="/register">회원가입</a> |
            <a href="/find-id">아이디(이메일) 찾기</a> |
            <a href="/find-password">비밀번호 찾기</a>
        </p>
    </div>
</div>

<!-- ✅ 에러 메시지 3초 후 자동 숨김 -->
<script>
    const errorEl = document.getElementById("errorMessage");
    if (errorEl) {
        setTimeout(() => {
            errorEl.style.display = "none";
        }, 3000);
    }
</script>
</body>
</html>
