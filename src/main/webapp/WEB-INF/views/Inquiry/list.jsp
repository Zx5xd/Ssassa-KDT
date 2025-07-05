<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>문의 목록 - 싸싸</title>
    
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
        .inquiry-container {
            max-width: 1000px;
            margin: 140px auto 80px;
            padding: 0 20px;
        }
        
        .inquiry-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
        
        .inquiry-title {
            font-size: 28px;
            color: var(--text-1);
            margin: 0;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .write-btn {
            background-color: var(--brand);
            color: white;
            padding: 12px 24px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 500;
            display: flex;
            align-items: center;
            gap: 8px;
            transition: all 0.3s ease;
        }
        
        .write-btn:hover {
            background-color: #d4a574;
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        
        .inquiry-table {
            background-color: rgba(255, 255, 255, 0.04);
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            overflow: hidden;
        }
        
        .table-header {
            background-color: rgba(255, 255, 255, 0.08);
            display: grid;
            grid-template-columns: 80px 1fr 120px 100px 150px;
            padding: 15px 20px;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }
        
        .table-header .table-cell {
            color: var(--text-1);
            font-weight: 600;
            font-size: 14px;
            text-align: left;
        }
        
        .table-row {
            display: grid;
            grid-template-columns: 80px 1fr 120px 100px 150px;
            padding: 15px 20px;
            border-bottom: 1px solid rgba(255, 255, 255, 0.05);
            transition: background-color 0.3s ease;
        }
        
        .table-row:hover {
            background-color: rgba(255, 255, 255, 0.08);
        }
        
        .table-row:last-child {
            border-bottom: none;
        }
        
        .table-cell {
            color: var(--text-1);
            font-size: 14px;
            display: flex;
            align-items: center;
        }
        
        .title-cell {
            font-weight: 500;
        }
        
        .title-link {
            color: var(--text-1);
            text-decoration: none;
            transition: color 0.3s ease;
        }
        
        .title-link:hover {
            color: var(--brand);
        }
        
        .status-badge {
            display: inline-block;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
        }
        
        .status-pending {
            background-color: #ffc107;
            color: #212529;
        }
        
        .status-answered {
            background-color: #28a745;
            color: white;
        }
        
        .status-closed {
            background-color: #6c757d;
            color: white;
        }
        
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: var(--text-2);
        }
        
        .empty-state .material-symbols-outlined {
            font-size: 64px;
            margin-bottom: 20px;
            opacity: 0.5;
        }
        
        .back-button {
            display: inline-flex;
            align-items: center;
            gap: 8px;
            margin-bottom: 20px;
            color: var(--text-2);
            text-decoration: none;
            font-size: 14px;
            transition: color 0.3s ease;
        }
        
        .back-button:hover {
            color: var(--brand);
        }
        
        @media (max-width: 768px) {
            .inquiry-header {
                flex-direction: column;
                gap: 15px;
                align-items: flex-start;
            }
            
            .table-header,
            .table-row {
                grid-template-columns: 1fr;
                gap: 8px;
            }
            
            .table-header {
                display: none;
            }
            
            .table-row {
                border: 1px solid rgba(255, 255, 255, 0.1);
                border-radius: 8px;
                margin-bottom: 10px;
                padding: 15px;
            }
            
            .table-cell {
                justify-content: space-between;
            }
            
            .table-cell::before {
                content: attr(data-label);
                font-weight: 600;
                color: var(--text-2);
                margin-right: 10px;
            }
        }
    </style>
</head>

<body class="noto-sans-kr-regular">
    <div class="inquiry-container">
        <a href="/mypage" class="back-button">
            <span class="material-symbols-outlined">arrow_back</span>
            마이페이지로 돌아가기
        </a>

        <div class="inquiry-header">
            <h1 class="inquiry-title">
                <span class="material-symbols-outlined">support_agent</span>
                문의 목록
            </h1>
            <a href="/inquiry/write" class="write-btn">
                <span class="material-symbols-outlined">add</span>
                문의 작성
            </a>
        </div>

        <div class="inquiry-table">
            <c:choose>
                <c:when test="${not empty inquiries}">
                    <div class="table-header">
                        <div class="table-cell">번호</div>
                        <div class="table-cell">제목</div>
                        <div class="table-cell">작성자</div>
                        <div class="table-cell">상태</div>
                        <div class="table-cell">작성일</div>
                    </div>
                    
    <c:forEach var="inq" items="${inquiries}">
                        <div class="table-row">
                            <div class="table-cell" data-label="번호">${inq.id}</div>
                            <div class="table-cell title-cell" data-label="제목">
                                <a href="/inquiry/detail/${inq.id}" class="title-link">${inq.title}</a>
                            </div>
                            <div class="table-cell" data-label="작성자">${inq.username}</div>
                            <div class="table-cell" data-label="상태">
                                <span class="status-badge 
                                    <c:choose>
                                        <c:when test="${inq.status == 'PENDING'}">status-pending</c:when>
                                        <c:when test="${inq.status == 'ANSWERED'}">status-answered</c:when>
                                        <c:when test="${inq.status == 'CLOSED'}">status-closed</c:when>
                                        <c:otherwise>status-pending</c:otherwise>
                                    </c:choose>">
                                    <c:choose>
                                        <c:when test="${inq.status == 'PENDING'}">답변 대기</c:when>
                                        <c:when test="${inq.status == 'ANSWERED'}">답변 완료</c:when>
                                        <c:when test="${inq.status == 'CLOSED'}">문의 종료</c:when>
                                        <c:otherwise>${inq.status}</c:otherwise>
                                    </c:choose>
                                </span>
                            </div>
                            <div class="table-cell" data-label="작성일">
                                <fmt:formatDate value="${formatUtil.LocalDateTimeToDate(inq.createdAt)}"
                                pattern="yyyy년 MM월 dd일 HH:mm" />
                            </div>
                        </div>
    </c:forEach>
                </c:when>
                <c:otherwise>
                    <div class="empty-state">
                        <span class="material-symbols-outlined">support_agent</span>
                        <h3>문의 내역이 없습니다</h3>
                        <p>아직 작성한 문의가 없습니다.<br>궁금한 점이 있으시면 문의해주세요!</p>
                        <a href="/inquiry/write" class="write-btn" style="margin-top: 20px;">
                            <span class="material-symbols-outlined">add</span>
                            첫 문의 작성하기
                        </a>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</body>
</html>