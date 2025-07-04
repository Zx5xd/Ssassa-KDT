<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>마이페이지 - 싸싸</title>

  <!--  폰트 및 아이콘 -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">

  <!--  외부 CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/profile.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/common.css">

  <style>
    main {
      max-width: 1200px;
      margin: 140px auto 80px; /*  더 아래로 내림 */
      padding: 0 20px;
    }

    .mypage-container {
      display: flex;
      justify-content: center;
      align-items: center; /*  수직 가운데 정렬 */
      gap: 60px; /*  여백 조금 늘림 */
      background-color: rgba(255, 255, 255, 0.04);
      border-radius: 12px;
      box-shadow: 0 0 10px rgba(0,0,0,0.3);
      padding: 50px;
      font-size: 17px;
    }

    .profile-section {
      flex: 0 0 250px;
      text-align: center;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100%;
    }

    .default-profile {
      width: 200px;
      height: 200px;
      border-radius: 50%;
      border: 2px solid #888;
      display: flex;
      justify-content: center;
      align-items: center;
      color: #ccc;
      font-size: 18px;
      text-align: center;
    }

    .info-section {
      flex: 1;
    }

    .info-section h2 {
      font-size: 28px;
      margin-bottom: 20px;
    }

    .info-section p {
      margin-bottom: 12px;
    }

    .btn {
      padding: 12px 24px;
      font-size: 16px;
      margin-right: 10px;
    }

    .btn.danger {
      background-color: #e74c3c;
      color: white;
    }

    .btn.secondary {
      background-color: #999;
      color: white;
    }
    
    /* 버튼 그룹 스타일 */
    .button-group {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
      gap: 15px;
      margin-top: 30px;
    }
    
    .btn {
      display: flex;
      align-items: center;
      justify-content: center;
      gap: 8px;
      padding: 12px 20px;
      font-size: 14px;
      font-weight: 500;
      border-radius: 8px;
      text-decoration: none;
      border: none;
      cursor: pointer;
      transition: all 0.3s ease;
      min-height: 44px;
    }
    
    .btn:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
    }
    
    .btn-primary {
      background-color: var(--brand);
      color: white;
    }
    
    .btn-primary:hover {
      background-color: #d4a574;
    }
    
    .btn-secondary {
      background-color: #6c757d;
      color: white;
    }
    
    .btn-secondary:hover {
      background-color: #5a6268;
    }
    
    .btn-info {
      background-color: #17a2b8;
      color: white;
    }
    
    .btn-info:hover {
      background-color: #138496;
    }
    
    .btn-warning {
      background-color: #ffc107;
      color: #212529;
    }
    
    .btn-warning:hover {
      background-color: #e0a800;
    }
    
    .btn-danger {
      background-color: #dc3545;
      color: white;
    }
    
    .btn-danger:hover {
      background-color: #c82333;
    }
    
    .material-symbols-outlined {
      font-size: 18px;
    }
    
    @media (max-width: 768px) {
      .button-group {
        grid-template-columns: 1fr;
        gap: 12px;
      }
      
      .btn {
        font-size: 16px;
        padding: 14px 20px;
      }
    }
  </style>
</head>

<body class="noto-sans-kr-regular">

<!--  메뉴바 -->
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
                   src="${pageContext.request.contextPath}${sessionScope.loginUser.profileImage}" alt="프로필">
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

<!--  마이페이지 본문 -->
<main>
  <div class="mypage-container">
    <div class="profile-section">
      <c:choose>
        <c:when test="${not empty sessionScope.loginUser.profileImage}">
          <img src="${pageContext.request.contextPath}${sessionScope.loginUser.profileImage}" alt="프로필 이미지">
        </c:when>
        <c:otherwise>
          <div class="default-profile">기본 프로필<br>이미지</div>
        </c:otherwise>
      </c:choose>
    </div>

    <div class="info-section">
      <h2>마이페이지</h2>
      <p><strong>이메일:</strong> ${sessionScope.loginUser.email}</p>
      <p><strong>이름:</strong> ${sessionScope.loginUser.name}</p>
      <p><strong>닉네임:</strong> ${sessionScope.loginUser.nickname}</p>
      <p><strong>전화번호:</strong> ${sessionScope.loginUser.phone}</p>
      <p><strong>가입일:</strong> ${formattedCreatedAt}</p>

      <p><strong>이메일 인증 여부:</strong>
        <c:choose>
          <c:when test="${sessionScope.loginUser.emailVerified}">인증 완료</c:when>
          <c:otherwise>미인증</c:otherwise>
        </c:choose>
      </p>


      </div>
      <div>
              <!-- 버튼 그룹 -->
      <div class="button-group">
        <a href="/mypage/edit" class="btn btn-primary">
          <span class="material-symbols-outlined">edit</span>
          정보 수정
        </a>
        
        <a href="/payments" class="btn btn-secondary">
          <span class="material-symbols-outlined">receipt_long</span>
          결제내역 보기
        </a>
        
        <a href="/inquiry/list" class="btn btn-info">
          <span class="material-symbols-outlined">support_agent</span>
          문의사항
        </a>
        
        <a href="/logout" class="btn btn-warning">
          <span class="material-symbols-outlined">logout</span>
          로그아웃
        </a>
        
        <form method="post" action="/withdraw" onsubmit="return confirm('정말 탈퇴하시겠습니까?\n\n탈퇴 시 모든 데이터가 삭제되며 복구할 수 없습니다.')" style="display: inline;">
          <button type="submit" class="btn btn-danger">
            <span class="material-symbols-outlined">delete_forever</span>
            회원 탈퇴
          </button>
        </form>
      </div>
    </div>
  </div>
</main>

<!--  스크립트 -->
<script>
  function toggleProfileMenu() {
    const menu = document.getElementById("profile-menu");
    menu.classList.toggle("hidden");
  }

  document.addEventListener("click", function (e) {
    const menu = document.getElementById("profile-menu");
    const wrapper = document.querySelector(".profile-wrapper");
    if (wrapper && !wrapper.contains(e.target)) {
      menu.classList.add("hidden");
    }
  });
</script>
<script src="${pageContext.request.contextPath}/resources/Ssa-Front/js/slide.js"></script>
<script src="${pageContext.request.contextPath}/resources/Ssa-Front/js/SsaComponent.js"></script>
</body>
</html>
