<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的购物车 - 在线书店</title>
    <link rel="stylesheet" href="./static/css/style.css">
    <link rel="stylesheet" href="./static/css/cart.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <h1>我的购物车</h1>

    <c:if test="${not empty sessionScope.message}">
        <div class="message message-success">${sessionScope.message}</div>
        <% session.removeAttribute("message"); %>
    </c:if>

    <c:if test="${not empty sessionScope.error}">
        <div class="message message-error">${sessionScope.error}</div>
        <% session.removeAttribute("message"); %>
    </c:if>

    <c:if test="${not empty requestScope.cartItems && requestScope.cartItems.size() > 0}">
        <div class="cart-container">
            <c:forEach var="item" items="${requestScope.cartItems}">
                <div class="cart-item">
                    <img src="./static/img/${item.book.title}.jpg" alt="${item.book.title}" class="cart-item-image">
                    <div class="cart-item-info">
                        <h3>${item.book.title}</h3>
                        <p class="cart-item-author">作者：${item.book.author}</p>
                        <form action="cart" method="post"
                              style="margin-top:8px;display:flex;align-items:center;gap:8px;">
                            <input type="hidden" name="bookId" value="${item.book.id}"/>
                            <label>数量：</label>
                            <input type="number" name="quantity" value="${item.quantity}" min="1" max="99"
                                   style="width:50px;padding:2px 6px;border-radius:4px;"/>
                            <button type="submit" class="btn btn-secondary" style="font-size:12px;padding:6px 12px;">
                                修改
                            </button>
                        </form>
                    </div>
                    <div class="cart-item-price">￥${item.book.price} x ${item.quantity} =
                        <span>￥<fmt:formatNumber value="${item.book.price * item.quantity}" type="number"
                                                 minFractionDigits="2" maxFractionDigits="2"/></span>
                    </div>
                    <div>
                        <a href="removeFromCart?bookId=${item.book.id}" class="btn btn-primary"
                           style="font-size: 12px; padding: 8px 15px;">删除</a>
                    </div>
                </div>
            </c:forEach>

            <div class="cart-total">
                <h2>总计：${requestScope.totalPrice}</h2>
                <a href="index" class="btn btn-secondary"
                   style="margin-top: 15px; padding: 15px 30px; font-size: 16px; margin-right: 15px;">继续购物</a>
                <a href="checkout" class="btn btn-success"
                   style="margin-top: 15px; padding: 15px 30px; font-size: 16px;">结算</a>
            </div>
        </div>
    </c:if>

    <c:if test="${empty requestScope.cartItems || requestScope.cartItems.size() == 0}">
        <div style="text-align: center; margin: 50px 0; color: #636e72;">
            <h2>购物车为空</h2>
            <p>快去添加一些书籍吧！</p>
            <a href="index" class="btn btn-primary" style="margin-top: 20px;">去购物</a>
        </div>
    </c:if>
</div>

<jsp:include page="footer.jsp"/>
</body>
</html> 