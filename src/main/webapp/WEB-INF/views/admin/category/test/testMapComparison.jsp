<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Map vs JSON 비교 테스트</title>
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
        
        .data-display {
            background: white;
            padding: 15px;
            border-radius: 5px;
            border-left: 4px solid #007bff;
            margin-bottom: 15px;
        }
        
        .field-item {
            background: #f8f9fa;
            padding: 10px;
            margin: 10px 0;
            border-radius: 5px;
            border: 1px solid #e9ecef;
        }
        
        .field-name {
            font-weight: bold;
            color: #495057;
            margin-bottom: 5px;
        }
        
        .value-list {
            display: flex;
            flex-wrap: wrap;
            gap: 5px;
            margin-top: 5px;
        }
        
        .value-item {
            background: #007bff;
            color: white;
            padding: 3px 8px;
            border-radius: 3px;
            font-size: 0.9em;
        }
        
        .weight-item {
            background: #28a745;
            color: white;
            padding: 3px 8px;
            border-radius: 3px;
            font-size: 0.9em;
            margin-left: 5px;
        }
        
        .json-display {
            background: #f8f9fa;
            padding: 10px;
            border-radius: 3px;
            font-family: 'Courier New', monospace;
            font-size: 0.9em;
            white-space: pre-wrap;
            overflow-x: auto;
        }
        
        .comparison-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        .comparison-table th,
        .comparison-table td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: left;
        }
        
        .comparison-table th {
            background-color: #007bff;
            color: white;
            font-weight: bold;
        }
        
        .comparison-table tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        
        .advantage {
            color: #28a745;
            font-weight: bold;
        }
        
        .disadvantage {
            color: #dc3545;
            font-weight: bold;
        }
        
        .usage-example {
            background: #e7f3ff;
            padding: 15px;
            border-radius: 5px;
            margin-top: 15px;
        }
        
        .usage-example h4 {
            color: #0056b3;
            margin-top: 0;
        }
        
        .code-block {
            background: #2d3748;
            color: #e2e8f0;
            padding: 15px;
            border-radius: 5px;
            font-family: 'Courier New', monospace;
            overflow-x: auto;
            margin: 10px 0;
        }
        
        .error-message {
            background: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 5px;
            border: 1px solid #f5c6cb;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🔍 Map vs JSON 비교 테스트</h1>
        
        <c:if test="${not empty errorMsg}">
            <div class="error-message">
                <strong>오류:</strong> ${errorMsg}
            </div>
        </c:if>
        
        <!-- 1. 원본 DTO 리스트 -->
        <div class="section">
            <h2>📋 1. 원본 DTO 리스트 (JSON 형태)</h2>
            <c:if test="${not empty originalDTOList}">
                <c:forEach var="dto" items="${originalDTOList}">
                    <div class="field-item">
                        <div class="field-name">${dto.attributeKey}</div>
                        <div>Unit: ${dto.unit}</div>
                        <div>Display Order: ${dto.displayOrder}</div>
                        <div class="json-display">${dto.valueList}</div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty originalDTOList}">
                <p>원본 데이터가 없습니다.</p>
            </c:if>
        </div>
        
        <!-- 2. 필터링된 DTO 리스트 -->
        <div class="section">
            <h2>🔄 2. 필터링된 DTO 리스트 (JSON 형태)</h2>
            <c:if test="${not empty filteredDTOList}">
                <c:forEach var="dto" items="${filteredDTOList}">
                    <div class="field-item">
                        <div class="field-name">${dto.attributeKey}</div>
                        <div>Unit: ${dto.unit}</div>
                        <div>Display Order: ${dto.displayOrder}</div>
                        <div class="json-display">${dto.valueList}</div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty filteredDTOList}">
                <p>필터링된 데이터가 없습니다.</p>
            </c:if>
        </div>
        
        <!-- 3. Map 형태 (값과 가중치) -->
        <div class="section">
            <h2>🗺️ 3. Map 형태 (값과 가중치)</h2>
            <c:if test="${not empty fieldsMapWithWeight}">
                <c:forEach var="fieldEntry" items="${fieldsMapWithWeight}">
                    <div class="field-item">
                        <div class="field-name">${fieldEntry.key}</div>
                        <div class="value-list">
                            <c:forEach var="valueEntry" items="${fieldEntry.value}">
                                <span class="value-item">${valueEntry.key}</span>
                                <span class="weight-item">weight: ${valueEntry.value}</span>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty fieldsMapWithWeight}">
                <p>Map 데이터가 없습니다.</p>
            </c:if>
        </div>
        
        <!-- 4. 단순 Map 형태 (값만) -->
        <div class="section">
            <h2>📝 4. 단순 Map 형태 (값만)</h2>
            <c:if test="${not empty fieldsSimpleMap}">
                <c:forEach var="fieldEntry" items="${fieldsSimpleMap}">
                    <div class="field-item">
                        <div class="field-name">${fieldEntry.key}</div>
                        <div class="value-list">
                            <c:forEach var="value" items="${fieldEntry.value}">
                                <span class="value-item">${value}</span>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </c:if>
            <c:if test="${empty fieldsSimpleMap}">
                <p>단순 Map 데이터가 없습니다.</p>
            </c:if>
        </div>
        
        <!-- 5. 비교 테이블 -->
        <div class="section">
            <h2>⚖️ 5. Map vs JSON 비교</h2>
            <table class="comparison-table">
                <thead>
                    <tr>
                        <th>비교 항목</th>
                        <th>Map 형태</th>
                        <th>JSON 배열 형태</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><strong>JSP에서 사용 편의성</strong></td>
                        <td class="advantage">✅ 매우 간단</td>
                        <td class="disadvantage">❌ 복잡함</td>
                    </tr>
                    <tr>
                        <td><strong>중복 제거</strong></td>
                        <td class="advantage">✅ 자동 처리</td>
                        <td class="disadvantage">❌ 수동 처리 필요</td>
                    </tr>
                    <tr>
                        <td><strong>검색 성능</strong></td>
                        <td class="advantage">✅ O(1) 시간복잡도</td>
                        <td class="disadvantage">❌ O(n) 시간복잡도</td>
                    </tr>
                    <tr>
                        <td><strong>메모리 사용량</strong></td>
                        <td class="disadvantage">❌ 약간 더 많음</td>
                        <td class="advantage">✅ 적음</td>
                    </tr>
                    <tr>
                        <td><strong>JavaScript 호환성</strong></td>
                        <td class="disadvantage">❌ 변환 필요</td>
                        <td class="advantage">✅ 직접 사용 가능</td>
                    </tr>
                    <tr>
                        <td><strong>코드 가독성</strong></td>
                        <td class="advantage">✅ 매우 좋음</td>
                        <td class="disadvantage">❌ 복잡함</td>
                    </tr>
                </tbody>
            </table>
        </div>
        
        <!-- 6. 사용 예시 -->
        <div class="section">
            <h2>💡 6. JSP에서 사용 예시</h2>
            
            <div class="usage-example">
                <h4>Map 형태 사용 (권장)</h4>
                <div class="code-block">
