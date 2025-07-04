<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>문의 작성 - 싸싸</title>
    
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
        .write-container {
            max-width: 800px;
            margin: 140px auto 80px;
            padding: 0 20px;
        }
        
        .write-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
        }
        
        .write-title {
            font-size: 28px;
            color: var(--text-1);
            margin: 0;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .write-form {
            background-color: rgba(255, 255, 255, 0.04);
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            padding: 30px;
        }
        
        .form-group {
            margin-bottom: 25px;
        }
        
        .form-label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            font-size: 14px;
        }

        .form-group .form-label {
            color: #ffffff;
        }

        .form-group label {
            color: #ffffff;
        }
        
        .form-control {
            width: 100%;
            padding: 12px;
            background-color: rgba(255, 255, 255, 0.1);
            border: 1px solid rgba(255, 255, 255, 0.2);
            border-radius: 6px;
            color: #ffffff;
            font-size: 14px;
            transition: all 0.3s ease;
            box-sizing: border-box;
        }
        
        .form-control:focus {
            outline: none;
            background-color: rgba(255, 255, 255, 0.15);
            border-color: var(--brand);
            box-shadow: 0 0 0 2px rgba(255, 193, 7, 0.25);
        }
        
        .form-control::placeholder {
            color: rgba(255, 255, 255, 0.6);
        }
        
        textarea.form-control {
            resize: vertical;
            min-height: 200px;
            font-family: inherit;
        }

        .file-input-wrapper {
            position: relative;
            display: inline-block;
            width: 100%;
        }
        
        .file-input {
            position: absolute;
            left: -9999px;
        }
        
        .file-label {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 12px 20px;
            background-color: rgba(255, 255, 255, 0.1);
            border: 2px dashed rgba(255, 255, 255, 0.3);
            border-radius: 6px;
            color: #ffffff;
            cursor: pointer;
            transition: all 0.3s ease;
            text-align: center;
            justify-content: center;
        }
        
        .file-label:hover {
            background-color: rgba(255, 255, 255, 0.15);
            border-color: var(--brand);
        }
        
        .file-name {
            margin-top: 10px;
            padding: 8px 12px;
            background-color: rgba(255, 255, 255, 0.1);
            border-radius: 4px;
            color: #ffffff;
            font-size: 12px;
            display: none;
        }
        
        .button-group {
            display: flex;
            gap: 15px;
            justify-content: flex-end;
            margin-top: 30px;
        }
        
        .btn {
            padding: 12px 24px;
            font-size: 16px;
            border-radius: 6px;
            text-decoration: none;
            border: none;
            cursor: pointer;
            transition: all 0.3s ease;
            font-weight: 500;
            display: inline-flex;
            align-items: center;
            gap: 8px;
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
        
        .required {
            color: #ff6b6b;
            font-weight: bold;
        }
        
        @media (max-width: 768px) {
            .write-header {
                flex-direction: column;
                gap: 15px;
                align-items: flex-start;
            }
            
            .button-group {
                flex-direction: column;
            }
            
            .btn {
                justify-content: center;
            }
        }
    </style>
</head>

<body class="noto-sans-kr-regular">
    <div class="write-container">
        <a href="/inquiry/list" class="back-button">
            <span class="material-symbols-outlined">arrow_back</span>
            문의 목록으로 돌아가기
        </a>

        <div class="write-header">
            <h1 class="write-title">
                <span class="material-symbols-outlined">edit_note</span>
                문의 작성
            </h1>
        </div>

        <div class="write-form">
            <form action="/inquiry/write" method="post" enctype="multipart/form-data" id="inquiryForm">
                <div class="form-group">
                    <label for="title" class="form-label">제목 <span class="required">*</span></label>
                    <input type="text" id="title" name="title" class="form-control" placeholder="문의 제목을 입력해주세요" required>
                </div>

                <div class="form-group">
                    <label for="content" class="form-label">내용 <span class="required">*</span></label>
                    <textarea id="content" name="content" class="form-control" placeholder="문의 내용을 자세히 작성해주세요" required></textarea>
                </div>

                <div class="form-group">
                    <label class="form-label">첨부파일</label>
                    <div class="file-input-wrapper">
                        <input type="file" id="file" name="file" class="file-input" accept="image/*,.pdf,.doc,.docx,.txt">
                        <label for="file" class="file-label">
                            <span class="material-symbols-outlined">upload_file</span>
                            파일을 선택하거나 여기에 드래그하세요
                        </label>
                    </div>
                    <div id="fileName" class="file-name"></div>
                </div>

                <div class="button-group">
                    <a href="/inquiry/list" class="btn btn-secondary">
                        <span class="material-symbols-outlined">cancel</span>
                        취소
                    </a>
                    <button type="submit" class="btn btn-primary">
                        <span class="material-symbols-outlined">send</span>
                        문의 등록
                    </button>
                </div>
            </form>
        </div>
    </div>

    <script>
        // 파일 선택 시 파일명 표시
        document.getElementById('file').addEventListener('change', function(e) {
            const fileName = e.target.files[0]?.name;
            const fileNameDiv = document.getElementById('fileName');
            
            if (fileName) {
                fileNameDiv.textContent = '선택된 파일: ' + fileName;
                fileNameDiv.style.display = 'block';
            } else {
                fileNameDiv.style.display = 'none';
            }
        });

        // 폼 제출 전 확인
        document.getElementById('inquiryForm').addEventListener('submit', function(e) {
            const title = document.getElementById('title').value.trim();
            const content = document.getElementById('content').value.trim();
            
            if (!title) {
                alert('제목을 입력해주세요.');
                e.preventDefault();
                return;
            }
            
            if (!content) {
                alert('내용을 입력해주세요.');
                e.preventDefault();
                return;
            }
        });
    </script>
</body>
</html>