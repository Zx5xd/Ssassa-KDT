<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>환불 결과</title>
</head>
<body>
<h2>카카오페이 환불 결과</h2>

<p><strong>결제 수단:</strong> ${cancel.payment_method_type}</p>
<p><strong>환불 금액:</strong> ${cancel.canceled_amount.total}원</p>
<p><strong>취소 일시:</strong> ${cancel.canceled_at}</p>

<a href="/admin/refunds">← 환불 목록으로 돌아가기</a>
</body>
</html>