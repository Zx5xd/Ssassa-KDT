<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>아이디(이메일) 찾기</title>
  <meta charset="UTF-8">
  <style>
    body {
      font-family: Arial, sans-serif;
    }
    .container {
      width: 300px;
      margin: 50px auto;
    }
    label {
      display: block;
      margin-top: 10px;
    }
    input[type="text"] {
      width: 100%;
      padding: 6px;
      margin-top: 5px;
    }
    button {
      margin-top: 15px;
      padding: 8px 16px;
    }
    .message {
      margin-top: 10px;
      font-weight: bold;
    }
    .error {
      color: red;
    }
    .success {
      color: green;
    }
  </style>
  <script>
    function formatPhone(input) {
      input.value = input.value
              .replace(/[^0-9]/g, '')
              .replace(/^(\d{3})(\d{0,4})(\d{0,4})$/, function (_, a, b, c) {
                return [a, b, c].filter(Boolean).join('-');
              })
              .substring(0, 13);
    }

    window.onload = function () {
      const errorEl = document.getElementById("errorMessage");
      if (errorEl) {
        setTimeout(() => {
          errorEl.style.display = "none";
        }, 3000);
      }
    };
  </script>
</head>
<body>
<div class="container">
  <h2>아이디(이메일) 찾기</h2>

  <form method="post" action="/find-id">
    <label>
      이름:
      <input type="text" name="name" required minlength="2"
             pattern=".{2,}" title="이름은 2자 이상 입력해주세요.">
    </label>

    <label>
      전화번호:
      <input type="text" name="phone" required maxlength="13"
             oninput="formatPhone(this)"
             pattern="010-[0-9]{4}-[0-9]{4}"
             title="010-1234-5678 형식으로 입력해주세요.">
    </label>

    <button type="submit">아이디 찾기</button>
  </form>

  <c:if test="${not empty foundEmail}">
    <p class="message success">가입된 이메일: <b>${foundEmail}</b></p>
  </c:if>

  <c:if test="${not empty error}">
    <p id="errorMessage" class="message error">${error}</p>
  </c:if>

  <p><a href="/login">로그인으로 돌아가기</a></p>
</div>
</body>
</html>
