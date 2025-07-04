<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>결제 성공 - 싸싸</title>
    
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
        .success-container {
            max-width: 600px;
            margin: 140px auto 80px;
            padding: 0 20px;
        }
        
        .success-card {
            background-color: rgba(255, 255, 255, 0.04);
            border-radius: 12px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
            padding: 40px;
            text-align: center;
        }
        
        .success-icon {
            font-size: 64px;
            color: #28a745;
            margin-bottom: 20px;
        }
        
        .success-title {
            font-size: 35px;
            color: var(--text-1);
            max-width: fit-content;
            margin-bottom: 10px;
            font-weight: bold;
        }
        
        .success-subtitle {
            color: var(--text-2);
            font-size: 16px;
            margin-bottom: 30px;
        }
        
        .payment-details {
            background-color: rgba(255, 255, 255, 0.08);
            border: 1px solid rgba(255, 255, 255, 0.1);
            border-radius: 8px;
            padding: 25px;
            margin-bottom: 30px;
            text-align: left;
        }
        
        .payment-details h3 {
            color: var(--text-1);
            margin-bottom: 20px;
            font-size: 18px;
            text-align: center;
        }
        
        .detail-item {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 12px 0;
            border-bottom: 1px solid rgba(255, 255, 255, 0.1);
        }
        
        .detail-item:last-child {
            border-bottom: none;
        }
        
        .detail-label {
            color: var(--text-2);
            font-weight: 500;
        }
        
        .detail-value {
            color: var(--text-1);
            font-weight: bold;
        }
        
        .amount-value {
            color: var(--brand);
            font-size: 18px;
            font-weight: bold;
        }
        
        .status-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 6px;
            font-size: 12px;
            font-weight: bold;
            background-color: #28a745;
            color: white;
        }
        
        .action-buttons {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
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
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-success {
            background-color: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background-color: #218838;
        }
        
        .info-box {
            background-color: rgba(255, 193, 7, 0.1);
            border: 1px solid rgba(255, 193, 7, 0.3);
            border-radius: 6px;
            padding: 15px;
            margin-bottom: 20px;
            text-align: left;
        }
        
        .info-box h4 {
            color: var(--text-1);
            margin-bottom: 10px;
            font-size: 16px;
        }
        
        .info-box ul {
            margin: 0;
            padding-left: 20px;
            color: var(--text-1);
        }
        
        .info-box li {
            margin-bottom: 5px;
            font-size: 14px;
        }
        
        @media (max-width: 768px) {
            .success-card {
                padding: 30px 20px;
            }
            
            .action-buttons {
                flex-direction: column;
            }
            
            .btn {
                justify-content: center;
            }
            
            .detail-item {
                flex-direction: column;
                align-items: flex-start;
                gap: 5px;
            }
        }
    </style>
</head>

<body class="noto-sans-kr-regular">
    <div class="success-container">
        <div class="success-card">
            <!-- 성공 아이콘 -->
            <div class="success-icon">
                <span class="material-symbols-outlined">check_circle</span>
            </div>
            
            <!-- 성공 메시지 -->
            <h1 class="success-title">🎉 결제가 완료되었습니다!</h1>
            <p class="success-subtitle">안전하고 빠른 결제가 성공적으로 처리되었습니다.</p>
            
            <!-- 결제 정보 -->
            <div class="payment-details">
                <h3>📋 결제 정보</h3>
                <div class="detail-item">
                    <span class="detail-label">상품명</span>
                    <span class="detail-value">${payment.itemName}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">결제 금액</span>
                    <span class="detail-value amount-value">${payment.amount}원</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">결제 상태</span>
                    <span class="status-badge">결제 완료</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">결제 번호</span>
                    <span class="detail-value">${payment.id}</span>
                </div>
                <div class="detail-item">
                    <span class="detail-label">결제 일시</span>
                    <span class="detail-value">
                        <fmt:formatDate value="${formatUtil.LocalDateTimeToDate(payment.createdAt)}"
                        pattern="yyyy년 MM월 dd일 HH:mm" />
                    </span>
                </div>
                <c:if test="${not empty payment.tid}">
                    <div class="detail-item">
                        <span class="detail-label">카카오페이 TID</span>
                        <span class="detail-value">${payment.tid}</span>
                    </div>
                </c:if>
            </div>
            
            <!-- 안내 정보 -->
            <div class="info-box">
                <h4>📢 주문 안내</h4>
                <ul>
                    <li>주문 확인 이메일이 발송되었습니다.</li>
                    <li>상품은 1-2일 내에 배송됩니다.</li>
                    <li>배송 관련 문의는 고객센터로 연락해주세요.</li>
                    <li>마이페이지에서 주문 현황을 확인할 수 있습니다.</li>
                </ul>
            </div>
            
            <!-- 액션 버튼 -->
            <div class="action-buttons">
                <a href="/mypage" class="btn btn-primary">
                    <span class="material-symbols-outlined">account_circle</span>
                    마이페이지로 이동
                </a>
                <a href="/products" class="btn btn-secondary">
                    <span class="material-symbols-outlined">shopping_bag</span>
                    쇼핑 계속하기
                </a>
                <a href="/payments" class="btn btn-success">
                    <span class="material-symbols-outlined">receipt_long</span>
                    결제내역 보기
                </a>
            </div>
        </div>
    </div>
</body>
</html>
