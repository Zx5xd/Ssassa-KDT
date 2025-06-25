<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>회원 정보 수정 - 싸싸</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/profile.css">
</head>
<body class="noto-sans-kr-regular">

<!-- ✅ 메뉴바 영역 -->
<nav>
  <a href="${pageContext.request.contextPath}/index" style="text-decoration: none;">
    <div id="logo" style="background-image: url('${pageContext.request.contextPath}/resources/Ssa-Front/assets/logo_main.png');"></div>
  </a>

  <div id="searchBox">
    <input type="text" id="searchInput" placeholder="검색할 제품을 입력해주세요">
    <div class="searchBtn">
      <span class="material-symbols-outlined">search</span>
    </div>
  </div>

  <div id="user-interface">
    <c:choose>
      <c:when test="${not empty sessionScope.loginUser}">
        <div class="profile-wrapper" onclick="toggleProfileMenu()">
          <c:choose>
            <c:when test="${not empty sessionScope.loginUser.profileImage}">
              <img class="navbar-profile-img"
                   src="${pageContext.request.contextPath}${sessionScope.loginUser.profileImage}" alt="프로필" />
            </c:when>
            <c:otherwise>
              <div class="login" style="display: flex; align-items: center; cursor: pointer;">
                <span class="material-symbols-outlined">account_circle</span>
                <span class="label">${sessionScope.loginUser.nickname}님</span>
              </div>
            </c:otherwise>
          </c:choose>
          <div id="profile-menu" class="hidden">
            <div class="menu-header">
              <strong>${sessionScope.loginUser.nickname}님</strong>
            </div>
            <ul class="menu-links">
              <li><a href="/mypage">마이페이지</a></li>
              <li><a href="#">장바구니</a></li>
              <li><a href="/logout">로그아웃</a></li>
            </ul>
          </div>
        </div>
      </c:when>
      <c:otherwise>
        <a href="${pageContext.request.contextPath}/login" style="text-decoration: none; color: inherit;">
          <div class="login">
            <span class="material-symbols-outlined">account_box</span>
            <span class="label">로그인</span>
          </div>
        </a>
      </c:otherwise>
    </c:choose>
  </div>
</nav>

<!-- ✅ 회원정보 수정 영역 -->
<div class="edit-container">
  <div class="edit-box">
    <h2>회원 정보 수정</h2>
    <c:if test="${not empty error}">
      <p style="color: #ff4d4d; font-weight: bold;">${error}</p>
    </c:if>
    <form method="post" action="/mypage/update" enctype="multipart/form-data">
      <input type="hidden" name="email" value="${user.email}">
      <div class="profile-box">
        <c:choose>
          <c:when test="${not empty user.profileImage}">
            <img id="preview" class="preview"
                 src="${pageContext.request.contextPath}${user.profileImage}" alt="프로필 이미지" />
          </c:when>
          <c:otherwise>
            <div id="preview" class="default-profile">기본<br>프로필<br>이미지</div>
          </c:otherwise>
        </c:choose>
        <div class="profile-label">프로필 이미지</div>
        <input type="file" name="profileImage" accept="image/*" onchange="previewImage(event)" />
      </div>
      <p><strong>이메일:</strong> <input type="text" value="${user.email}" readonly /></p>
      <p><strong>이름:</strong> <input type="text" value="${user.name}" readonly /></p>
      <p><strong>닉네임:</strong> <input type="text" name="nickname" value="${user.nickname}" required /></p>
      <p><strong>비밀번호:</strong> <input type="password" name="password" placeholder="비밀번호 변경 시에만 입력" /></p>
      <p><strong>전화번호:</strong> <input type="text" name="phone" value="${user.phone}" oninput="formatPhone(this)" required /></p>
      <div style="margin-top: 20px;">
        <button type="submit" class="btn" style="margin-right: 10px;">수정 완료</button>
        <a href="/mypage" class="btn danger">마이페이지로</a>
      </div>
    </form>
    <c:if test="${not empty user.profileImage}">
      <form method="post" action="/mypage/profile/delete">
        <button type="submit" class="btn secondary">이미지 삭제</button>
      </form>
    </c:if>
  </div>
</div>

<!-- ✅ 스크립트 -->
<script>
  function toggleProfileMenu() {
    const menu = document.getElementById("profile-menu");
    menu.classList.toggle("hidden");
  }

  document.addEventListener("click", function (e) {
    const menu = document.getElementById("profile-menu");
    const wrapper = document.querySelector(".profile-wrapper");
    if (!wrapper.contains(e.target)) {
      menu.classList.add("hidden");
    }
  });

  function formatPhone(input) {
    let value = input.value.replace(/[^0-9]/g, '');
    if (value.length < 4) {
      input.value = value;
    } else if (value.length < 8) {
      input.value = value.slice(0, 3) + '-' + value.slice(3);
    } else {
      input.value = value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7, 11);
    }
  }

  function previewImage(event) {
    const file = event.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = function () {
      const preview = document.getElementById('preview');
      if (preview.tagName === 'DIV') {
        const newImg = document.createElement('img');
        newImg.id = 'preview';
        newImg.className = 'preview';
        newImg.src = reader.result;
        preview.parentNode.replaceChild(newImg, preview);
      } else {
        preview.src = reader.result;
      }
    };
    reader.readAsDataURL(file);
  }
</script>
<script src="${pageContext.request.contextPath}/resources/Ssa-Front/js/slide.js"></script>
<script src="${pageContext.request.contextPath}/resources/Ssa-Front/js/SsaComponent.js"></script>
</body>
</html>
