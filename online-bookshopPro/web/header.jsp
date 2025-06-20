<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>在线书城</title>
    <link rel="stylesheet" href="WEB-INF/css/style.css">
</head>
<body>
<header>
    <div class="header-container">
        <div class="logo">
            <h1>在线书城</h1>
        </div>
        <nav class="main-nav">
            <a href="index">首页</a>
            <a href="list">图书列表</a>
            <c:if test="${not empty sessionScope.user}">
                <a href="cart">购物车</a>
            </c:if>
        </nav>
        <div class="user-section">
            <c:choose>
                <c:when test="${not empty sessionScope.user}">
                    <span class="welcome">欢迎，${sessionScope.user.username}</span>
                    <a href="logout" class="btn-logout">退出</a>
                </c:when>
                <c:otherwise>
                    <a href="login.jsp" class="btn-login">登录</a>
                    <a href="register.jsp" class="btn-register">注册</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>