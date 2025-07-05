<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>관리자 - 싸싸</title>
    
    <!-- 폰트 및 아이콘 -->
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">

    <!-- 프로젝트 CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/common.css">
    
    <style>
        body {
            background: var(--surface-1);
            color: var(--text-1);
            font-family: 'Noto Sans KR', sans-serif;
        }
        
        .admin-container {
            max-width: 1200px;
            margin: 140px auto 80px;
            padding: 0 20px;
        }
        
        .section-title {
            font-size: 28px;
            color: var(--text-1);
            margin-bottom: 30px;
            text-align: center;
            font-weight: 600;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
        }
        
        .card {
            background-color: rgba(255, 255, 255, 0.04);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            margin-bottom: 40px;
            overflow: hidden;
        }
        
        .card-body {
            padding: 30px;
        }
        
        .table {
            width: 100%;
            border-collapse: collapse;
            color: var(--text-1);
            background-color: transparent;
        }
        
        .table thead {
            background-color: rgba(255, 255, 255, 0.08);
        }
        
        .table th {
            padding: 15px 12px;
            text-align: center;
            font-weight: 600;
            font-size: 14px;
            color: var(--text-1);
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }
        
        .table td {
            padding: 15px 12px;
            text-align: center;
            vertical-align: middle;
            border-bottom: 1px solid rgba(255, 255, 255, 0.05);
        }
        
        .table tbody tr:hover {
            background-color: rgba(255, 255, 255, 0.08);
        }
        
        .table tbody tr:last-child td {
            border-bottom: none;
        }
        
        .text-start {
            text-align: left;
        }
        
        .text-center {
            text-align: center;
        }
        
        .align-middle {
            vertical-align: middle;
        }
        
        .btn {
            display: inline-flex;
            align-items: center;
            gap: 6px;
            padding: 8px 16px;
            font-size: 14px;
            font-weight: 500;
            border-radius: 6px;
            text-decoration: none;
            border: none;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        
        .btn:hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        
        .btn-sm {
            padding: 6px 12px;
            font-size: 12px;
        }
        
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background-color: #c82333;
            color: white;
        }
        
        .btn-success {
            background-color: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background-color: #218838;
            color: white;
        }
        
        .btn-outline-primary {
            background-color: transparent;
            color: var(--brand);
            border: 1px solid var(--brand);
        }
        
        .btn-outline-primary:hover {
            background-color: var(--brand);
            color: white;
        }
        
        .btn-outline-danger {
            background-color: transparent;
            color: #dc3545;
            border: 1px solid #dc3545;
        }
        
        .btn-outline-danger:hover {
            background-color: #dc3545;
            color: white;
        }
        
        .badge {
            display: inline-block;
            padding: 4px 8px;
            font-size: 12px;
            font-weight: 600;
            border-radius: 4px;
        }
        
        .bg-warning {
            background-color: #ffc107;
            color: #212529;
        }
        
        .bg-success {
            background-color: #28a745;
            color: white;
        }
        
        .bg-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .text-dark {
            color: #212529;
        }
        
        .form-control {
            width: 100%;
            padding: 12px;
            background-color: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
            border-radius: 6px;
            color: var(--text-1);
            font-size: 14px;
            font-family: inherit;
            transition: all 0.3s ease;
            box-sizing: border-box;
        }
        
        .form-control:focus {
            outline: none;
            background-color: rgba(255, 255, 255, 0.15);
            border-color: var(--brand);
            box-shadow: 0 0 0 2px rgba(255, 193, 7, 0.25);
        }
        
        .form-control::placeholder {
            color: rgba(255, 255, 255, 0.6);
        }
        
        textarea.form-control {
            resize: vertical;
            min-height: 100px;
        }
        
        .mb-2 {
            margin-bottom: 10px;
        }
        
        .mb-4 {
            margin-bottom: 20px;
        }
        
        .mb-5 {
            margin-bottom: 30px;
        }
        
        .mt-5 {
            margin-top: 30px;
        }
        
        .p-3 {
            padding: 15px;
        }
        
        .px-3 {
            padding-left: 15px;
            padding-right: 15px;
        }
        
        .rounded {
            border-radius: 6px;
        }
        
        .shadow-sm {
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }
        
        .fw-bold {
            font-weight: 600;
        }
        
        .text-muted {
            color: var(--text-2);
        }
        
        .preview-img {
            max-width: max-content;
            max-height: 200px;
            border: 1px solid rgba(255, 255, 255, 0.2);
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
        
        .detail-content {
            padding: 20px;
            background-color: rgba(255, 255, 255, 0.08);
            border-radius: 6px;
            margin: 10px;
            text-align: center;
        }
        
        .detail-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 30px;
            margin-bottom: 20px;
        }
        
        .detail-left {
            text-align: left;
        }
        
        .detail-right {
            text-align: left;
        }
        
        .detail-right img {
            display: block;
            margin: 0 auto;
        }
        
        .detail-item {
            margin-bottom: 15px;
            color: var(--text-1);
        }
        
        .detail-item strong {
            color: var(--text-1);
            font-weight: 600;
            display: block;
            margin-bottom: 5px;
        }
        
        .content-text {
            background-color: rgba(255, 255, 255, 0.05);
            border-radius: 6px;
            padding: 15px;
            margin-top: 8px;
            line-height: 1.6;
            white-space: pre-wrap;
            text-align: left;
        }
        
        .detail-reply {
            text-align: left;
            margin-top: 20px;
            padding-top: 20px;
            border-top: 1px solid rgba(255, 255, 255, 0.1);
        }
        
        .reply-text {
            background-color: rgba(255, 193, 7, 0.1);
            border: 1px solid rgba(255, 193, 7, 0.3);
            border-radius: 6px;
            padding: 15px;
            margin-top: 8px;
            line-height: 1.6;
            white-space: pre-wrap;
        }
        
        .detail-content a {
            color: var(--brand);
            text-decoration: none;
        }
        
        .detail-content a:hover {
            text-decoration: underline;
        }
        
        .empty-message {
            text-align: center;
            padding: 40px 20px;
            color: var(--text-2);
        }
        
        .empty-message h3 {
            margin-bottom: 10px;
            color: var(--text-1);
        }
        
        .empty-message p {
            margin-bottom: 20px;
        }
        
        @media (max-width: 768px) {
            .admin-container {
                padding: 0 10px;
            }
            
            .card-body {
                padding: 20px;
            }
            
            .table {
                font-size: 12px;
            }
            
            .table th,
            .table td {
                padding: 10px 6px;
            }
            
            .btn-sm {
                padding: 4px 8px;
                font-size: 11px;
            }
            
            .detail-grid {
                grid-template-columns: 1fr;
                gap: 20px;
            }
            
            .detail-right {
                text-align: left;
            }
            
            .detail-content {
                padding: 15px;
            }
        }
    </style>
