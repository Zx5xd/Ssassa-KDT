<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>카테고리 순서 관리 - 싸싸</title>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/admin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/common.css">
    <style>
        .selection-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 40px 20px;
        }
        
        .category-info {
            background: white;
            border-radius: 10px;
            padding: 30px;
            margin-bottom: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        
        .category-name {
            font-size: 1.5rem;
            font-weight: bold;
            color: #2c3e50;
            margin-bottom: 10px;
        }
        
        .category-code {
            color: #6c757d;
            font-size: 1rem;
        }
        
        .order-options {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-top: 30px;
        }
        
        .order-option {
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
            transition: transform 0.3s ease;
            cursor: pointer;
            text-decoration: none;
            color: inherit;
        }
        
        .order-option:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }
        
        .option-icon {
            font-size: 3rem;
            margin-bottom: 15px;
        }
        
        .option-title {
            font-size: 1.2rem;
            font-weight: bold;
            color: #2c3e50;
            margin-bottom: 10px;
        }
        
        .option-description {
            color: #6c757d;
            font-size: 0.9rem;
            line-height: 1.5;
        }
        
        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #007bff;
            text-decoration: none;
        }
        
        .back-link:hover {
            text-decoration: underline;
        }
        
        @media (max-width: 768px) {
            .order-options {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body class="noto-sans-kr-regular">
    <main>
        <div class="admin-container">
            <div class="selection-container">
                <a href="/admin/categories" class="back-link">← 카테고리 관리로 돌아가기</a>
                
                <div class="category-info">
                    <div class="category-name">${category.name}</div>
                    <div class="category-code">코드: ${category.code}</div>
                </div>
                
                <h2 class="admin-title">
                    <span class="material-symbols-outlined">sort</span>
                    순서 관리 방식 선택
                </h2>
                
                <div class="order-options">
                    <a href="/cat/displayOrder/${category.id}" class="order-option">
                        <div class="option-icon">📋</div>
                        <div class="option-title">일반 순서 관리</div>
                        <div class="option-description">
                            카테고리 필드의 기본 순서를 관리합니다.<br>
                            단순한 순서 변경에 적합합니다.
                        </div>
                    </a>
                    
                    <a href="/cat/displayOrder-All/${category.id}" class="order-option">
                        <div class="option-icon">📊</div>
                        <div class="option-title">전체 순서 관리</div>
                        <div class="option-description">
                            모든 카테고리 필드를 한 번에 관리합니다.<br>
                            복잡한 순서 변경에 적합합니다.
                        </div>
                    </a>
                </div>
            </div>
        </div>
    </main>
</body>
</html> 