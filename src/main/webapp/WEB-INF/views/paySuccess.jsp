<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>결제 성공</title></head>
<body>
<h2>결제가 완료되었습니다!</h2>
<p>상품명: ${payment.itemName}</p>
<p>금액: ${payment.amount}원</p>
<p>상태: ${payment.status}</p>
<a href="/mypage">마이페이지로 이동</a>
</body>
</html>
