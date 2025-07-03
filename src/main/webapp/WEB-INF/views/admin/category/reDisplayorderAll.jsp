<%@ page contentType="text/html; charset=UTF-8" language="java" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
      <!DOCTYPE html>
      <html lang="ko">

      <head>
        <meta charset="UTF-8">
        <title>카테고리 필드 전체 순서 관리 - 싸싸</title>
        <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/admin.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/common.css">
        <style>
          .table-container {
            margin: 30px 0;
            overflow-x: auto;
          }

          .data-table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
          }

          .data-table th {
            background: #f8f9fa;
            padding: 15px 10px;
            text-align: left;
            font-weight: bold;
            color: #2c3e50;
            border-bottom: 2px solid #dee2e6;
          }

          .data-table td {
            padding: 12px 10px;
            border-bottom: 1px solid #dee2e6;
          }

          .data-table tr:hover {
            background: #f8f9fa;
          }

          .data-table input[type="text"] {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 0.9rem;
          }

          .data-table input[type="text"]:focus {
            outline: none;
            border-color: #007bff;
            box-shadow: 0 0 0 2px rgba(0, 123, 255, 0.25);
          }

          .btn {
            background: #007bff;
            color: white;
            padding: 12px 24px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            transition: background-color 0.3s ease;
          }

          .btn:hover {
            background: #0056b3;
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

          .category-links {
            display: grid;
            grid-template-columns: repeat(7, 2fr);
            row-gap: 10px;
            column-gap: 20px;
            margin: 10px 0;
          }

          .category-links a {
            display: inline-block;
            padding: 8px 16px;
            background: #6c757d;
            color: white;
            text-decoration: none;
            width: auto;
            border-radius: 5px;
            transition: background-color 0.3s ease;
            text-align: center;
            justify-items: center;
          }

          .category-links a:hover {
            background: #545b62;
          }

          .category-links a:active {
            background: #007bff;
            color: white;
          }

          .no-data {
            text-align: center;
            padding: 40px;
            color: #6c757d;
            font-style: italic;
          }

          /* value/weight 테이블 스타일 */
          .value-weight-table {
            border-collapse: collapse;
            width: 100%;
            font-size: 0.8em;
            margin: 0;
          }

          .value-weight-table th {
            border: 1px solid #ddd;
            padding: 4px;
            text-align: center;
            font-weight: bold;
            color: #495057;
          }

          .value-weight-table td {
            border: 1px solid #ddd;
            padding: 4px;
            text-align: center;
          }

          /* Admin-title 줄바꿈 방지 */
          .admin-title {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
          }

          /* thead 내 테이블 헤더 줄바꿈 방지 */
          .data-table th {
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            text-align: center;
            justify-items: center;
          }

          /* 특정 헤더에만 줄바꿈 허용 (값 목록) */
          .data-table th.allow-wrap {
            white-space: normal;
            word-wrap: break-word;
          }
        </style>
        <script>
          if (typeof resultMsg !== 'undefined' && resultMsg !== "") {
            alert('${resultMsg}');
          }
        </script>
      </head>

      <body class="noto-sans-kr-regular">
        <main>
          <div class="admin-container">
            <a href="/admin/categories" class="back-link">← 카테고리 관리로 돌아가기</a>

            <h2 class="admin-title">
              <span class="material-symbols-outlined">table_chart</span>
              카테고리 필드 전체 순서 관리
            </h2>

            <c:if test="${not empty category}">
              <div class="category-links">
                <c:forEach items="${category}" var="cat">
                  <a href="/cat/displayOrder-All/${cat.id}">${cat.name}</a>
                </c:forEach>
              </div>
            </c:if>

            <!-- 값이 없을 때 -->
            <c:if test="${empty dtoList}">
              <div class="no-data">
                <p>데이터가 없습니다.</p>
              </div>
            </c:if>

            <!-- 값이 있을 때 -->
            <c:if test="${not empty dtoList}">
              <form action="/cat/set/displayOrder-All" method="post">
                <div class="table-container">
                  <table class="data-table">
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>카테고리 ID</th>
                        <th>하위 카테고리 ID</th>
                        <th>속성 키</th>
                        <th>표시명</th>
                        <th>데이터 타입</th>
                        <th>단위</th>
                        <th>표시 순서</th>
                        <th>툴팁</th>
                        <th>값 목록<br>변경하기</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach var="item" items="${dtoList}">
                        <tr>
                          <td>${item.id}</td>
                          <input type="hidden" name="fieldId" value="${item.id}" />
                          <td>${item.categoryId}</td>
                          <td>${item.categoryChildId}</td>
                          <td>${item.attributeKey}</td>
                          <td>${item.displayName}</td>
                          <td>${item.dataType}</td>
                          <td>${item.unit}</td>
                          <td>
                            <input type="text" name="orderNum" value="${item.displayOrder}" />
                          </td>
                          <td>${item.tooltip}</td>
                          <td>
                            <a href="/cat/edit/valueList/${item.id}" class="material-symbols-outlined">edit</a>
                          </td>
                          <!-- <td style="padding: 8px;">
                        <c:out value="${item.formattedValue}" escapeXml="false"/>
                    </td> -->
                        </tr>
                      </c:forEach>
                      <tr>
                        <td colspan="8"></td>
                        <td colspan="2">
                          <input type="submit" value="변경" class="btn" style="width: 100%">
                        </td>
                      </tr>
                    </tbody>
                  </table>
                </div>
              </form>
            </c:if>
          </div>
        </main>
      </body>

      </html>