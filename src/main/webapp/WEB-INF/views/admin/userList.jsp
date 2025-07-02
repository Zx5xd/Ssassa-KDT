<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>회원 목록</title>

  <!--  외부 CSS 파일 적용 (index.css 및 admin.css 등) -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/admin.css">
</head>
<body class="noto-sans-kr-regular admin-body">

<main class="admin-container">
  <section>
    <h2 class="admin-title"> 정상 회원 목록</h2>
    <table class="admin-table">
      <thead>
      <tr>
        <th>이메일</th><th>이름</th><th>닉네임</th><th>전화번호</th><th>가입일</th><th>관리</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="user" items="${activeUsers}">
        <tr>
          <td>${user.email}</td>
          <td>${user.name}</td>
          <td>${user.nickname}</td>
          <td>${user.phone}</td>
          <td>${user.createdAt}</td>
          <td>
            <form action="/admin/deleteUser" method="post" style="display:inline;">
              <input type="hidden" name="email" value="${user.email}" />
              <button class="danger-btn" onclick="return confirm('정말 삭제하시겠습니까?')">삭제</button>
            </form>
            <form action="/admin/editUser" method="get" style="display:inline;">
              <input type="hidden" name="email" value="${user.email}" />
              <button class="edit-btn">수정</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty activeUsers}">
        <tr><td colspan="6">정상 회원이 없습니다.</td></tr>
      </c:if>
      </tbody>
    </table>
  </section>

  <section style="margin-top: 60px;">
    <h2 class="admin-title">❌ 탈퇴한 회원 목록</h2>
    <table class="admin-table">
      <thead>
      <tr>
        <th>이메일</th><th>이름</th><th>닉네임</th><th>전화번호</th><th>가입일</th><th>관리</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="user" items="${deletedUsers}">
        <tr>
          <td>${user.email}</td>
          <td>${user.name}</td>
          <td>${user.nickname}</td>
          <td>${user.phone}</td>
          <td>${user.createdAt}</td>
          <td>
            <form action="/admin/restoreUser" method="post">
              <input type="hidden" name="email" value="${user.email}" />
              <button class="restore-btn" onclick="return confirm('복구하시겠습니까?')">복구</button>
            </form>
          </td>
        </tr>
      </c:forEach>
      <c:if test="${empty deletedUsers}">
        <tr><td colspan="6">탈퇴한 회원이 없습니다.</td></tr>
      </c:if>
      </tbody>
    </table>
  </section>

  <!-- 돌아가기 버튼 -->
  <div style="text-align: center; margin-top: 40px;">
    <a href="/admin" class="link-button">← 관리자 홈으로 돌아가기</a>
  </div>
</main>

</body>
</html>
