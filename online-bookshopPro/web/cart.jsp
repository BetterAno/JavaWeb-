<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="./header.jsp"/>

<div class="cart-container">
    <h2>我的购物车</h2>

    <c:choose>
        <c:when test="${empty requestScope.cartItems}">
            <div class="empty-cart">
                <p>购物车是空的</p>
                <a href="index.jsp" class="btn-continue-shopping">继续购物</a>
            </div>
        </c:when>
        <c:otherwise>
            <div class="cart-items">
                <c:set var="total" value="0" />
                <c:forEach var="item" items="${requestScope.cartItems}">
                    <c:set var="itemTotal" value="${item.book.price * item.quantity}" />
                    <c:set var="total" value="${total + itemTotal}" />
                    <div class="cart-item">
                        <div class="book-image">
                            <img src="${empty item.book.coverImage ? 'https://via.placeholder.com/120x150/cccccc/666666?text=暂无图片' : item.book.coverImage}"
                                 alt="${item.book.title}">
                        </div>
                        <div class="book-info">
                            <h3>${item.book.title}</h3>
                            <p>作者：${item.book.author}</p>
                            <p>单价：¥<fmt:formatNumber value="${item.book.price}" pattern="#,##0.00"/></p>
                            <p>数量：${item.quantity}</p>
                            <p>小计：¥<fmt:formatNumber value="${itemTotal}" pattern="#,##0.00"/></p>
                        </div>
                        <div class="cart-actions">
                            <a href="cart?action=remove&id=${item.id}" class="btn-remove">删除</a>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <div class="cart-summary">
                <div class="total">
                    <h3>总计：¥<fmt:formatNumber value="${total}" pattern="#,##0.00"/></h3>
                </div>
                <div class="cart-buttons">
                    <a href="index.jsp" class="btn-continue-shopping">继续购物</a>
                    <a href="#" class="btn-checkout">结算</a>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<jsp:include page="./footer.jsp"/>