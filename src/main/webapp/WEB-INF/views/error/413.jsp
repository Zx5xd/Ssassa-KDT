<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>413 Payload Too Large - 싸싸</title>
    
    <!-- 스타일 시트 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/common.css">
    
    <style>
        .error-container {
            max-width: 600px;
            margin: 100px auto;
            padding: 40px;
            text-align: center;
            background: white;
            border-radius: 10px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        
        .error-code {
            font-size: 72px;
            font-weight: bold;
            color: #e74c3c;
            margin-bottom: 20px;
        }
        
        .error-title {
            font-size: 24px;
            color: #2c3e50;
            margin-bottom: 15px;
        }
        
        .error-message {
            font-size: 16px;
            color: #7f8c8d;
            margin-bottom: 30px;
            line-height: 1.6;
        }
        
        .error-actions {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 12px 24px;
            border: none;
            border-radius: 5px;
            text-decoration: none;
            font-weight: 500;
            cursor: pointer;
            transition: all 0.3s ease;
        }
        
        .btn-primary {
            background-color: #3498db;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #2980b9;
        }
        
        .btn-secondary {
            background-color: #95a5a6;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #7f8c8d;
        }
        
        .file-size-info {
            background-color: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 5px;
            padding: 15px;
            margin: 20px 0;
            text-align: left;
        }
        
        .file-size-info h4 {
            margin: 0 0 10px 0;
            color: #495057;
        }
        
        .file-size-info ul {
            margin: 0;
            padding-left: 20px;
            color: #6c757d;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-code">413</div>
        <h1 class="error-title">Payload Too Large</h1>
        <p class="error-message">
            <c:if test="${not empty error}">
                <strong>${error}</strong><br>
            </c:if>
            <c:if test="${not empty message}">
                ${message}
            </c:if>
            <c:if test="${empty error and empty message}">
                업로드하려는 파일의 크기가 서버에서 허용하는 최대 크기를 초과했습니다.
            </c:if>
        </p>
        
        <div class="file-size-info">
            <h4>📁 파일 업로드 제한</h4>
            <ul>
                <li><strong>최대 파일 크기:</strong> 10MB</li>
                <li><strong>지원 파일 형식:</strong> 이미지 파일 (JPG, PNG, GIF)</li>
                <li><strong>권장 크기:</strong> 5MB 이하</li>
            </ul>
        </div>
        
        <div class="error-actions">
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">홈으로 돌아가기</a>
            <a href="javascript:history.back()" class="btn btn-secondary">이전 페이지로</a>
        </div>
    </div>
</body>
</html> 