<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>회원가입</title>
    <meta charset="UTF-8">
    <script>
        // 이메일 인증 코드 전송
        function sendAuthCode() {
            const email = document.getElementById("email").value;
            if (!email) {
                alert("이메일을 입력하세요.");
                return;
            }

            fetch("/send-auth-code?email=" + encodeURIComponent(email))
                .then(res => res.text())
                .then(msg => alert(msg))
                .catch(() => alert("서버 오류로 인증 코드 전송에 실패했습니다."));
        }

        // 이메일 인증 코드 확인
        function verifyAuthCode() {
            const email = document.getElementById("email").value;
            const code = document.getElementById("authCodeInput").value;

            if (!code) {
                alert("인증 코드를 입력하세요.");
                return;
            }

            fetch("/verify-code?email=" + encodeURIComponent(email) + "&code=" + encodeURIComponent(code))
                .then(res => res.text())
                .then(result => {
                    if (result === "true") {
                        document.getElementById("authResult").innerText = "이메일 인증 성공!";
                        document.getElementById("authResult").style.color = "green";
                        document.getElementById("emailVerified").value = "true";
                    } else {
                        document.getElementById("authResult").innerText = "인증 실패. 인증코드를 확인하세요.";
                        document.getElementById("authResult").style.color = "red";
                        document.getElementById("emailVerified").value = "false";
                    }
                })
                .catch(() => alert("서버 오류로 인증 확인에 실패했습니다."));
        }

        // 닉네임 중복 체크
        function checkNickname() {
            const nickname = document.getElementById("nickname").value;
            if (!nickname) return;

            fetch("/check-nickname?nickname=" + encodeURIComponent(nickname))
                .then(res => res.text())
                .then(result => {
                    const resultEl = document.getElementById("nicknameResult");
                    if (result === "true") {
                        resultEl.innerText = "이미 사용 중인 닉네임입니다.";
                        resultEl.style.color = "red";
                    } else {
                        resultEl.innerText = "사용 가능한 닉네임입니다.";
                        resultEl.style.color = "green";
                    }
                });
        }

        // 전화번호 자동 하이픈 입력
        function formatPhone(input) {
            input.value = input.value
                .replace(/[^0-9]/g, '')
                .replace(/^(\d{3})(\d{0,4})(\d{0,4})$/, function(_, a, b, c) {
                    return [a, b, c].filter(Boolean).join('-');
                })
                .substring(0, 13); // 최대 길이 제한
        }

        // 회원가입 전 이메일 인증 확인
        function validateForm() {
            const verified = document.getElementById("emailVerified").value;
            if (verified !== "true") {
                alert("이메일 인증을 완료해야 가입할 수 있습니다.");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
<h2>회원가입</h2>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<form method="post" action="/register" onsubmit="return validateForm()">
    이메일:
    <input type="email" id="email" name="email" required>
    <button type="button" onclick="sendAuthCode()">인증 코드 발송</button><br><br>

    인증 코드 입력:
    <input type="text" id="authCodeInput">
    <button type="button" onclick="verifyAuthCode()">인증 확인</button>
    <div id="authResult" style="margin-top: 5px;"></div><br>

    <input type="hidden" id="emailVerified" name="emailVerified" value="false">

    비밀번호: <input type="password" name="password" required><br>

    이름:
    <input type="text" name="name" required minlength="2"
           pattern=".{2,}" title="이름은 최소 2자 이상이어야 합니다."><br>

    닉네임:
    <input type="text" name="nickname" id="nickname" required onblur="checkNickname()">
    <span id="nicknameResult" style="margin-left: 10px;"></span><br>

    전화번호:
    <input type="text" name="phone" required maxlength="13"
           oninput="formatPhone(this)"
           pattern="010-[0-9]{4}-[0-9]{4}"
           title="010-1234-5678 형식으로 입력해주세요."><br>

    <input type="hidden" name="role" value="USER"><br><br>

    <button type="submit">가입</button>
</form>
</body>
</html>
