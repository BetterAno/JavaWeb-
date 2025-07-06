<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header">
    <div class="header-container">
        <div class="header-left">
            <img src="./static/img/header-left-logo.png" alt="Logo" class="header-left-logo">
            <img src="./static/img/header-center-logo.jpg" alt="Center Logo" class="header-center-logo">
        </div>
        <div class="header-right">
            <c:if test="${not empty sessionScope.user}">
                <span style="color: white; font-weight: 500;">欢迎，${sessionScope.user.username}</span>
                <a href="wallet" class="btn btn-wallet">
                    我的钱包
                </a>
                <a href="cart" class="btn btn-cart">
                    我的购物车
                </a>
                <a href="logout" class="btn btn-secondary">退出登录</a>
            </c:if>
            <c:if test="${empty sessionScope.user}">
                <a href="login" class="btn btn-primary">登录</a>
                <a href="register" class="btn btn-secondary">注册</a>
            </c:if>
        </div>
    </div>
</header> 