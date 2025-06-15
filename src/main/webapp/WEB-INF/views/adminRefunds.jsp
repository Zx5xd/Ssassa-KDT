<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자 대시보드</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body class="bg-light">
<div class="container mt-5">


    <!-- 환불 요청 목록 -->
    <h2 class="mb-4 text-center fw-bold">📦 환불 요청 관리</h2>
    <div class="card shadow-sm mb-5">
        <div class="card-body">
            <table class="table table-bordered table-hover text-center align-middle">
                <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>상품명</th>
                    <th>금액</th>
                    <th>상태</th>
                    <th>처리</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="payment" items="${refunds}">
                    <tr>
                        <td>${payment.id}</td>
                        <td>${payment.itemName}</td>
                        <td>${payment.amount}원</td>
                        <td><span class="badge bg-warning text-dark">${payment.status}</span></td>
                        <td>
                            <form method="post" action="/admin/refund/complete">
                                <input type="hidden" name="paymentId" value="${payment.id}" />
                                <button type="submit" class="btn btn-danger btn-sm">환불 승인</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty refunds}">
                <div class="text-center text-muted p-3">📭 현재 환불 요청이 없습니다.</div>
            </c:if>
        </div>
    </div>

    <!-- 문의사항 관리 -->
    <h2 class="mb-4 text-center fw-bold">📨 문의사항 관리</h2>
    <div class="card shadow-sm mb-5">
        <div class="card-body">
            <table class="table table-bordered table-hover text-center align-middle">
                <thead class="table-success">
                <tr>
                    <th>ID</th>
                    <th>제목</th>
                    <th>작성자</th>
                    <th>작성일</th>
                    <th>관리</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="inq" items="${inquiries}">
                    <tr>
                        <td>${inq.id}</td>
                        <td>${inq.title}</td>
                        <td>${inq.username}</td>
                        <td>${inq.createdAt}</td>
                        <td>
                            <a href="/inquiry/detail/${inq.id}" class="btn btn-outline-info btn-sm">보기</a>
                            <form method="post" action="/admin/inquiry/delete" style="display:inline-block;">
                                <input type="hidden" name="id" value="${inq.id}" />
                                <button type="submit" class="btn btn-outline-danger btn-sm">삭제</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty inquiries}">
                <div class="text-center text-muted p-3">❔ 등록된 문의사항이 없습니다.</div>
            </c:if>
        </div>
    </div>

</div>
</body>
</html>