<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>새 비밀번호 설정</title>

  <!-- ✅ 외부 CSS 연결 -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/auth.css">
</head>
<body class="noto-sans-kr-regular">

<main>
  <div class="grid-form-wrapper">
    <form method="post" action="/reset-password" class="grid-form">

      <input type="hidden" name="email" value="${email}" />

      <label for="newPassword">새 비밀번호</label>
      <div class="input-wrap">
        <input type="password" id="newPassword" name="newPassword" required placeholder="새 비밀번호를 입력하세요" />
      </div>
      <div class="form-buttons">
        <button type="submit" class="submit">비밀번호 변경</button>
        <a href="/login" class="cancel" style="text-align: center; padding: 10px; display: block; border-radius: 8px; text-decoration: none;">로그인으로 돌아가기</a>
      </div>

    </form>
  </div>
</main>

</body>
</html>
