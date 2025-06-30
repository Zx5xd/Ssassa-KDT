<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입 - 싸싸</title>

    <!--  구글 폰트 & 아이콘 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">

    <!--  외부 스타일 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/auth.css">
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
    <div id="container" style="gap: var(--size-3)">
        <div id="header">
            <h4>회원가입</h4>
        </div>

        <!--  회원가입 폼 -->
        <div class="grid-form-wrapper">
            <form class="grid-form" method="post" action="/register" onsubmit="return validateForm()">
                <!-- 이메일 -->
                <label for="email-id">이메일</label>
                <div class="input-wrap">
                    <input type="text" id="email-id" required>
                    <span>@</span>
                    <select id="email-domain">
                        <option value="naver.com">naver.com</option>
                        <option value="gmail.com">gmail.com</option>
                    </select>
                </div>
                <span class="material-symbols-outlined icon send" onclick="sendAuthCode()">send</span>
                <input type="hidden" name="email" id="full-email">

                <!-- 인증코드 -->
                <label for="code">인증코드</label>
                <input type="text" id="code" name="code" required>
                <span class="material-symbols-outlined icon check" onclick="verifyCode()">check</span>

                <!-- 비밀번호 -->
                <label for="pw">비밀번호</label>
                <input type="password" id="pw" name="password" required>
                <div></div>

                <!-- 비밀번호 확인 -->
                <label for="pw2">비밀번호 확인</label>
                <input type="password" id="pw2" name="confirmPassword" required oninput="checkPasswordMatch()">
                <div id="pw-msg" style="font-size: 12px; color: red;"></div>

                <!-- 이름 -->
                <label for="name">이름</label>
                <input type="text" id="name" name="name" required>
                <div></div>

                <!-- 닉네임 -->
                <label for="nickname">닉네임</label>
                <div class="input-wrap">
                    <input type="text" id="nickname" name="nickname" required>
                    <button type="button" onclick="checkNickname()">중복확인</button>
                </div>
                <div id="nickname-msg" style="font-size: 12px; color: red;"></div>

                <!-- 전화번호 -->
                <label for="tel">전화번호</label>
                <input type="tel" id="tel" name="phone" required>
                <div></div>

                <!-- 에러 메시지 -->
                <c:if test="${not empty error}">
                    <div class="error-message" id="errorMessage">${error}</div>
                </c:if>

                <!-- 버튼 -->
                <div></div>
                <div class="form-buttons">
                    <button class="submit" type="submit">회원가입</button>
                    <button class="cancel" type="button" onclick="location.href='/login'">취소</button>
                </div>
                <div></div>
            </form>
        </div>
    </div>
</main>

<!--  스크립트 -->
<script>
    function validateForm() {
        const emailId = document.getElementById("email-id").value.trim();
        const emailDomain = document.getElementById("email-domain").value;
        const pw = document.getElementById("pw").value;
        const pw2 = document.getElementById("pw2").value;
        const name = document.getElementById("name").value.trim();

        document.getElementById("full-email").value = emailId + "@" + emailDomain;

        if (pw !== pw2) {
            alert("비밀번호가 일치하지 않습니다.");
            return false;
        }

        if (name.length < 2) {
            alert("이름은 최소 2글자 이상 입력해야 합니다.");
            return false;
        }

        return true;
    }

    function sendAuthCode() {
        const emailId = document.getElementById("email-id").value.trim();
        const emailDomain = document.getElementById("email-domain").value;
        const fullEmail = emailId + "@" + emailDomain;

        if (!emailId) {
            alert("이메일을 입력해주세요.");
            return;
        }

        fetch("/send-auth-code?email=" + encodeURIComponent(fullEmail))
            .then(response => response.text())
            .then(message => alert(message))
            .catch(() => alert("인증 코드 요청 중 오류가 발생했습니다."));
    }

    function verifyCode() {
        const emailId = document.getElementById("email-id").value.trim();
        const emailDomain = document.getElementById("email-domain").value;
        const code = document.getElementById("code").value.trim();
        const fullEmail = emailId + "@" + emailDomain;

        if (!code) {
            alert("인증코드를 입력해주세요.");
            return;
        }

        fetch("/verify-code?email=" + encodeURIComponent(fullEmail) + "&code=" + encodeURIComponent(code))
            .then(response => response.text())
            .then(result => {
                if (result === "true") {
                    alert("인증 성공!");
                } else {
                    alert("인증 실패. 코드를 확인해주세요.");
                }
            })
            .catch(() => alert("인증 확인 중 오류가 발생했습니다."));
    }

    function checkNickname() {
        const nickname = document.getElementById("nickname").value.trim();
        const msgEl = document.getElementById("nickname-msg");

        if (!nickname) {
            msgEl.innerText = "닉네임을 입력해주세요.";
            return;
        }

        fetch("/check-nickname?nickname=" + encodeURIComponent(nickname))
            .then(res => res.text())
            .then(result => {
                if (result === "true") {
                    msgEl.style.color = "red";
                    msgEl.innerText = "이미 사용 중인 닉네임입니다.";
                } else {
                    msgEl.style.color = "green";
                    msgEl.innerText = "사용 가능한 닉네임입니다.";
                }
            });
    }

    function checkPasswordMatch() {
        const pw = document.getElementById("pw").value;
        const pw2 = document.getElementById("pw2").value;
        const msg = document.getElementById("pw-msg");

        if (!pw2) {
            msg.innerText = "";
            return;
        }

        msg.innerText = pw === pw2 ? "비밀번호가 일치합니다." : "비밀번호가 다릅니다.";
        msg.style.color = pw === pw2 ? "green" : "red";
    }

    window.onload = function () {
        const errorEl = document.getElementById("errorMessage");
        if (errorEl) {
            setTimeout(() => errorEl.style.display = "none", 3000);
        }

        // 전화번호 하이픈 자동 삽입
        document.getElementById("tel").addEventListener("input", function (e) {
            let value = e.target.value.replace(/[^0-9]/g, "");
            if (value.length >= 11) {
                value = value.replace(/^(\d{3})(\d{4})(\d{4})$/, "$1-$2-$3");
            } else if (value.length >= 7) {
                value = value.replace(/^(\d{3})(\d{3,4})(\d{0,4})$/, "$1-$2-$3");
            } else if (value.length >= 4) {
                value = value.replace(/^(\d{3})(\d{0,4})$/, "$1-$2");
            }
            e.target.value = value;
        });
    };
</script>

</body>
</html>
