<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/style.css">
</head>
<body class="bg-light">
<div class="container mt-5">

    <!-- ✅ 환불 요청 목록 -->
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

    <!-- ✅ 문의사항 관리 -->
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
                    <th>답변 상태</th>
                    <th>관리</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="inq" items="${inquiries}">
                    <tr>
                        <td>${inq.id}</td>
                        <td class="text-start px-3">${inq.title}</td>
                        <td>${inq.username}</td>
                        <td>${inq.createdAt.toString().substring(0, 16)}</td>
                        <td>
                            <c:choose>
                                <c:when test="${inq.hasReply}">
                                    <span class="badge bg-success">답변 완료</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge bg-secondary">미답변</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <a href="/admin/inquiry/detail/${inq.id}" class="btn btn-outline-primary btn-sm">📄 상세</a>
                            <form method="post" action="/admin/inquiry/delete" style="display:inline-block;">
                                <input type="hidden" name="id" value="${inq.id}" />
                                <button type="submit" class="btn btn-outline-danger btn-sm">🗑 삭제</button>
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

    <!-- ✅ 문의 상세 및 답변 폼 (선택된 문의가 있을 경우) -->
    <c:if test="${selectedInquiry != null}">
        <div class="card shadow-sm">
            <div class="card-body">
                <h5 class="mb-3 fw-bold">📄 문의 상세 보기</h5>
                <p><strong>제목:</strong> ${selectedInquiry.title}</p>
                <p><strong>작성자:</strong> ${selectedInquiry.username}</p>
                <p><strong>내용:</strong><br>${selectedInquiry.content}</p>

                <c:if test="${selectedInquiry.fileName != null}">
                    <p><strong>첨부파일:</strong>
                        <a href="/uploads/${selectedInquiry.fileName}" download>${selectedInquiry.fileName}</a>
                    </p>
                </c:if>

                <c:if test="${selectedInquiry.hasReply}">
                    <p><strong>📢 기존 답변:</strong><br>${selectedInquiry.adminComment}</p>
                </c:if>

                <!-- ✅ 답변 작성 폼 -->
                <form method="post" action="/admin/inquiry/reply">
                    <input type="hidden" name="id" value="${selectedInquiry.id}" />
                    <div class="mb-3">
                        <label for="adminComment" class="form-label">답변 내용</label>
                        <textarea name="adminComment" id="adminComment" rows="4" class="form-control" required></textarea>
                    </div>
                    <button type="submit" class="btn btn-success">답변 등록</button>
                </form>
            </div>
        </div>
    </c:if>

</div>
</body>
</html>