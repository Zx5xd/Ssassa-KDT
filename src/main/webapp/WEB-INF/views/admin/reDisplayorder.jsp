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
        let currentCategoryId = ${ categoryId };
        let selectedChildId = null;
        let selectedAttributeKey = null;
        let selectedField = null;

        // 페이지 로드 시 하위 카테고리 목록 로드
        window.onload = function () {
          loadCategoryChildren();
        };

        // 하위 카테고리 목록 로드
        async function loadCategoryChildren() {
          try {
            const response = await fetch(`/cat/api/categories/${currentCategoryId}/children`);
            const children = await response.json();

            const childSelect = document.getElementById('childId');
            childSelect.innerHTML = '<option value="">하위 카테고리 선택 (선택사항)</option>';

            children.forEach(child => {
              const option = document.createElement('option');
              option.value = child.id;
              option.textContent = child.name;
              childSelect.appendChild(option);
            });

            updateStepIndicator(1);
          } catch (error) {
            console.error('하위 카테고리 로드 실패:', error);
          }
        }

        // 하위 카테고리 선택 시 속성 키 목록 로드
        async function onChildCategoryChange() {
          const childSelect = document.getElementById('childId');
          selectedChildId = childSelect.value || null;

          // 속성 키 목록 초기화
          const attributeSelect = document.getElementById('attributeKey');
          attributeSelect.innerHTML = '<option value="">속성 키를 선택하세요</option>';

          // 필드 테이블 초기화
          document.getElementById('fieldsTable').style.display = 'none';

          if (selectedChildId) {
            await loadAttributeKeys();
          }

          updateStepIndicator(2);
        }

        // 속성 키 목록 로드
        async function loadAttributeKeys() {
          try {
            let url = `/cat/api/categories/${currentCategoryId}/attributes`;
            if (selectedChildId) {
              url += `?childId=${selectedChildId}`;
            }

            const response = await fetch(url);
            const attributes = await response.json();

            const attributeSelect = document.getElementById('attributeKey');
            attributeSelect.innerHTML = '<option value="">속성 키를 선택하세요</option>';

            attributes.forEach(attr => {
              const option = document.createElement('option');
              option.value = attr.attributeKey;
              option.textContent = attr.attributeKey;
              attributeSelect.appendChild(option);
            });
          } catch (error) {
            console.error('속성 키 로드 실패:', error);
          }
        }

        // 속성 키 선택 시 필드 목록 로드
        async function onAttributeKeyChange() {
          const attributeSelect = document.getElementById('attributeKey');
          selectedAttributeKey = attributeSelect.value || null;

          if (selectedAttributeKey) {
            await loadFields();
          }

          updateStepIndicator(3);
        }

        // 필드 목록 로드
        async function loadFields() {
          try {
            let url = `/cat/api/categories/${currentCategoryId}/fields`;
            const params = new URLSearchParams();

            if (selectedChildId) {
              params.append('childId', selectedChildId);
            }
            if (selectedAttributeKey) {
              params.append('attributeKey', selectedAttributeKey);
            }

            if (params.toString()) {
              url += `?${params.toString()}`;
            }

            const response = await fetch(url);
            const fields = await response.json();

            const tbody = document.getElementById('fieldsTableBody');
            tbody.innerHTML = '';

            fields.forEach(field => {
              const row = document.createElement('tr');
              row.className = 'field-row';
              row.onclick = () => selectField(field);

              row.innerHTML = `
            <td>${field.id}</td>
            <td>${field.displayName}</td>
            <td>${field.dataType}</td>
            <td>${field.displayOrder}</td>
            <td>${field.tooltip || '-'}</td>
          `;

              tbody.appendChild(row);
            });

            document.getElementById('fieldsTable').style.display = 'table';
          } catch (error) {
            console.error('필드 로드 실패:', error);
          }
        }

        // 필드 선택
        function selectField(field) {
          // 이전 선택 해제
          document.querySelectorAll('.field-row').forEach(row => {
            row.classList.remove('selected');
          });

          // 현재 선택 표시
          event.target.closest('.field-row').classList.add('selected');

          selectedField = field;
          document.getElementById('fieldId').value = field.id;
          document.getElementById('displayOrder').value = field.displayOrder;
          document.getElementById('displayOrder').disabled = false;
          document.getElementById('submitBtn').disabled = false;

          updateStepIndicator(4);
        }

        // 단계 표시 업데이트
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

        // 폼 제출
        function submitForm() {
          if (!selectedField) {
            alert('필드를 선택해주세요.');
            return false;
          }

          const displayOrder = document.getElementById('displayOrder').value;
          if (!displayOrder || isNaN(displayOrder)) {
            alert('올바른 순서 번호를 입력해주세요.');
            return false;
          }

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
              <div class="step active">1. 하위 카테고리 선택</div>
              <div class="step">2. 속성 키 선택</div>
              <div class="step">3. 필드 선택</div>
              <div class="step">4. 순서 변경</div>
            </div>

            <div class="form-card">
              <form onsubmit="return submitForm()" action="/cat/set/displayOrder" method="post">
                <input type="hidden" id="fieldId" name="fieldId" value="">

                <div class="form-group">
                  <label for="childId">하위 카테고리 (선택사항)</label>
                  <select id="childId" name="childId" onchange="onChildCategoryChange()">
                    <option value="">하위 카테고리 선택 (선택사항)</option>
                  </select>
                </div>

                <div class="form-group">
                  <label for="attributeKey">속성 키</label>
                  <select id="attributeKey" name="attributeKey" onchange="onAttributeKeyChange()">
                    <option value="">속성 키를 선택하세요</option>
                  </select>
                </div>

                <div class="form-group">
                  <label for="displayOrder">표시 순서</label>
                  <input type="number" id="displayOrder" name="displayOrder" min="1" disabled>
                </div>

                <button type="submit" class="btn" disabled id="submitBtn">
                  변경하기기
                </button>
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
                <tbody id="fieldsTableBody">
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </main>
    </body>

    </html>