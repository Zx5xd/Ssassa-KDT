<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>문의 상세 - 싸싸</title>
    
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
        .detail-container {
            max-width: 800px;
            margin: 140px auto 80px;
            padding: 0 20px;
        }
        
        .detail-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
        
        .detail-title {
            font-size: 28px;
            color: var(--text-1);
            margin: 0;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .detail-card {
            background-color: rgba(255, 255, 255, 0.04);
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            padding: 30px;
            margin-bottom: 20px;
        }
        
        .inquiry-info {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 25px;
            padding-bottom: 20px;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }
        
        .info-item {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        
        .info-label {
            color: var(--text-2);
            font-size: 12px;
            font-weight: 500;
        }
        
        .info-value {
            color: var(--text-1);
            font-weight: 600;
        }
        
        .inquiry-content {
            margin-bottom: 25px;
        }
        
        .content-text {
            color: var(--text-1);
            line-height: 1.6;
            white-space: pre-wrap;
            background-color: rgba(255, 255, 255, 0.08);
            border-radius: 6px;
            padding: 20px;
        }
        
        .attachment-section {
            margin-bottom: 25px;
        }
        
        .attachment-title {
            color: var(--text-1);
            font-weight: 600;
            margin-bottom: 15px;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        
        .file-link {
            color: var(--brand);
            text-decoration: none;
            display: flex;
            align-items: center;
            gap: 8px;
            padding: 10px 15px;
            background-color: rgba(255, 255, 255, 0.08);
            border-radius: 6px;
            transition: all 0.3s ease;
        }
        
        .file-link:hover {
            background-color: rgba(255, 255, 255, 0.12);
            transform: translateY(-1px);
        }
        
        .preview-image {
            max-width: 100%;
            border-radius: 8px;
            margin-top: 15px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        
        .admin-reply {
            background-color: rgba(255, 193, 7, 0.1);
            border: 1px solid rgba(255, 193, 7, 0.3);
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 25px;
        }
        
        .admin-reply h3 {
            color: var(--text-1);
            margin: 0 0 15px 0;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        
        .admin-reply-content {
            color: var(--text-1);
            line-height: 1.6;
            white-space: pre-wrap;
        }
        
        .reply-form {
            background-color: rgba(255, 255, 255, 0.08);
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 25px;
        }
        
        .reply-form h3 {
            color: var(--text-1);
            margin: 0 0 15px 0;
            display: flex;
            align-items: center;
            gap: 8px;
        }
        
        .reply-textarea {
            width: 100%;
            min-height: 120px;
            padding: 12px;
            background-color: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
            border-radius: 6px;
            color: #ffffff;
            font-size: 14px;
            font-family: inherit;
            resize: vertical;
        }
        
        .reply-textarea:focus {
            outline: none;
            background-color: rgba(255, 255, 255, 0.15);
            border-color: var(--brand);
            box-shadow: 0 0 0 2px rgba(255, 193, 7, 0.25);
        }
        
        .action-buttons {
            display: flex;
            gap: 15px;
            justify-content: flex-end;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 10px 20px;
            font-size: 14px;
            border-radius: 6px;
            text-decoration: none;
            border: none;
            cursor: pointer;
            transition: all 0.3s ease;
            font-weight: 500;
            display: inline-flex;
            align-items: center;
            gap: 6px;
        }
        
        .btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }
        
        .btn-primary {
            background-color: var(--brand);
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #d4a574;
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background-color: #c82333;
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
            .detail-header {
                flex-direction: column;
                gap: 15px;
                align-items: flex-start;
            }
            
            .inquiry-info {
                grid-template-columns: 1fr;
            }
            
            .action-buttons {
                flex-direction: column;
            }
            
            .btn {
                justify-content: center;
            }
        }
    </style>
</head>

<body class="noto-sans-kr-regular">
    <div class="detail-container">
        <a href="/inquiry/list" class="back-button">
            <span class="material-symbols-outlined">arrow_back</span>
            문의 목록으로 돌아가기
        </a>

        <div class="detail-header">
            <h1 class="detail-title">
                <span class="material-symbols-outlined">support_agent</span>
                문의 상세
            </h1>
        </div>

        <div class="detail-card">
            <h2 style="color: var(--text-1); margin-bottom: 20px;">${inquiry.title}</h2>
            
            <div class="inquiry-info">
                <div class="info-item">
                    <span class="info-label">작성자</span>
                    <span class="info-value">${inquiry.username}</span>
                </div>
                <div class="info-item">
                    <span class="info-label">작성일</span>
                    <span class="info-value">
                        <fmt:formatDate value="${formatUtil.LocalDateTimeToDate(inq.createdAt)}"
                        pattern="yyyy년 MM월 dd일 HH:mm" />
                    </span>
                </div>
                <div class="info-item">
                    <span class="info-label">문의 번호</span>
                    <span class="info-value">#${inquiry.id}</span>
                </div>
            </div>

            <div class="inquiry-content">
                <h3 style="color: var(--text-1); margin-bottom: 15px;">📝 문의 내용</h3>
                <div class="content-text">${inquiry.content}</div>
            </div>

            <c:if test="${not empty inquiry.fileName}">
                <div class="attachment-section">
                    <h3 class="attachment-title">
                        <span class="material-symbols-outlined">attach_file</span>
                        첨부파일
                    </h3>
                    <a href="${inquiry.filePath}" download class="file-link">
                        <span class="material-symbols-outlined">download</span>
                        ${inquiry.fileName}
                    </a>

                    <!-- 이미지 파일 미리보기 -->
                    <c:if test="${fn:contains(fn:toLowerCase(inquiry.fileName), '.png')
                                or fn:contains(fn:toLowerCase(inquiry.fileName), '.jpg')
                                or fn:contains(fn:toLowerCase(inquiry.fileName), '.jpeg')
                                or fn:contains(fn:toLowerCase(inquiry.fileName), '.gif')
                                or fn:contains(fn:toLowerCase(inquiry.fileName), '.webp')}">
                        <img src="${inquiry.filePath}" alt="미리보기 이미지" class="preview-image"/>
                    </c:if>
                </div>
            </c:if>

            <c:if test="${inquiry.adminComment != null}">
                <div class="admin-reply">
                    <h3>
                        <span class="material-symbols-outlined">admin_panel_settings</span>
                        관리자 답변
                    </h3>
                    <div class="admin-reply-content">${inquiry.adminComment}</div>
                </div>
            </c:if>

            <c:if test="${sessionScope.loginUser != null && sessionScope.loginUser.role eq 'ADMIN'}">
                <div class="reply-form">
                    <h3>
                        <span class="material-symbols-outlined">reply</span>
                        답변 작성
                    </h3>
                    <form action="/inquiry/reply/${inquiry.id}" method="post" id="replyForm">
                        <textarea name="adminComment" class="reply-textarea" placeholder="관리자 답변을 작성해주세요..." required></textarea>
                        <div style="margin-top: 15px;">
                            <button type="submit" class="btn btn-primary">
                                <span class="material-symbols-outlined">send</span>
                                답변 등록
                            </button>
                        </div>
                    </form>
                </div>
            </c:if>

            <c:if test="${sessionScope.loginUser.name eq inquiry.username}">
                <div class="action-buttons">
                    <a href="/inquiry/edit/${inquiry.id}" class="btn btn-secondary">
                        <span class="material-symbols-outlined">edit</span>
                        수정
                    </a>
                    <a href="/inquiry/delete/${inquiry.id}" class="btn btn-danger" onclick="return confirm('정말 삭제하시겠습니까?\n\n삭제된 문의는 복구할 수 없습니다.');">
                        <span class="material-symbols-outlined">delete</span>
                        삭제
                    </a>
                </div>
            </c:if>
        </div>
    </div>

    <script>
        // 답변 폼 제출 전 확인
        const replyForm = document.getElementById('replyForm');
        if (replyForm) {
            replyForm.addEventListener('submit', function(e) {
                const comment = document.querySelector('textarea[name="adminComment"]').value.trim();
                
                if (!comment) {
                    alert('답변 내용을 입력해주세요.');
                    e.preventDefault();
                    return;
                }
            });
        }
    </script>
</body>
</html>