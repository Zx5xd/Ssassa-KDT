<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>관리자</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/css/style.css">
    <style>
        .preview-img {
            max-width: 200px;
            max-height: 200px;
            border: 1px solid #ddd;
            border-radius: 6px;
            margin-top: 10px;
        }
        .slide-detail {
            display: none;
            animation: slideDown 0.4s ease-in-out;
        }
        @keyframes slideDown {
            from {opacity: 0; transform: translateY(-10px);}
            to {opacity: 1; transform: translateY(0);}
        }
    </style>
</head>
<body class="bg-light">
<div class="container mt-5">

    <!-- ✅ 확불 요청 목록 -->
    <h2 class="mb-4 text-center fw-bold">📦 확불 요청 관리</h2>
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
                                <button type="submit" class="btn btn-danger btn-sm">확불 승인</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty refunds}">
                <div class="text-center text-muted p-3">📜 현재 확불 요청이 없습니다.</div>
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
                            <a href="javascript:void(0);" class="btn btn-outline-primary btn-sm" onclick="toggleDetail(${inq.id})">📄 상세</a>
                            <form method="post" action="/admin/inquiry/delete" style="display:inline-block;">
                                <input type="hidden" name="id" value="${inq.id}" />
                                <button type="submit" class="btn btn-outline-danger btn-sm">🗑 삭제</button>
                            </form>
                        </td>
                    </tr>
                    <tr id="detail-${inq.id}" class="slide-detail">
                        <td colspan="6">
                            <div class="p-3 bg-light rounded">
                                <p><strong>제목:</strong> ${inq.title}</p>
                                <p><strong>작성자:</strong> ${inq.username}</p>
                                <p><strong>내용:</strong><br>${inq.content}</p>

                                <c:if test="${not empty inq.fileName}">
                                    <p><strong>첨부파일:</strong>
                                        <c:set var="lowerName" value="${fn:toLowerCase(inq.fileName)}" />
                                        <c:choose>
                                            <c:when test="${fn:endsWith(lowerName, '.jpg') || fn:endsWith(lowerName, '.jpeg') || fn:endsWith(lowerName, '.png') || fn:endsWith(lowerName, '.gif') || fn:endsWith(lowerName, '.webp')}">
                                                <br><img src="${inq.filePath}" class="preview-img" alt="챔드 이미지 미리보기"/>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${inq.filePath}" download>${inq.fileName}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                </c:if>

                                <c:if test="${inq.hasReply}">
                                    <p><strong>📢 기존 답변:</strong><br>${inq.adminComment}</p>
                                </c:if>

                                <form method="post" action="/admin/inquiry/reply">
                                    <input type="hidden" name="id" value="${inq.id}" />
                                    <div class="mb-2">
                                        <label>답변 내용</label>
                                        <textarea name="adminComment" class="form-control" required></textarea>
                                    </div>
                                    <button type="submit" class="btn btn-sm btn-success">답변 등록</button>
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${empty inquiries}">
                <div class="text-center text-muted p-3">❓ 등록된 문의사항이 없습니다.</div>
            </c:if>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
    function toggleDetail(id) {
        $("tr[id^='detail-']").not("#detail-" + id).slideUp();
        $("#detail-" + id).slideToggle();
    }
</script>
</body>
</html>
