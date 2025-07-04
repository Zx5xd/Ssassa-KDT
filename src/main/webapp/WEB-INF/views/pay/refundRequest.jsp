<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html lang="ko">

            <head>
                <meta charset="UTF-8">
                <title>환불 요청 - SSA</title>

                <!-- 폰트 및 아이콘 -->
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
                <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
                <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">

                <!-- 프로젝트 CSS -->
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/index.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/common.css">
                <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/Ssa-Front/css/profile.css">


                <style>
                    .refund-container {
                        max-width: 800px;
                        margin: 140px auto 80px;
                        padding: 0 20px;
                    }

                    .refund-card {
                        background-color: rgba(255, 255, 255, 0.04);
                        border-radius: 12px;
                        box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
                        padding: 30px;
                        margin-bottom: 20px;
                    }

                    .refund-header {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        margin-bottom: 30px;
                    }

                    .refund-header h2 {
                        margin: 0;
                        color: var(--text-1);
                    }

                    .payment-info {
                        background-color: rgba(255, 255, 255, 0.08);
                        border: 1px solid rgba(255, 255, 255, 0.1);
                        border-radius: 8px;
                        padding: 20px;
                        margin-bottom: 20px;
                    }

                    .payment-info h4 {
                        color: var(--text-1);
                        margin-bottom: 15px;
                    }

                    .payment-grid {
                        display: grid;
                        grid-template-columns: 1fr 1fr;
                        gap: 20px;
                    }

                    .payment-grid p {
                        margin: 8px 0;
                        color: var(--text-1);
                    }

                    .refund-form {
                        background-color: rgba(255, 255, 255, 0.08);
                        border: 1px solid rgba(255, 255, 255, 0.1);
                        border-radius: 8px;
                        padding: 20px;
                    }

                    .refund-form h4 {
                        color: var(--text-1);
                        margin-bottom: 15px;
                    }

                    .form-group {
                        margin-bottom: 20px;
                    }

                    .form-label {
                        display: block;
                        margin-bottom: 8px;
                        color: #ffffff;
                        font-weight: 500;
                        font-size: 14px;
                    }

                    .form-group label {
                        color: #ffffff;
                    }

                    .form-control,
                    .form-select {
                        width: 100%;
                        padding: 12px;
                        background-color: rgba(255, 255, 255, 0.1);
                        border: 1px solid rgba(255, 255, 255, 0.2);
                        border-radius: 6px;
                        color: #c5822d;
                        font-size: 14px;
                        transition: all 0.3s ease;
                    }

                    .form-control:focus,
                    .form-select:focus {
                        outline: none;
                        background-color: rgba(255, 255, 255, 0.15);
                        border-color: var(--brand);
                        box-shadow: 0 0 0 2px rgba(255, 193, 7, 0.25);
                    }

                    .form-control::placeholder {
                        color: var(--text-2);
                    }

                    .form-row {
                        display: grid;
                        grid-template-columns: 1fr 2fr;
                        gap: 10px;
                    }

                    .form-text {
                        font-size: 12px;
                        color: #cccccc;
                        margin-top: 5px;
                    }

                    .form-check {
                        display: flex;
                        align-items: flex-start;
                        gap: 10px;
                        margin-bottom: 20px;
                    }

                    .form-check-input {
                        margin-top: 3px;
                    }

                    .form-check .form-check-input {
                        max-width: 16px;
                        max-height: 16px;
                    }

                    .form-check-label {
                        color: #ffffff;
                        font-size: 14px;
                        line-height: 1.4;
                    }

                    .status-badge {
                        display: inline-block;
                        font-size: 12px;
                        padding: 4px 8px;
                        border-radius: 4px;
                        font-weight: bold;
                    }

                    .warning-box {
                        background-color: rgba(255, 193, 7, 0.1);
                        border: 1px solid rgba(255, 193, 7, 0.3);
                        border-radius: 6px;
                        padding: 15px;
                        margin-bottom: 20px;
                    }

                    .warning-box h5 {
                        color: var(--text-1);
                        margin-bottom: 10px;
                    }

                    .warning-box ul {
                        margin: 0;
                        padding-left: 20px;
                        color: var(--text-1);
                    }

                    .warning-box li {
                        margin-bottom: 5px;
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
                    }

                    .btn:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                    }

                    .btn-secondary {
                        background-color: #6c757d;
                        color: white;
                    }

                    .btn-danger {
                        background-color: #dc3545;
                        color: white;
                    }

                    .btn-danger:hover {
                        background-color: #c82333;
                    }

                    .btn-group {
                        display: flex;
                        gap: 10px;
                        justify-content: flex-end;
                        margin-top: 20px;
                    }

                    .alert {
                        background-color: rgba(255, 255, 255, 0.08);
                        border: 1px solid rgba(255, 255, 255, 0.1);
                        border-radius: 6px;
                        padding: 15px;
                        margin-bottom: 20px;
                        color: var(--text-1);
                    }

                    .alert h5 {
                        color: var(--text-1);
                        margin-bottom: 10px;
                    }

                    .alert p {
                        margin: 5px 0;
                        color: var(--text-1);
                    }

                    .text-primary {
                        color: var(--brand);
                        font-weight: bold;
                    }

                    .text-danger {
                        color: #e74c3c;
                    }

                    .required {
                        color: #ff6b6b;
                        font-weight: bold;
                    }

                    @media (max-width: 768px) {
                        .payment-grid {
                            grid-template-columns: 1fr;
                        }

                        .form-row {
                            grid-template-columns: 1fr;
                        }

                        .btn-group {
                            flex-direction: column;
                        }

                        .refund-header {
                            flex-direction: column;
                            gap: 15px;
                            align-items: flex-start;
                        }
                    }
                </style>
            </head>

            <body class="noto-sans-kr-regular">
                <div class="refund-container">
                    <div class="refund-card">
                        <div class="refund-header">
                            <h2>🔄 환불 요청</h2>
                            <a href="/mypage" class="btn btn-secondary">
                                ← 마이페이지로 돌아가기
                            </a>
                        </div>

                        <!-- 경고 메시지 -->
                        <div class="warning-box">
                            <h5>⚠️ 환불 요청 안내</h5>
                            <ul>
                                <li>환불 요청은 결제 완료 후 7일 이내에만 가능합니다.</li>
                                <li>환불 요청 후 관리자 검토를 거쳐 처리됩니다.</li>
                                <li>상품을 사용하거나 포장을 훼손한 경우 환불이 제한될 수 있습니다.</li>
                                <li>환불 처리에는 3-5일이 소요될 수 있습니다.</li>
                            </ul>
                        </div>

                        <!-- 결제 정보 -->
                        <div class="payment-info">
                            <h4>📋 결제 정보</h4>
                            <div class="payment-grid">
                                <div>
                                    <p><strong>결제 번호:</strong> ${payment.id}</p>
                                    <p><strong>상품명:</strong> ${payment.itemName}</p>
                                    <p><strong>결제 금액:</strong> <span class="text-primary">${payment.amount}원</span></p>
                                </div>
                                <div>
                                    <p><strong>결제 상태:</strong>
                                        <c:choose>
                                            <c:when test="${payment.status == 'SUCCESS'}">
                                                <span class="status-badge"
                                                    style="background-color: #28a745; color: white;">결제 완료</span>
                                            </c:when>
                                            <c:when test="${payment.status == 'REFUND_REQUEST'}">
                                                <span class="status-badge"
                                                    style="background-color: #ffc107; color: #212529;">환불 요청 중</span>
                                            </c:when>
                                            <c:when test="${payment.status == 'REFUNDED'}">
                                                <span class="status-badge"
                                                    style="background-color: #17a2b8; color: white;">환불 완료</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="status-badge"
                                                    style="background-color: #6c757d; color: white;">${payment.status}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </p>
                                    <p><strong>결제 일시:</strong>
                                        <fmt:formatDate value="${formatUtil.LocalDateTimeToDate(payment.createdAt)}"
                                            pattern="yyyy년 MM월 dd일 HH:mm" />
                                    </p>
                                    <p><strong>카카오페이 TID:</strong> ${payment.tid}</p>
                                </div>
                            </div>
                        </div>

                        <!-- 환불 요청 폼 -->
                        <c:choose>
                            <c:when test="${payment.status == 'SUCCESS'}">
                                <div class="refund-form">
                                    <h4>📝 환불 요청</h4>

                                    <form method="post" action="/refund/request" id="refundForm">
                                        <input type="hidden" name="paymentId" value="${payment.id}" />

                                        <div class="form-group">
                                            <label for="refundReason" class="form-label">환불 사유 <span
                                                    class="required">*</span></label>
                                            <select class="form-select" id="refundReason" name="refundReason" required>
                                                <option value="">환불 사유를 선택해주세요</option>
                                                <option value="상품 불량">상품 불량</option>
                                                <option value="상품과 다름">상품과 다름</option>
                                                <option value="배송 지연">배송 지연</option>
                                                <option value="단순 변심">단순 변심</option>
                                                <option value="중복 주문">중복 주문</option>
                                                <option value="기타">기타</option>
                                            </select>
                                        </div>

                                        <div class="form-group">
                                            <label for="refundDetail" class="form-label">상세 사유 <span
                                                    class="required">*</span></label>
                                            <textarea class="form-control" id="refundDetail" name="refundDetail"
                                                rows="4" placeholder="환불 사유를 자세히 설명해주세요." required></textarea>
                                        </div>

                                                                    <div class="form-group">
                                <label for="bankInfo" class="form-label">환불 계좌 정보</label>
                                <div class="form-row">
                                    <select class="form-select" id="bankCode" name="bankCode">
                                        <option value="">은행 선택</option>
                                        <option value="004">국민은행</option>
                                        <option value="088">신한은행</option>
                                        <option value="020">우리은행</option>
                                        <option value="081">하나은행</option>
                                        <option value="003">기업은행</option>
                                        <option value="011">농협은행</option>
                                        <option value="007">수협은행</option>
                                        <option value="002">산업은행</option>
                                        <option value="012">지역농협</option>
                                        <option value="031">대구은행</option>
                                        <option value="032">부산은행</option>
                                        <option value="034">광주은행</option>
                                        <option value="035">제주은행</option>
                                        <option value="037">전북은행</option>
                                        <option value="039">경남은행</option>
                                        <option value="045">새마을금고</option>
                                        <option value="048">신협</option>
                                        <option value="050">상호저축은행</option>
                                        <option value="071">우체국</option>
                                        <option value="089">케이뱅크</option>
                                        <option value="090">카카오뱅크</option>
                                        <option value="092">토스뱅크</option>
                                    </select>
                                    <input type="text" class="form-control" id="accountNumber"
                                        name="accountNumber" placeholder="계좌번호 (하이픈 없이 입력)" maxlength="20">
                                </div>
                                <input type="hidden" name="refundAccount" id="refundAccount" value="">
                                <small class="form-text">환불 계좌 정보는 선택사항입니다. 미입력 시 카카오페이로 환불됩니다.</small>
                            </div>

                                        <div class="form-group">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" id="agreeTerms"
                                                    name="agreePolicy" required>
                                                <label class="form-check-label" for="agreeTerms">
                                                    환불 정책을 확인했으며, 환불 요청에 동의합니다. <span class="required">*</span>
                                                </label>
                                            </div>
                                        </div>

                                        <div class="btn-group">
                                            <a href="/mypage" class="btn btn-secondary">취소</a>
                                            <button type="submit" class="btn btn-danger" id="submitBtn">환불 요청하기</button>
                                        </div>
                                    </form>
                                </div>
                            </c:when>

                            <c:when test="${payment.status == 'REFUND_REQUEST'}">
                                <div class="alert">
                                    <h5>⏳ 환불 요청 처리 중</h5>
                                    <p>이미 환불 요청이 접수되었습니다. 관리자 검토 후 처리됩니다.</p>
                                    <p><strong>처리 예상 기간:</strong> 3-5일</p>
                                </div>
                            </c:when>

                            <c:when test="${payment.status == 'REFUNDED'}">
                                <div class="alert">
                                    <h5>✅ 환불 완료</h5>
                                    <p>이미 환불이 완료된 결제입니다.</p>
                                </div>
                            </c:when>

                            <c:otherwise>
                                <div class="alert">
                                    <h5>❌ 환불 불가</h5>
                                    <p>이 결제는 환불 요청이 불가능한 상태입니다.</p>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>

                <script>
                    document.addEventListener('DOMContentLoaded', function () {
                        const form = document.getElementById('refundForm');
                        const submitBtn = document.getElementById('submitBtn');

                        if (form) {
                            form.addEventListener('submit', function (e) {
                                e.preventDefault();

                                // 필수 필드 검증
                                const reason = document.getElementById('refundReason').value;
                                const detail = document.getElementById('refundDetail').value;
                                const agreeTerms = document.getElementById('agreeTerms').checked;

                                if (!reason) {
                                    alert('환불 사유를 선택해주세요.');
                                    return;
                                }

                                if (!detail.trim()) {
                                    alert('상세 사유를 입력해주세요.');
                                    return;
                                }

                                if (!agreeTerms) {
                                    alert('환불 정책 동의가 필요합니다.');
                                    return;
                                }

                                // 최종 확인
                                if (confirm('환불 요청을 제출하시겠습니까?\n\n제출 후에는 취소할 수 없습니다.')) {
                                    submitBtn.disabled = true;
                                    submitBtn.textContent = '처리 중...';
                                    form.submit();
                                }
                            });
                        }

                        // 환불 사유가 '기타'일 때 상세 사유 필수 입력 안내
                        const reasonSelect = document.getElementById('refundReason');
                        const detailTextarea = document.getElementById('refundDetail');

                        if (reasonSelect && detailTextarea) {
                            reasonSelect.addEventListener('change', function () {
                                if (this.value === '기타') {
                                    detailTextarea.placeholder = '기타 사유를 구체적으로 설명해주세요.';
                                } else {
                                    detailTextarea.placeholder = '환불 사유를 자세히 설명해주세요.';
                                }
                            });
                        }
                        
                        // 계좌 정보 조합
                        const bankCodeSelect = document.getElementById('bankCode');
                        const accountNumberInput = document.getElementById('accountNumber');
                        const refundAccountHidden = document.getElementById('refundAccount');
                        
                        function updateRefundAccount() {
                            const bankCode = bankCodeSelect.value;
                            const accountNumber = accountNumberInput.value;
                            
                            if (bankCode && accountNumber) {
                                refundAccountHidden.value = `${bankCode}:${accountNumber}`;
                            } else {
                                refundAccountHidden.value = '';
                            }
                        }
                        
                        if (bankCodeSelect && accountNumberInput && refundAccountHidden) {
                            bankCodeSelect.addEventListener('change', updateRefundAccount);
                            accountNumberInput.addEventListener('input', updateRefundAccount);
                        }
                    });
                </script>
            </body>

            </html>