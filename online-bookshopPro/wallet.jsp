<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>我的钱包</title>
    <link rel="stylesheet" href="./static/css/style.css">
    <link rel="stylesheet" href="./static/css/wallet.css">
</head>
<body>
<div class="wallet-container">
    <div class="wallet-header">
        <h2>${sessionScope.user.username}的钱包</h2>
    </div>

    <div class="balance-display">
        <div class="balance-label">当前余额</div>
        <div class="balance-amount">¥<fmt:formatNumber value="${requestScope.balance}" pattern="#0.00"/></div>
    </div>

    <div class="recharge-section">
        <div class="recharge-title">选择充值金额</div>

        <div class="recharge-buttons">
            <form method="post" action="wallet" style="display: inline;">
                <input type="hidden" name="amount" value="1">
                <button type="submit" class="recharge-btn">¥1</button>
            </form>
            <form method="post" action="wallet" style="display: inline;">
                <input type="hidden" name="amount" value="5">
                <button type="submit" class="recharge-btn">¥5</button>
            </form>
            <form method="post" action="wallet" style="display: inline;">
                <input type="hidden" name="amount" value="20">
                <button type="submit" class="recharge-btn">¥20</button>
            </form>
            <form method="post" action="wallet" style="display: inline;">
                <input type="hidden" name="amount" value="50">
                <button type="submit" class="recharge-btn">¥50</button>
            </form>
            <form method="post" action="wallet" style="display: inline;">
                <input type="hidden" name="amount" value="100">
                <button type="submit" class="recharge-btn">¥100</button>
            </form>
            <form method="post" action="wallet" style="display: inline;">
                <input type="hidden" name="amount" value="500">
                <button type="submit" class="recharge-btn">¥500</button>
            </form>
        </div>

        <div class="custom-amount">
            <form method="post" action="wallet" style="display: inline;">
                <input type="number" name="amount" placeholder="自定义金额" min="0" step="1" required>
                <button type="submit">充值</button>
            </form>
        </div>
    </div>

    <div class="back-link">
        <a href="cart" class="btn btn-primary">返回购物车</a>
        <a href="index" class="btn btn-secondary">返回首页</a>
    </div>
</div>
</body>
</html> 