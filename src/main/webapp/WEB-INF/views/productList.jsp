<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 목록 - 온라인 쇼핑몰</title>
    <link rel="stylesheet" href="/css/style.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Noto Sans KR', Arial, sans-serif;
            background-color: #f8f9fa;
            color: #333;
            line-height: 1.6;
        }

        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }

        .header {
            text-align: center;
            margin-bottom: 40px;
            padding: 20px 0;
            border-bottom: 2px solid #e9ecef;
        }

        .header h1 {
            color: #2c3e50;
            font-size: 2.5rem;
            margin-bottom: 10px;
        }

        .header p {
            color: #6c757d;
            font-size: 1.1rem;
        }

        .controls {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding: 15px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .select-all {
            display: flex;
            align-items: center;
            gap: 10px;
            font-weight: 500;
        }

        .selected-count {
            color: #007bff;
            font-weight: bold;
        }

        .product-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 25px;
            margin-bottom: 40px;
        }

        .product-card {
            background: white;
            border-radius: 15px;
            overflow: hidden;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            transition: all 0.3s ease;
            position: relative;
        }

        .product-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
        }

        .product-image {
            position: relative;
            overflow: hidden;
            height: 200px;
        }

        .product-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.3s ease;
        }

        .product-card:hover .product-image img {
            transform: scale(1.05);
        }

        .product-info {
            padding: 20px;
        }

        .product-name {
            font-size: 1.2rem;
            font-weight: bold;
            color: #2c3e50;
            margin-bottom: 10px;
            line-height: 1.4;
        }

        .product-price {
            font-size: 1.4rem;
            font-weight: bold;
            color: #e74c3c;
            margin-bottom: 20px;
        }

        .product-controls {
            display: flex;
            align-items: center;
            justify-content: space-between;
            gap: 15px;
        }

        .quantity-control {
            display: flex;
            align-items: center;
            gap: 8px;
        }

        .quantity-label {
            font-weight: 500;
            color: #495057;
        }

        .quantity-input {
            width: 60px;
            padding: 8px;
            border: 2px solid #e9ecef;
            border-radius: 5px;
            text-align: center;
            font-weight: bold;
        }

        .quantity-input:focus {
            outline: none;
            border-color: #007bff;
        }

        .product-checkbox {
            position: relative;
        }

        .product-checkbox input[type="checkbox"] {
            width: 20px;
            height: 20px;
            cursor: pointer;
        }

        .product-checkbox label {
            margin-left: 8px;
            cursor: pointer;
            font-weight: 500;
        }

        .checkout-section {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            text-align: center;
        }

        .total-info {
            margin-bottom: 25px;
            padding: 20px;
            background: #f8f9fa;
            border-radius: 10px;
        }

        .total-price {
            font-size: 1.5rem;
            font-weight: bold;
            color: #e74c3c;
            margin-bottom: 10px;
        }

        .selected-items {
            color: #6c757d;
        }

        .button-group {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
        }

        .btn {
            padding: 15px 30px;
            border: none;
            border-radius: 8px;
            font-size: 1.1rem;
            font-weight: bold;
            cursor: pointer;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
        }

        .btn-primary {
            background: linear-gradient(135deg, #007bff, #0056b3);
            color: white;
        }

        .btn-primary:hover {
            background: linear-gradient(135deg, #0056b3, #004085);
            transform: translateY(-2px);
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
        }

        .btn-secondary:hover {
            background: #545b62;
            transform: translateY(-2px);
        }

        .btn:disabled {
            background: #e9ecef;
            color: #6c757d;
            cursor: not-allowed;
            transform: none;
        }

        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #6c757d;
        }

        .empty-state i {
            font-size: 4rem;
            margin-bottom: 20px;
        }

        @media (max-width: 768px) {
            .container {
                padding: 15px;
            }

            .product-grid {
                grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
                gap: 20px;
            }

            .controls {
                flex-direction: column;
                gap: 15px;
                align-items: stretch;
            }

            .button-group {
                flex-direction: column;
            }
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>상품 목록</h1>
        <p>원하는 상품을 선택하고 장바구니에 담아보세요</p>
        <div style="background: #fff; padding: 10px 20px; border-bottom: 1px solid #ddd; text-align: right;">
            <a href="/inquiry/list" style="text-decoration: none; font-weight: bold; color: #007bff;">
                📮 문의사항
            </a>
        </div>
    </div>

    <c:choose>
        <c:when test="${empty products}">
            <div class="empty-state">
                <div style="font-size: 4rem; margin-bottom: 20px;">📦</div>
                <h3>등록된 상품이 없습니다</h3>
                <p>곧 새로운 상품들을 만나보실 수 있습니다!</p>
            </div>
        </c:when>
        <c:otherwise>
            <form id="checkoutForm" action="/cart/checkout" method="post">
                <div class="controls">
                    <div class="select-all">
                        <input type="checkbox" id="selectAll">
                        <label for="selectAll">전체 선택</label>
                    </div>
                    <div class="selected-count">
                        선택된 상품: <span id="selectedCount">0</span>개
                    </div>
                </div>

                <div class="product-grid">
                    <c:forEach var="product" items="${products}" varStatus="status">
                        <div class="product-card" data-product-id="${product.id}">
                            <div class="product-image">
                                <img src="${product.thumbnail}"
                                     alt="${product.name}"
                                     onerror="this.src='/images/no-image.png'">
                            </div>

                            <div class="product-info">
                                <div class="product-name">${product.name}</div>
                                <div class="product-price">
                                    <fmt:formatNumber value="${product.price}" type="number" groupingUsed="true"/>원
                                </div>

                                <div class="product-controls">
                                    <div class="quantity-control">
                                        <span class="quantity-label">수량:</span>
                                        <input type="number"
                                               class="quantity-input"
                                               name="quantities[${product.id}]"
                                               value="1"
                                               min="1"
                                               max="99"
                                               data-price="${product.price}">
                                    </div>

                                    <div class="product-checkbox">
                                        <input type="checkbox"
                                               id="product_${product.id}"
                                               name="selectedProducts"
                                               value="${product.id}"
                                               class="product-select">
                                        <label for="product_${product.id}">선택</label>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>

                <div class="checkout-section">
                    <div class="total-info">
                        <div class="total-price">
                            총 결제금액: <span id="totalPrice">0</span>원
                        </div>
                        <div class="selected-items">
                            <span id="selectedItemsDetail">상품을 선택해주세요</span>
                        </div>
                    </div>

                    <div class="button-group">
                        <button type="submit" class="btn btn-primary" id="checkoutBtn" disabled>
                            선택한 상품 결제하기
                        </button>
                    </div>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        const selectAllCheckbox = document.getElementById('selectAll');
        const productCheckboxes = document.querySelectorAll('.product-select');
        const quantityInputs = document.querySelectorAll('.quantity-input');
        const checkoutBtn = document.getElementById('checkoutBtn');
        const selectedCountSpan = document.getElementById('selectedCount');
        const totalPriceSpan = document.getElementById('totalPrice');
        const selectedItemsDetail = document.getElementById('selectedItemsDetail');

        // 전체 선택/해제
        selectAllCheckbox.addEventListener('change', function() {
            productCheckboxes.forEach(checkbox => {
                checkbox.checked = this.checked;
            });
            updateTotalAndCount();
        });

        // 개별 상품 선택
        productCheckboxes.forEach(checkbox => {
            checkbox.addEventListener('change', function() {
                updateSelectAllState();
                updateTotalAndCount();
            });
        });

        // 수량 변경
        quantityInputs.forEach(input => {
            input.addEventListener('change', function() {
                if (this.value < 1) this.value = 1;
                if (this.value > 99) this.value = 99;
                updateTotalAndCount();
            });
        });

        function updateSelectAllState() {
            const checkedCount = document.querySelectorAll('.product-select:checked').length;
            const totalCount = productCheckboxes.length;

            selectAllCheckbox.checked = checkedCount === totalCount;
            selectAllCheckbox.indeterminate = checkedCount > 0 && checkedCount < totalCount;
        }

        function updateTotalAndCount() {
            let totalPrice = 0;
            let selectedCount = 0;
            const selectedItems = [];

            productCheckboxes.forEach(checkbox => {
                if (checkbox.checked) {
                    selectedCount++;
                    const productCard = checkbox.closest('.product-card');
                    const quantityInput = productCard.querySelector('.quantity-input');
                    const price = parseInt(quantityInput.dataset.price);
                    const quantity = parseInt(quantityInput.value);
                    const productName = productCard.querySelector('.product-name').textContent;

                    totalPrice += price * quantity;
                    selectedItems.push(`${productName} x${quantity}`);
                }
            });

            selectedCountSpan.textContent = selectedCount;
            totalPriceSpan.textContent = totalPrice.toLocaleString();
            checkoutBtn.disabled = selectedCount === 0;

            if (selectedCount === 0) {
                selectedItemsDetail.textContent = '상품을 선택해주세요';
            } else {
                selectedItemsDetail.textContent = selectedItems.slice(0, 2).join(', ') +
                    (selectedItems.length > 2 ? ` 외 ${selectedItems.length - 2}개` : '');
            }
        }

        // 폼 제출 시 검증
        document.getElementById('checkoutForm').addEventListener('submit', function(e) {
            const checkedProducts = document.querySelectorAll('.product-select:checked');
            if (checkedProducts.length === 0) {
                e.preventDefault();
                alert('결제할 상품을 선택해주세요.');
                return;
            }
        });

        // 초기 상태 업데이트
        updateTotalAndCount();
    });
</script>
</body>
</html>