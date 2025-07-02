<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>회원 정보 수정</title>

  <!--  index.css : 전체 배경 및 전역 스타일 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">

  <!--  editUser.css : 폼 구성 및 버튼 스타일 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/user-edit.css">
</head>
<body class="edit-body"> <!-- index.css 배경 그대로 적용 -->

<div class="edit-container">
  <h2 class="edit-title">회원 정보 수정</h2>

  <form method="post" action="/admin/updateUser" class="edit-form">
    <input type="hidden" name="email" value="${user.email}">

    <label>이름
      <input type="text" name="name" value="${user.name}" readonly>
    </label>

    <label>닉네임
      <input type="text" name="nickname" value="${user.nickname}" required>
    </label>

    <label>전화번호
      <input type="text" name="phone" value="${user.phone}" oninput="formatPhone(this)" required>
    </label>

    <button type="submit" class="submit-btn">수정 완료</button>
  </form>

  <form method="post" action="/admin/sendTempPassword" class="temp-form">
    <input type="hidden" name="email" value="${user.email}">
    <button type="submit" class="temp-btn">임시 비밀번호 전송</button>
  </form>

  <a href="/admin/users" class="link-button">← 목록으로 돌아가기</a>
</div>

<script>
  function formatPhone(input) {
    const value = input.value.replace(/[^0-9]/g, '');
    if (value.length < 4) {
      input.value = value;
    } else if (value.length < 8) {
      input.value = value.slice(0, 3) + '-' + value.slice(3);
    } else {
      input.value = value.slice(0, 3) + '-' + value.slice(3, 7) + '-' + value.slice(7, 11);
    }
  }
</script>

</body>
</html>
