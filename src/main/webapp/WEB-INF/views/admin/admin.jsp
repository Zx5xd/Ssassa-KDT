<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>관리자 페이지 - 싸싸</title>

  <!-- ✅ 폰트 및 아이콘 -->
  <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">

  <!-- ✅ 외부 CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/admin.css">
</head>
<body class="noto-sans-kr-regular">

<main>
  <div class="admin-container">
    <h2 class="admin-title">
      <span class="material-symbols-outlined">admin_panel_settings</span>
      관리자님 환영합니다, <span class="admin-name">${adminName}</span>!
    </h2>

    <div class="admin-card">
      <ul class="admin-menu">
        <li><a href="/admin/users">👥 회원 목록 관리</a></li>
        <li><a href="/admin/products">🛍️ 상품 관리</a></li>
        <li><a href="#">📂 카테고리 관리</a></li> <!-- ✅ 추가됨 -->
        <li><a href="/admin/inquiries">📩 환불 및 문의사항 관리</a></li>
        <li><a href="/logout">🔓 로그아웃</a></li>
      </ul>
    </div>
  </div>
</main>

</body>
</html>
