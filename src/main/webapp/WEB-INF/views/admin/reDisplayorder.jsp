<%@ page contentType="text/html; charset=UTF-8" language="java" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <!DOCTYPE html>
    <html lang="ko">

    <head>
      <meta charset="UTF-8">
      <title>카테고리 필드 순서 관리 - 싸싸</title>
      <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
      <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
      <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/admin.css">
      <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/common.css">
      <style>
        .form-card {
          background: white;
          border-radius: 10px;
          padding: 30px;
          box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .fields-table {
          width: 100%;
          border-collapse: collapse;
          margin-top: 20px;
        }

        .fields-table th,
        .fields-table td {
          padding: 12px;
          text-align: left;
          border-bottom: 1px solid #ddd;
        }

        .fields-table th {
          background-color: #f8f9fa;
          font-weight: bold;
        }

        .fields-table tr:hover {
          background-color: #f8f9fa;
        }

        .field-row {
          cursor: pointer;
        }

        .field-row.selected {
          background-color: #e3f2fd;
        }

        .step-indicator {
          display: flex;
          margin-bottom: 30px;
          background: #f8f9fa;
          border-radius: 10px;
          padding: 20px;
        }

        .step {
          flex: 1;
          text-align: center;
          padding: 10px;
          position: relative;
        }

        .step.active {
          color: #007bff;
          font-weight: bold;
        }

        .step.completed {
          color: #28a745;
        }

        .step:not(:last-child)::after {
          content: '→';
          position: absolute;
          right: -10px;
          top: 50%;
          transform: translateY(-50%);
          color: #6c757d;
        }
      </style>
      <script>
        const currentCategoryId = Number('${categoryId}');
        const isSpecialCategory = (currentCategoryId === 5 || currentCategoryId === 8 || currentCategoryId === 9);
        let fieldsCache = [];

        window.onload = function () {
          if (isSpecialCategory) {
            document.getElementById('childId').addEventListener('change', onChildCategoryChange);
          } else {
            loadFields();
          }
        };

        function onChildCategoryChange() {
          const childId = document.getElementById('childId').value;
          if (childId) {
            loadFields(childId);
            updateStepIndicator(2);
          }
        }

        function loadFields(childId) {
          let url = "/cat/fields/" + currentCategoryId;
          if (childId) url += "?childId=" + childId;
          fetch(url)
            .then(res => res.json())
            .then(fields => {
              fieldsCache = fields;
              const fieldSelect = document.getElementById('fieldSelect');
              fieldSelect.innerHTML = '<option value="">필드를 선택하세요</option>';
              fields.forEach(field => {
                const option = document.createElement('option');
                option.value = field.id;
                option.textContent = field.displayName;
                fieldSelect.appendChild(option);
              });
              updateStepIndicator(isSpecialCategory ? 2 : 1);
            });
        }

        function onFieldChange() {
          const fieldId = document.getElementById('fieldSelect').value;
          if (!fieldId) return;
          const field = fieldsCache.find(f => String(f.id) === String(fieldId));
          if (field) {
            document.getElementById('displayOrder').value = field.displayOrder;
            document.getElementById('oldOrder').value = field.displayOrder;

            updateStepIndicator(isSpecialCategory ? 3 : 2);
            document.getElementById('submitBtn').disabled = true;
            document.getElementById('fieldId').value = fieldId;
          }
        }

        document.addEventListener('DOMContentLoaded', function() {
          document.getElementById('displayOrder').addEventListener('input', function() {
            const fieldId = document.getElementById('fieldSelect').value;
            if (fieldId && this.value) {
              document.getElementById('submitBtn').disabled = false;
            } else {
              document.getElementById('submitBtn').disabled = true;
            }
          });
        });

        function updateStepIndicator(step) {
          document.querySelectorAll('.step').forEach((el, index) => {
            el.classList.remove('active', 'completed');
            if (index + 1 < step) {
              el.classList.add('completed');
            } else if (index + 1 === step) {
              el.classList.add('active');
            }
          });
        }

        function submitForm() {
          const fieldId = document.getElementById('fieldSelect').value;
          if (!fieldId) {
            alert('필드를 선택해주세요.');
            return false;
          }
          const field = fieldsCache.find(f => String(f.id) === String(fieldId));
          const displayOrder = document.getElementById('displayOrder').value;
          if (!displayOrder || isNaN(displayOrder)) {
            alert('올바른 순서 번호를 입력해주세요.');
            return false;
          }
          document.getElementById('categoryChildId').value = parseInt(field.categoryChildId) == 0 ? -1 : parseInt(field.categoryChildId);
          document.getElementById('fieldId').value = parseInt(fieldId) == 0 ? -1 : parseInt(fieldId);
          document.getElementById('categoryId').value = parseInt(currentCategoryId);
          document.getElementById('oldOrder').value = parseInt(field.displayOrder);
          document.getElementById('newOrder').value = parseInt(displayOrder);
          return true;
        }
      </script>
    </head>

    <body class="noto-sans-kr-regular">
      <main>
        <div class="admin-container">
          <div class="form-container">
            <a href="/admin/categories" class="back-link">← 카테고리 관리로 돌아가기</a>

            <h2 class="admin-title">
              <span class="material-symbols-outlined">sort</span>
              카테고리 필드 순서 관리
            </h2>

            <div class="step-indicator">
              <c:choose>
                <c:when test="${categoryId == 5 || categoryId == 8 || categoryId == 9}">
                  <div class="step active">1. 하위 카테고리 선택</div>
                  <div class="step">2. 필드 선택</div>
                  <div class="step">3. 순서 변경</div>
                </c:when>
                <c:otherwise>
                  <div class="step active">1. 필드 선택</div>
                  <div class="step">2. 순서 변경</div>
                </c:otherwise>
              </c:choose>
            </div>

            <div class="form-card">
              <form onsubmit="return submitForm()" action="/cat/set/displayOrder-static" method="post">
                <input type="hidden" id="fieldId" name="fieldId" value="">
                <input type="hidden" id="categoryId" name="categoryId" value="${categoryId}">
                <input type="hidden" id="categoryChildId" name="categoryChildId">

                <c:if test="${categoryId == 5 || categoryId == 8 || categoryId == 9}">
                  <div class="form-group">
                    <label for="childId">하위 카테고리</label>
                    <select id="childId" name="childId" onchange="onChildCategoryChange()">
                      <option value="">하위 카테고리 선택</option>
                      <c:forEach var="child" items="${categoryChildren}">
                        <option value="${child.id}">${child.name}</option>
                      </c:forEach>
                    </select>
                  </div>
                </c:if>

                <div class="form-group">
                  <label for="fieldSelect">필드</label>
                  <select id="fieldSelect" name="fieldSelect" onchange="onFieldChange()">
                    <option value="">필드를 선택하세요</option>
                  </select>
                </div>

                <div class="form-group">
                  <label for="displayOrder">표시 순서</label>
                  <input type="number" id="displayOrder" name="newOrder" min="1">
                  <input type="hidden" name="oldOrder" id="oldOrder">
                </div>

                <button type="submit" class="btn" disabled id="submitBtn">변경하기</button>
              </form>

              <table id="fieldsTable" class="fields-table" style="display: none;">
                <thead>
                  <tr>
                    <th>ID</th>
                    <th>표시명</th>
                    <th>데이터 타입</th>
                    <th>현재 순서</th>
                    <th>툴팁</th>
                  </tr>
                </thead>
                <tbody id="fieldsTableBody"></tbody>
              </table>
            </div>
          </div>
        </div>
      </main>
    </body>

    </html>
    </html>
