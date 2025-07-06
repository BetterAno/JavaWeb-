<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>订单详情</title>
    <link rel="stylesheet" href="./static/css/order.css">
</head>
<body>
<div class="container">
    <div class="header">
        <h2>订单详情</h2>
    </div>

    <div class="books-section">
        <div class="books-title">购买商品</div>
        <c:forEach var="item" items="${requestScope.order.items}">
            <div class="book-item">
                <div class="book-info">
                    <div class="book-title">${item.book.title}</div>
                    <div class="book-author">作者：${item.book.author}</div>
                </div>
                <div class="book-quantity">
                    单价：${item.book.price}&nbsp;&nbsp;
                </div>
                <div class="book-quantity">
                    数量：${item.quantity}
                </div>
            </div>
        </c:forEach>
    </div>

    <div class="order-info">
        <div class="info-row">
            <span class="label">订单号：</span>
            <span class="value">${requestScope.order.orderNo}</span>
        </div>
        <div class="info-row">
            <span class="label">下单时间：</span>
            <span class="value">
        <fmt:formatDate value="${requestScope.order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </span>
        </div>
        <div class="info-row">
            <span class="label">订单总价：</span>
            <span class="total">¥${requestScope.order.totalPrice}</span>
        </div>
    </div>

    <c:if test="${not empty requestScope.message}">
        <div>
            <c:if test="${requestScope.success}">
                <div class="message success-message">
                        ${requestScope.message}
                </div>
            </c:if>
            <c:if test="${not requestScope.success}">
                <div class="message error-message">
                        ${requestScope.message}
                </div>
            </c:if>
        </div>
    </c:if>

    <div class="back-link">
        <a href="cart">返回购物车</a>
        <c:if test="${requestScope.reclick == true}">
            <a href="payment" class="payment-btn">确认购买</a>
        </c:if>

    </div>

</div>
</body>
</html> 