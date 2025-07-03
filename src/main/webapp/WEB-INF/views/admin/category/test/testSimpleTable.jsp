<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>동적 필터 | ValueList 테이블</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            padding: 20px;
            background-color: #f5f5f5;
        }
        
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
            border-bottom: 3px solid #007bff;
            padding-bottom: 10px;
        }
        
        .section {
            margin-bottom: 40px;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #fafafa;
        }
        
        .section h2 {
            color: #007bff;
            margin-top: 0;
            margin-bottom: 15px;
            font-size: 1.5em;
        }
        
        .filter-group {
            margin-bottom: 20px;
        }
        
        .filter-label {
            font-weight: bold;
            color: #007bff;
            margin-right: 10px;
        }
        
        .filter-options {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-top: 5px;
        }
        
        .filter-checkbox {
            margin-right: 5px;
        }
        
        .data-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background: white;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
        }
        
        .data-table th,
        .data-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
            vertical-align: top;
        }
        
        .data-table th {
            background-color: #007bff;
            color: white;
            font-weight: bold;
            text-align: center;
        }
        
        .data-table tr:nth-child(even) {
            background-color: #f8f9fa;
        }
        
        .data-table tr:hover {
            background-color: #e3f2fd;
        }
        
        .field-name {
            font-weight: bold;
            color: #495057;
            min-width: 150px;
        }
        
        .value-list {
            display: flex;
            flex-wrap: wrap;
            gap: 5px;
        }
        
        .value-item {
            background: #007bff;
            color: white;
            padding: 4px 8px;
            border-radius: 3px;
            font-size: 0.85em;
            white-space: nowrap;
        }
        
        .json-display {
            background: #f8f9fa;
            padding: 8px;
            border-radius: 3px;
            font-family: 'Courier New', monospace;
            font-size: 0.85em;
            white-space: pre-wrap;
            overflow-x: auto;
            max-height: 200px;
            overflow-y: auto;
            border: 1px solid #e9ecef;
        }
        
        .empty-data {
            color: #6c757d;
            font-style: italic;
            text-align: center;
            padding: 20px;
        }
        
        .nav-links {
            text-align: center;
            margin-top: 30px;
            padding-top: 20px;
            border-top: 1px solid #ddd;
        }
        
        .nav-links a {
            display: inline-block;
            background: #007bff;
            color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            margin: 0 10px;
            transition: background-color 0.3s;
        }
        
        .nav-links a:hover {
            background: #0056b3;
        }
        
        .toggle-btn {
            background: #28a745;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            margin-bottom: 10px;
            font-size: 0.9em;
        }
        
        .toggle-btn:hover {
            background: #218838;
        }
        
        .hidden {
            display: none;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>📊 동적 필터 | ValueList 테이블</h1>

        <div class="section">
            <h2>📝 동적 필터 (모든 필터 복수 선택)</h2>
            <form id="filterForm" onsubmit="return false;">
                <c:forEach var="fieldEntry" items="${fieldsSimpleMap}">
                    <div class="filter-group">
                        <span class="filter-label">${fieldEntry.key}</span>
                        <div class="filter-options">
                            <c:forEach var="value" items="${fieldEntry.value}">
                                <label>
                                    <input type="checkbox" class="filter-checkbox" name="${fieldEntry.key}" value="${value}" onchange="searchProducts()">
                                    ${value}
                                </label>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </form>
        </div>

        <div class="section">
            <h2>🔍 상품 리스트</h2>
            <div id="product-list-area">
                <!-- AJAX로 상품 리스트가 여기에 렌더링됩니다. -->
            </div>
        </div>

        <div class="nav-links">
            <a href="/cat/displayOrder-All">📋 카테고리 필드 관리</a>
        </div>
    </div>
    
    <script>
        function searchProducts() {
            const form = document.getElementById('filterForm');
            const formData = new FormData(form);
            const params = new URLSearchParams();
            for (const [key, value] of formData.entries()) {
                params.append(key, value);
            }
            fetch('/cat/searchProductsAjax?' + params.toString())
                .then(res => res.text())
                .then(html => {
                    document.getElementById('product-list-area').innerHTML = html;
                });
        }
        // 페이지 진입 시 최초 상품 전체 조회
        window.onload = searchProducts;
    </script>
</body>
</html> 