</head>
<body class="noto-sans-kr-regular">
<div class="admin-container">

    <!-- ✅ 환불 요청 목록 -->
    <h2 class="section-title">📦 환불 요청 관리</h2>
    <div class="card">
        <div class="card-body">
            <table class="table">
                <thead>
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
                <div class="empty-message">
                    <h3>📜 현재 환불 요청이 없습니다.</h3>
                </div>
            </c:if>
        </div>
    </div>

    <!-- ✅ 문의사항 관리 -->
    <h2 class="section-title">📨 문의사항 관리</h2>
    <div class="card">
        <div class="card-body">
            <table class="table">
                <thead>
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
                            <a href="javascript:void(0);" class="btn btn-outline-primary btn-sm" onclick="toggleDetail('${inq.id}')">📄 상세</a>
                            <form method="post" action="/admin/inquiry/delete" style="display:inline-block;">
                                <input type="hidden" name="id" value="${inq.id}" />
                                <button type="submit" class="btn btn-outline-danger btn-sm">🗑 삭제</button>
                            </form>
                        </td>
                    </tr>
                    <tr id="detail-${inq.id}" class="slide-detail">
                        <td colspan="6">
                            <div class="detail-content">
                                <div class="detail-grid">
                                    <!-- 첫 번째 줄: 왼쪽 파트 (제목, 작성자, 내용) -->
                                    <div class="detail-left">
                                        <div class="detail-item">
                                            <strong>제목:</strong> ${inq.title}
                                        </div>
                                        <div class="detail-item">
                                            <strong>작성자:</strong> ${inq.username}
                                        </div>
                                        <div class="detail-item">
                                            <strong>내용:</strong>
                                            <div class="content-text">${inq.content}</div>
                                        </div>
                                    </div>
                                    
                                    <!-- 첫 번째 줄: 오른쪽 파트 (첨부파일) -->
                                    <div class="detail-right">
                                <c:if test="${not empty inq.fileName}">
                                            <div class="detail-item">
                                                <strong>첨부파일:</strong>
                                        <c:set var="lowerName" value="${fn:toLowerCase(inq.fileName)}" />
                                        <c:choose>
                                            <c:when test="${fn:endsWith(lowerName, '.jpg') || fn:endsWith(lowerName, '.jpeg') || fn:endsWith(lowerName, '.png') || fn:endsWith(lowerName, '.gif') || fn:endsWith(lowerName, '.webp')}">
                                                        <img src="${inq.filePath}" class="preview-img" alt="첨부 이미지 미리보기"/>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="${inq.filePath}" download>${inq.fileName}</a>
                                            </c:otherwise>
                                        </c:choose>
                                            </div>
                                </c:if>
                                    </div>
                                </div>

                                <!-- 두 번째 줄: 기존 답변 -->
                                <c:if test="${inq.hasReply}">
                                    <div class="detail-reply">
                                        <div class="detail-item">
                                            <strong>📢 기존 답변:</strong>
                                            <div class="reply-text">${inq.adminComment}</div>
                                        </div>
                                    </div>
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
                <div class="empty-message">
                    <h3>❓ 등록된 문의사항이 없습니다.</h3>
                </div>
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