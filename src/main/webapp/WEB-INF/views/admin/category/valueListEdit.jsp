<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="ko">

<head>
  <meta charset="UTF-8">
  <title>값 목록 편집 - 싸싸</title>
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
      text-align: center;
      font-weight: bold;
      color: #2c3e50;
      border-bottom: 2px solid #dee2e6;
      white-space: nowrap;
    }

    .data-table td {
      padding: 12px 10px;
      border-bottom: 1px solid #dee2e6;
      text-align: center;
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
      text-align: center;
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
      margin: 5px;
    }

    .btn:hover {
      background: #0056b3;
    }

    .btn-secondary {
      background: #6c757d;
    }

    .btn-secondary:hover {
      background: #545b62;
    }

    .btn-danger {
      background: #dc3545;
    }

    .btn-danger:hover {
      background: #c82333;
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

    .field-info {
      background: #f8f9fa;
      padding: 20px;
      border-radius: 10px;
      margin-bottom: 20px;
      border-left: 4px solid #007bff;
    }

    .field-info h3 {
      margin: 0 0 10px 0;
      color: #2c3e50;
    }

    .field-info p {
      margin: 5px 0;
      color: #6c757d;
    }

    .no-data {
      text-align: center;
      padding: 40px;
      color: #6c757d;
      font-style: italic;
    }

    .action-buttons {
      text-align: center;
      margin-top: 20px;
    }

    .add-row-btn {
      background: #28a745;
      color: white;
      padding: 8px 16px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
      font-size: 0.9rem;
      margin-bottom: 20px;
    }

    .add-row-btn:hover {
      background: #218838;
    }

    .remove-row-btn {
      background: #dc3545;
      color: white;
      padding: 4px 8px;
      border: none;
      border-radius: 3px;
      cursor: pointer;
      font-size: 0.8rem;
    }

    .remove-row-btn:hover {
      background: #c82333;
    }
  </style>
</head>

<body class="noto-sans-kr-regular">
  <main>
    <div class="admin-container">
      <a href="/cat/displayOrder-All/${field.categoryId}" class="back-link">← 카테고리 필드 관리로 돌아가기</a>

      <h2 class="admin-title">
        <span class="material-symbols-outlined">edit</span>
        값 목록 편집
      </h2>

      <!-- 필드 정보 표시 -->
      <div class="field-info">
        <h3>필드 정보</h3>
        <p><strong>속성 키:</strong> ${field.attributeKey}</p>
        <p><strong>표시명:</strong> ${field.displayName}</p>
        <p><strong>데이터 타입:</strong> ${field.dataType}</p>
        <p><strong>단위:</strong> ${field.unit}</p>
      </div>

      <form action="/cat/update/valueList" method="post">
        <input type="hidden" name="fieldId" value="${field.id}" />
        
        <div class="table-container">
          <button type="button" class="add-row-btn" onclick="addRow()">
            <span class="material-symbols-outlined">add</span> 새 행 추가
          </button>
          
          <table class="data-table" id="valueListTable">
            <thead>
              <tr>
                <th>Value</th>
                <th>Weight</th>
                <th>삭제</th>
              </tr>
            </thead>
            <tbody id="valueListBody">
              <c:choose>
                <c:when test="${not empty valueListMap}">
                  <c:forEach var="entry" items="${valueListMap}">
                    <tr>
                      <td>
                        <input type="text" name="values" value="${entry.key}" required />
                      </td>
                      <td>
                        <input type="text" name="weights" value="${entry.value}" required />
                      </td>
                      <td>
                        <button type="button" class="remove-row-btn" onclick="removeRow(this)">
                          <span class="material-symbols-outlined">delete</span>
                        </button>
                      </td>
                    </tr>
                  </c:forEach>
                </c:when>
                <c:otherwise>
                  <tr>
                    <td>
                      <input type="text" name="values" placeholder="값을 입력하세요" required />
                    </td>
                    <td>
                      <input type="text" name="weights" placeholder="가중치를 입력하세요" required />
                    </td>
                    <td>
                      <button type="button" class="remove-row-btn" onclick="removeRow(this)">
                        <span class="material-symbols-outlined">delete</span>
                      </button>
                    </td>
                  </tr>
                </c:otherwise>
              </c:choose>
            </tbody>
          </table>
        </div>

        <div class="action-buttons">
          <button type="submit" class="btn">저장</button>
          <a href="/cat/displayOrder-All/${field.categoryId}" class="btn btn-secondary">취소</a>
        </div>
      </form>
    </div>
  </main>

  <script>
    // weight 값 변경을 추적하기 위한 변수
    let originalWeights = [];
    let isAdjusting = false;

    // 페이지 로드 시 원본 weight 값들을 저장
    document.addEventListener('DOMContentLoaded', function() {
      const weightInputs = document.querySelectorAll('input[name="weights"]');
      originalWeights = Array.from(weightInputs).map(input => parseInt(input.value) || 0);
      
      // weight 입력 필드에 이벤트 리스너 추가
      weightInputs.forEach((input, index) => {
        input.addEventListener('change', function() {
          adjustWeights(index, parseInt(this.value) || 0);
        });
      });
    });

    function addRow() {
      const tbody = document.getElementById('valueListBody');
      const newRow = document.createElement('tr');
      const newIndex = tbody.children.length;
      
      newRow.innerHTML = `
        <td>
          <input type="text" name="values" placeholder="값을 입력하세요" required />
        </td>
        <td>
          <input type="text" name="weights" placeholder="가중치를 입력하세요" required />
        </td>
        <td>
          <button type="button" class="remove-row-btn" onclick="removeRow(this)">
            <span class="material-symbols-outlined">delete</span>
          </button>
        </td>
      `;
      tbody.appendChild(newRow);
      
      // 새 행의 weight 입력 필드에 이벤트 리스너 추가
      const newWeightInput = newRow.querySelector('input[name="weights"]');
      newWeightInput.addEventListener('change', function() {
        adjustWeights(newIndex, parseInt(this.value) || 0);
      });
      
      // 원본 weight 배열에 새 값 추가 (기본값: 현재 행 수)
      originalWeights.push(newIndex + 1);
    }

    function removeRow(button) {
      const tbody = document.getElementById('valueListBody');
      if (tbody.children.length > 1) {
        const rowIndex = Array.from(tbody.children).indexOf(button.closest('tr'));
        button.closest('tr').remove();
        
        // 원본 weight 배열에서 해당 인덱스 제거
        originalWeights.splice(rowIndex, 1);
        
        // 나머지 weight 값들을 순차적으로 재조정
        reorderWeights();
      } else {
        alert('최소 하나의 행은 유지해야 합니다.');
      }
    }

    function adjustWeights(changedIndex, newWeight) {
      if (isAdjusting) return;
      isAdjusting = true;
      
      const weightInputs = document.querySelectorAll('input[name="weights"]');
      const oldWeight = originalWeights[changedIndex];
      
      if (newWeight === oldWeight) {
        isAdjusting = false;
        return;
      }
      
      // weight 값들을 배열로 변환
      let weights = Array.from(weightInputs).map(input => parseInt(input.value) || 0);
      
      if (newWeight > oldWeight) {
        // weight가 증가한 경우: oldWeight + 1 ~ newWeight 범위의 값들을 1씩 감소
        for (let i = 0; i < weights.length; i++) {
          if (i !== changedIndex && weights[i] >= oldWeight + 1 && weights[i] <= newWeight) {
            weights[i]--;
            weightInputs[i].value = weights[i];
          }
        }
      } else {
        // weight가 감소한 경우: newWeight ~ oldWeight - 1 범위의 값들을 1씩 증가
        for (let i = 0; i < weights.length; i++) {
          if (i !== changedIndex && weights[i] >= newWeight && weights[i] <= oldWeight - 1) {
            weights[i]++;
            weightInputs[i].value = weights[i];
          }
        }
      }
      
      // 변경된 weight를 원본 배열에 반영
      originalWeights[changedIndex] = newWeight;
      
      // 다른 weight 값들도 원본 배열에 반영
      for (let i = 0; i < weights.length; i++) {
        if (i !== changedIndex) {
          originalWeights[i] = weights[i];
        }
      }
      
      isAdjusting = false;
    }

    function reorderWeights() {
      const weightInputs = document.querySelectorAll('input[name="weights"]');
      const weights = Array.from(weightInputs).map((input, index) => ({
        index: index,
        value: parseInt(input.value) || 0
      }));
      
      // weight 값으로 정렬
      weights.sort((a, b) => a.value - b.value);
      
      // 순차적으로 재할당
      weights.forEach((item, newIndex) => {
        weightInputs[item.index].value = newIndex + 1;
        originalWeights[item.index] = newIndex + 1;
      });
    }

    // 폼 제출 전 유효성 검사
    document.querySelector('form').addEventListener('submit', function(e) {
      const values = document.querySelectorAll('input[name="values"]');
      const weights = document.querySelectorAll('input[name="weights"]');
      
      for (let i = 0; i < values.length; i++) {
        if (!values[i].value.trim() || !weights[i].value.trim()) {
          e.preventDefault();
          alert('모든 필드를 입력해주세요.');
          return;
        }
        
        // weight가 숫자인지 확인
        if (isNaN(weights[i].value)) {
          e.preventDefault();
          alert('가중치는 숫자로 입력해주세요.');
          return;
        }
      }
      
      // weight 값이 중복되지 않는지 확인
      const weightValues = Array.from(weights).map(input => parseInt(input.value));
      const uniqueWeights = new Set(weightValues);
      if (uniqueWeights.size !== weightValues.length) {
        e.preventDefault();
        alert('가중치 값이 중복되지 않도록 해주세요.');
        return;
      }
    });
  </script>
</body>

</html> 