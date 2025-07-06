<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户登录 - 在线书店</title>
    <link rel="stylesheet" href="./static/css/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="form-container">
    <h2 class="form-title">用户登录</h2>

    <c:if test="${not empty requestScope.error}">
        <div class="message message-error">${requestScope.error}</div>
    </c:if>

    <form action="login" method="post">
        <div class="form-group">
            <label for="username" class="form-label">用户名</label>
            <input type="text" id="username" name="username" class="form-input" required>
        </div>

        <div class="form-group">
            <label for="password" class="form-label">密码</label>
            <input type="password" id="password" name="password" class="form-input" required>
        </div>

        <button type="submit" class="form-submit">登录</button>
    </form>

    <div style="text-align: center; margin-top: 20px;">
        <p>还没有账号？<a href="register" style="color: #74b9ff; text-decoration: none;">立即注册</a></p>
    </div>
</div>
</body>
</html> 