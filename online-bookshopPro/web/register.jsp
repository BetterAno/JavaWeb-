<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="./header.jsp"/>

<div class="register-container">
    <div class="register-form">
        <h2>用户注册</h2>

        <c:if test="${not empty requestScope.error}">
            <div class="error-message">
                    ${requestScope.error}
            </div>
        </c:if>

        <form action="register" method="post">
            <div class="form-group">
                <label for="username">用户名：</label>
                <input type="text" id="username" name="username" required>
            </div>

            <div class="form-group">
                <label for="password">密码：</label>
                <input type="password" id="password" name="password" required>
            </div>

            <div class="form-group">
                <label for="email">邮箱：</label>
                <input type="email" id="email" name="email" required>
            </div>

            <div class="form-group">
                <button type="submit" class="btn-register">注册</button>
            </div>
        </form>

        <div class="login-link">
            已有账号？<a href="login.jsp">立即登录</a>
        </div>
    </div>
</div>

<jsp:include page="./footer.jsp"/>