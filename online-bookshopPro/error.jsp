<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./static/css/error.css">
    <title>错误页面</title>
</head>
<body>
<div class="error-container">
    <div class="error-code">
        <% if (response.getStatus() == 404) { %>
        请稍后再试，或联系管理员。
        <% } else { %>
        请稍后再试，或联系管理员。
        <% } %>
    </div>

    <a href="index" class="back-link">返回首页</a>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>