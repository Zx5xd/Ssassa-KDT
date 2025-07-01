<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>로그인 - 싸싸</title>

    <!-- ✅ 폰트 및 아이콘 -->
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">

    <!-- ✅ 스타일 시트 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/auth.css">
    <style>
        .error-message {
            color: red;
            font-weight: bold;
            text-align: center;
            margin-top: 10px;
        }
    </style>
</head>
<body class="noto-sans-kr-regular">
<nav>
    <!-- 로고 클릭 시 index.jsp 이동 -->
    <a href="${pageContext.request.contextPath}/index" style="text-decoration: none;">
        <div id="logo" style="background-image: url('${pageContext.request.contextPath}/resources/Ssa-Front/assets/logo_main.png');"></div>
    </a>

    <div id="searchBox">
        <input type="text" id="searchInput" placeholder="검색할 제품을 입력해주세요">
        <div class="searchBtn">
            <span class="material-symbols-outlined">search</span>
        </div>
    </div>

    <!-- 로그인 버튼 클릭 시 /login 이동 -->
    <a href="${pageContext.request.contextPath}/login" style="text-decoration: none; color: inherit;">
        <div id="user-interface">
            <div class="login">
                <span class="material-symbols-outlined">account_box</span>
                <span class="label">로그인</span>
            </div>
        </div>
    </a>
</nav>
<main>
    <div id="container">
        <div id="header">
            <h2>로그인</h2>
        </div>

        <!-- ✅ 로그인 폼 -->
        <form id="form-box" method="post" action="/login" autocomplete="off">
            <div class="form-item">
                <div class="form-label">이메일</div>
                <div class="form-input">
                    <input type="email"
                           name="email"
                           required
                           value="<c:out value='${cookie.rememberEmail.value}'/>"
                           autocomplete="off"
                           placeholder="이메일을 입력해주세요" />
                </div>
            </div>

            <div class="form-item">
                <div class="form-label">비밀번호</div>
                <div class="form-input">
                    <input type="password" name="password" required />
                </div>
            </div>

            <!-- ✅ 아이디 저장 체크박스 -->
            <label class="form-login-status-box">
                <input type="checkbox" name="rememberEmail" id="login-status"
                       <c:if test="${not empty cookie.rememberEmail}">checked</c:if> />
                <span class="material-symbols-outlined">check_circle</span>
                <span class="label">아이디 저장</span>
            </label>

            <!-- ✅ 에러 메시지 출력 -->
            <c:if test="${not empty error}">
                <p class="error-message" id="errorMessage">${error}</p>
            </c:if>

            <div class="form-button">
                <button class="login" type="submit">로그인</button>
            </div>

            <div class="form-bottom">
                <a href="/register">회원가입</a>
                <a href="/find-id">아이디 찾기</a>
                <a href="/find-password">비밀번호 찾기</a>
            </div>
        </form>
    </div>
</main>

<!-- ✅ 에러 메시지 자동 숨김 -->
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