&lt;!-- 간단한 사용 --&gt;
&lt;c:forEach var="field" items="${fieldsSimpleMap}"&gt;
    &lt;h3&gt;${field.key}&lt;/h3&gt;
    &lt;select&gt;
        &lt;c:forEach var="value" items="${field.value}"&gt;
            &lt;option value="${value}"&gt;${value}&lt;/option&gt;
        &lt;/c:forEach&gt;
    &lt;/select&gt;
&lt;/c:forEach&gt;

&lt;!-- 특정 필드 검색 --&gt;
&lt;c:if test="${fieldsSimpleMap.containsKey('RAM')}"&gt;
    &lt;h3&gt;RAM 옵션&lt;/h3&gt;
    &lt;c:forEach var="ramValue" items="${fieldsSimpleMap['RAM']}"&gt;
        &lt;span&gt;${ramValue}&lt;/span&gt;
    &lt;/c:forEach&gt;
&lt;/c:if&gt;
                </div>
            </div>
            
            <div class="usage-example">
                <h4>JSON 배열 형태 사용</h4>
                <div class="code-block">
&lt;!-- 복잡한 사용 --&gt;
&lt;c:forEach var="dto" items="${filteredDTOList}"&gt;
    &lt;h3&gt;${dto.attributeKey}&lt;/h3&gt;
    &lt;select&gt;
        &lt;c:forEach var="item" items="${dto.valueListArray}"&gt;
            &lt;option value="${item.value}"&gt;${item.value}&lt;/option&gt;
        &lt;/c:forEach&gt;
    &lt;/select&gt;
&lt;/c:forEach&gt;
                </div>
            </div>
        </div>
        
        <!-- 7. 결론 -->
        <div class="section">
            <h2>🎯 7. 결론</h2>
            <div class="data-display">
                <h3>View 파일에서는 <span class="advantage">Map 형태</span>가 더 적합합니다!</h3>
                <ul>
                    <li><strong>JSP 사용 편의성:</strong> Map이 훨씬 간단하고 직관적</li>
                    <li><strong>성능:</strong> 검색 및 필터링이 빠름</li>
                    <li><strong>중복 처리:</strong> 자동으로 중복 제거</li>
                    <li><strong>코드 가독성:</strong> 더 깔끔하고 이해하기 쉬움</li>
                </ul>
                <p><strong>권장사항:</strong> JSP View에서는 Map 형태를 사용하고, JavaScript나 API 응답에서는 JSON 배열 형태를 사용하는 것이 최적입니다.</p>
            </div>
        </div>
    </div>
</body>
</html> 