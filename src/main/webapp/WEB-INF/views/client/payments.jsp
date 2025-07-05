<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>결제내역 - 싸싸</title>

  <!--  폰트 및 아이콘 -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">

  <!--  외부 CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/common.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/profile.css">


  <style>
    main {
      max-width: 1200px;
      margin: 140px auto 80px;
      padding: 0 20px;
    }

    .payments-header {
      text-align: center;
      margin-bottom: 40px;
    }

    .payments-header h1 {
      font-size: 32px;
      margin-bottom: 10px;
      color: var(--text-1);
    }

    .payments-header p {
      color: var(--text-2);
      font-size: 16px;
    }

    .payments-container {
      background-color: rgba(255, 255, 255, 0.04);
      border-radius: 12px;
      box-shadow: 0 0 10px rgba(0,0,0,0.3);
      padding: 30px;
    }

    .payment-item {
      border: 1px solid rgba(255, 255, 255, 0.1);
      border-radius: 8px;
      padding: 20px;
      margin-bottom: 20px;
      background-color: rgba(255, 255, 255, 0.08);
      transition: all 0.3s ease;
    }

    .payment-item:hover {
      background-color: rgba(255, 255, 255, 0.12);
      transform: translateY(-2px);
    }

    .payment-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 15px;
      flex-wrap: wrap;
      gap: 10px;
    }

    .payment-title {
      margin: 0;
      color: var(--text-1);
      font-size: 18px;
      font-weight: bold;
    }

    .payment-status {
      padding: 6px 12px;
      border-radius: 6px;
      font-size: 12px;
      font-weight: bold;
      white-space: nowrap;
    }

    .status-success {
      background-color: #28a745;
      color: white;
    }

    .status-refund-request {
      background-color: #ffc107;
      color: #212529;
    }

    .status-refunded {
      background-color: #17a2b8;
      color: white;
    }

    .status-cancelled {
      background-color: #6c757d;
      color: white;
    }

    .payment-details {
      color: var(--text-2);
      font-size: 14px;
      line-height: 1.6;
    }

    .payment-details p {
      margin: 8px 0;
    }

    .payment-amount {
      color: var(--brand);
      font-weight: bold;
      font-size: 16px;
    }

    .payment-actions {
      margin-top: 15px;
      display: flex;
      gap: 10px;
      flex-wrap: wrap;
    }

    .btn {
      padding: 8px 16px;
      border-radius: 6px;
      text-decoration: none;
      font-size: 14px;
      font-weight: 500;
      transition: all 0.3s ease;
      border: none;
      cursor: pointer;
    }

    .btn-refund {
      background-color: #dc3545;
      color: white;
    }

    .btn-refund:hover {
      background-color: #c82333;
    }

    .btn-secondary {
      background-color: #6c757d;
      color: white;
    }

    .btn-secondary:hover {
      background-color: #5a6268;
    }

    .empty-state {
      text-align: center;
      padding: 60px 20px;
      color: var(--text-2);
    }

    .empty-state .material-symbols-outlined {
      font-size: 64px;
      margin-bottom: 20px;
      opacity: 0.5;
    }

    .back-button {
      display: inline-flex;
      align-items: center;
      gap: 8px;
      margin-bottom: 20px;
      color: var(--text-2);
      text-decoration: none;
      font-size: 14px;
      transition: color 0.3s ease;
    }

    .back-button:hover {
      color: var(--brand);
    }

    @media (max-width: 768px) {
      .payment-header {
        flex-direction: column;
        align-items: flex-start;
      }
      
      .payment-actions {
        flex-direction: column;
      }
      
      .btn {
        text-align: center;
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
        <!--  로그인 상태 -->
        <div class="profile-wrapper" onclick="toggleProfileMenu(event)">
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

          <!--  드롭다운 메뉴 (초기 숨김) -->
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
        <!--  비로그인 상태 -->
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

<!--  결제내역 본문 -->
<main>
  <a href="/mypage" class="back-button">
    <span class="material-symbols-outlined">arrow_back</span>
    마이페이지로 돌아가기
  </a>

  <div class="payments-header">
    <h1>💳 결제내역</h1>
    <p>${sessionScope.loginUser.nickname}님의 모든 결제 내역을 확인하세요</p>
  </div>

  <div class="payments-container">
    <c:choose>
      <c:when test="${not empty payments}">
        <c:forEach var="payment" items="${payments}">
          <div class="payment-item">
            <div class="payment-header">
              <h3 class="payment-title">${payment.itemName}</h3>
              <span class="payment-status 
                <c:choose>
                  <c:when test="${payment.status == 'SUCCESS'}">status-success</c:when>
                  <c:when test="${payment.status == 'REFUND_REQUEST'}">status-refund-request</c:when>
                  <c:when test="${payment.status == 'REFUNDED'}">status-refunded</c:when>
                  <c:otherwise>status-cancelled</c:otherwise>
                </c:choose>">
                <c:choose>
                  <c:when test="${payment.status == 'SUCCESS'}">결제 완료</c:when>
                  <c:when test="${payment.status == 'REFUND_REQUEST'}">환불 요청 중</c:when>
                  <c:when test="${payment.status == 'REFUNDED'}">환불 완료</c:when>
                  <c:otherwise>${payment.status}</c:otherwise>
                </c:choose>
              </span>
            </div>
            
            <div class="payment-details">
              <p><strong>결제 번호:</strong> ${payment.id}</p>
              <p><strong>결제 금액:</strong> <span class="payment-amount">${payment.amount}원</span></p>
              <p><strong>결제 일시:</strong> ${payment.createdAt}</p>
            </div>
            
            <div class="payment-actions">
              <c:if test="${payment.status == 'SUCCESS'}">
                <a href="/refund/request?paymentId=${payment.id}" class="btn btn-refund">
                  환불 요청
                </a>
              </c:if>
              <c:if test="${payment.status == 'REFUND_REQUEST'}">
                <span class="btn btn-secondary" style="cursor: default;">
                  환불 요청 처리 중
                </span>
              </c:if>
            </div>
          </div>
        </c:forEach>
      </c:when>
      <c:otherwise>
        <div class="empty-state">
          <span class="material-symbols-outlined">receipt_long</span>
          <h3>결제 내역이 없습니다</h3>
          <p>아직 결제한 상품이 없습니다.<br>상품을 구매해보세요!</p>
          <a href="/pd/list" class="btn" style="background-color: var(--brand); color: white; margin-top: 20px;">
            상품 둘러보기
          </a>
        </div>
      </c:otherwise>
    </c:choose>
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