<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>在线书店 - 首页</title>
    <link rel="stylesheet" href="static/css/style.css">
    <link rel="stylesheet" href="static/css/index-css.css">
</head>
<body>
<jsp:include page="header.jsp"/>

<div class="container">
    <%-- test 属性需要判断 boolean 类型 --%>
    <c:if test="${not empty sessionScope.message}">
        <div class="message message-success">${sessionScope.message}</div>
        <%-- 移除message 属性, 确保下次访问不会再次显示--%>
        <% session.removeAttribute("message"); %>
    </c:if>

    <c:if test="${not empty sessionScope.error}">
        <div class="message message-error">${sessionScope.error}</div>
        <% session.removeAttribute("message"); %>
    </c:if>

    <div class="main-content">
        <div class="category-list">
            <ul>
                <li>
                    <a href="index">全部</a>
                </li>
                <c:forEach var="item" items="${requestScope.categories}">
                    <li>
                        <a href="index?categoryId=${item.categoryId}">${item.categoryName}</a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <!-- 右侧内容 -->
        <div class="right-content">
            <img src="./static/img/header-center-logo.jpg" alt="LOGO" class="header-center-img"/>
            <div class="books-grid">
                <c:forEach var="book" items="${requestScope.books}">
                    <div class="book-card">
                        <img src="./static/img/${book.title}.jpg" alt="${book.title}" class="image">
                        <div class="book-info">
                            <h3 class="book-title">${book.title}</h3>
                            <p class="book-author">作者：${book.author}</p>
                            <p class="book-price">￥${book.price}</p>
                            <div class="actions">
                                <a href="view?id=${book.id}" class="btn btn-primary">查看详情</a>
                                <c:if test="${not empty sessionScope.user}">
                                    <a href="addToCart?id=${book.id}&from=index" class="btn btn-success">
                                        加入购物车
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <c:if test="${empty requestScope.books}">
                <h1>暂无书籍信息</h1>
            </c:if>

        </div>

    </div>
</div>

<jsp:include page="footer.jsp"/>

</body>
</html>
