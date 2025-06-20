<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="./header.jsp"/>

<div class="book-detail-container">
    <c:choose>
        <c:when test="${not empty requestScope.book}">
            <div class="book-detail">
                <div class="book-image">
                    <img src="${empty requestScope.book.coverImage ? 'https://via.placeholder.com/300x400/cccccc/666666?text=暂无图片' : requestScope.book.coverImage}"
                         alt="${requestScope.book.title}">
                </div>
                <div class="book-info">
                    <h1>${requestScope.book.title}</h1>
                    <p class="author">作者：${requestScope.book.author}</p>
                    <p class="price">价格：¥<fmt:formatNumber value="${requestScope.book.price}" pattern="#,##0.00"/></p>
                    <c:if test="${not empty requestScope.book.description}">
                        <div class="description">
                            <h3>图书简介：</h3>
                            <p>${requestScope.book.description}</p>
                        </div>
                    </c:if>
                    <div class="book-actions">
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                <a href="addToCart?id=${requestScope.book.id}" class="btn-add-cart">加入购物车</a>
                            </c:when>
                            <c:otherwise>
                                <a href="login.jsp" class="btn-add-cart">登录后购买</a>
                            </c:otherwise>
                        </c:choose>
                        <a href="list" class="btn-back">返回列表</a>
                    </div>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="error-message">
                <p>图书信息不存在或已被删除</p>
                <a href="list" class="btn-back">返回图书列表</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="./footer.jsp"/>