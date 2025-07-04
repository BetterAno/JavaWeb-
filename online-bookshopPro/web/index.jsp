<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="./header.jsp"/>

<div class="book-list">
    <h2>欢迎来到在线书城</h2>

    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <div class="welcome-message">
                <p>请先 <a href="login.jsp">登录</a> 以访问购物车和购买图书。</p>
            </div>
        </c:when>
        <c:otherwise>
            <div class="welcome-message">
                <p>您好，${sessionScope.user.username}！欢迎浏览我们的图书收藏。</p>
            </div>
        </c:otherwise>
    </c:choose>

    <c:if test="${param.message eq 'added'}">
        <div class="success-message">图书已成功添加到购物车！</div>
    </c:if>

    <c:if test="${param.error eq 'invalid'}">
        <div class="error-message">操作失败，请重试。</div>
    </c:if>

    <div class="books">
        <c:choose>
            <c:when test="${not empty requestScope.books}">
                <c:forEach var="book" items="${requestScope.books}">
                    <div class="book-item">
                        <img src="${empty book.coverImage ? 'https://via.placeholder.com/200x250/cccccc/666666?text=暂无图片' : book.coverImage}"
                             alt="${book.title}">
                        <h3>${book.title}</h3>
                        <p>作者：${book.author}</p>
                        <p class="price">¥<fmt:formatNumber value="${book.price}" pattern="#,##0.00"/></p>
                        <div class="book-actions">
                            <a href="view?id=${book.id}" class="btn-view">查看详情</a>
                            <c:choose>
                                <c:when test="${not empty sessionScope.user}">
                                    <a href="addToCart?id=${book.id}" class="btn-add-cart">加入购物车</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="login.jsp" class="btn-add-cart">登录后购买</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="no-books">
                    <p>暂无图书信息</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="./footer.jsp"/>