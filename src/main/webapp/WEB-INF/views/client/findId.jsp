<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>아이디(이메일) 찾기 - 싸싸</title>

  <!--  폰트 및 아이콘 -->
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">

  <!--  스타일 시트 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/auth.css">

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
<body class="noto-sans-kr-regular">

<main>
  <div id="container">
    <div id="header">
      <h2>아이디(이메일) 찾기</h2>
    </div>

    <form id="form-box" method="post" action="/find-id">
      <div class="form-item">
        <div class="form-label">이름</div>
        <div class="form-input">
          <input type="text" name="name" required minlength="2"
                 pattern=".{2,}" title="이름은 2자 이상 입력해주세요.">
        </div>
      </div>

      <div class="form-item">
        <div class="form-label">전화번호</div>
        <div class="form-input">
          <input type="text" name="phone" required maxlength="13"
                 oninput="formatPhone(this)"
                 pattern="010-[0-9]{4}-[0-9]{4}"
                 title="010-1234-5678 형식으로 입력해주세요.">
        </div>
      </div>

      <div class="form-button">
        <button class="login" type="submit">아이디 찾기</button>
      </div>

      <c:if test="${not empty foundEmail}">
        <p class="message success">가입된 이메일: <b>${foundEmail}</b></p>
      </c:if>

      <c:if test="${not empty error}">
        <p id="errorMessage" class="message error">${error}</p>
      </c:if>

      <div class="form-bottom">
        <a href="/login">로그인으로 돌아가기</a>
      </div>
    </form>
  </div>
</main>

</body>
</html>
