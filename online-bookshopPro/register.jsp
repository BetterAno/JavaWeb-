<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>用户注册 - 在线书店</title>
    <link rel="stylesheet" href="./static/css/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="form-container">
    <h2 class="form-title">用户注册</h2>

    <c:if test="${not empty requestScope.error}">
        <div class="message message-error">${requestScope.error}</div>
    </c:if>

    <form action="register" method="post">
        <div class="form-group">
            <label for="username" class="form-label">用户名</label>
            <input type="text" id="username" name="username" class="form-input" required>
        </div>

        <div class="form-group">
            <label for="password" class="form-label">密码</label>
            <input type="password" id="password" name="password" class="form-input" required>
        </div>

        <div class="form-group">
            <label for="confirmPassword" class="form-label">确认密码</label>
            <input type="password" id="confirmPassword" name="confirmPassword" class="form-input" required>
        </div>

        <div class="form-group">
            <label for="email" class="form-label">邮箱</label>
            <input type="email" id="email" name="email" class="form-input" required>
        </div>

        <button type="submit" class="form-submit">注册</button>
    </form>

    <div style="text-align: center; margin-top: 20px;">
        <p>已有账号？<a href="login" style="color: #74b9ff; text-decoration: none;">立即登录</a></p>
    </div>
</div>
</body>
</html> 