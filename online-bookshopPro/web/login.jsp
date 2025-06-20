<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="./header.jsp"/>

<div class="login-container">
    <div class="login-form">
        <h2>用户登录</h2>

        <c:if test="${not empty requestScope.error}">
            <div class="error-message">
                    ${requestScope.error}
            </div>
        </c:if>

        <c:if test="${not empty requestScope.success}">
            <div class="success-message">
                    ${requestScope.success}
            </div>
        </c:if>

        <form action="login" method="post">
            <div class="form-group">
                <label for="username">用户名：</label>
                <input type="text" id="username" name="username" required>
            </div>
            <div class="form-group">
                <label for="password">密码：</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <button type="submit" class="btn-login">登录</button>
            </div>
        </form>
        <div class="register-link">
            <p>还没有账号？<a href="register.jsp">立即注册</a></p>
        </div>
    </div>
</div>

<jsp:include page="./footer.jsp"/>