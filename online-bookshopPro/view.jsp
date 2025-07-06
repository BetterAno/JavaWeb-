<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${requestScope.book.title} - 在线书店</title>
    <link rel="stylesheet" href="./static/css/style.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <c:if test="${not empty sessionScope.message}">
        <div class="message message-success">${sessionScope.message}</div>
        <% session.removeAttribute("message"); %>
    </c:if>

    <c:if test="${not empty sessionScope.error}">
        <div class="message message-error">${sessionScope.error}</div>
        <% session.removeAttribute("message"); %>
    </c:if>

    <c:if test="${not empty requestScope.book}">
        <div class="book-detail">
            <div class="book-detail-content">
                <img src="./static/img/${requestScope.book.title}.jpg" alt="${requestScope.book.title}"
                     class="book-detail-image">
                <div class="book-detail-info">
                    <h1>${requestScope.book.title}</h1>
                    <p class="book-detail-author">作者：${requestScope.book.author}</p>
                    <p class="book-detail-price">￥${requestScope.book.price}</p>
                    <p style="margin: 20px 0; color: #444; font-size: 16px; line-height: 1.8;">
                        介绍：${requestScope.book.description}</p>

                    <div style="margin-top: 30px;">
                        <c:if test="${not empty sessionScope.user}">
                            <form action="addToCart" method="get" style="display:inline-block; margin-right:15px;">
                                <input type="hidden" name="id" value="${requestScope.book.id}"/>
                                <input type="hidden" name="from" value="detail"/>
                                <label for="quantity">数量：</label>
                                <select name="quantity" id="quantity" style="padding:4px 6px; border-radius:4px;">
                                    <c:forEach var="i" begin="1" end="10">
                                        <option value="${i}">${i}</option>
                                    </c:forEach>
                                </select>
                                <button type="submit" class="btn btn-success">
                                    加入购物车
                                </button>
                            </form>
                        </c:if>
                        <a href="index" class="btn btn-secondary">返回首页</a>
                    </div>
                </div>
            </div>
        </div>
    </c:if>

    <c:if test="${empty requestScope.book}">
        <div style="text-align: center; margin: 50px 0; color: #636e72;">
            <h2>书籍不存在</h2>
            <p>请检查书籍ID是否正确</p>
            <a href="index" class="btn btn-primary" style="margin-top: 20px;">返回首页</a>
        </div>
    </c:if>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html> 