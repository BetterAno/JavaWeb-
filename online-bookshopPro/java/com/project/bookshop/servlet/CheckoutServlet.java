package com.project.bookshop.servlet;

import com.project.bookshop.dao.Impl.CartDaoImpl;
import com.project.bookshop.dao.Impl.OrderDaoImpl;
import com.project.bookshop.pojo.CartItem;
import com.project.bookshop.pojo.Order;
import com.project.bookshop.pojo.OrderItem;
import com.project.bookshop.pojo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    CartDaoImpl cartDao = new CartDaoImpl();
    OrderDaoImpl orderDao = new OrderDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<CartItem> cartItems = cartDao.getCartItems(user.getId());


        BigDecimal total = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem item : cartItems) {
            BigDecimal price = BigDecimal.valueOf(item.getBook().getPrice()).multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(price);
            orderItems.add(new OrderItem(0, 0, item.getBook().getId(), item.getQuantity(), item.getBook()));
        }

        BigDecimal totalPrice = total.setScale(2, RoundingMode.HALF_UP);
        String orderNo = generateOrderNo();
        Timestamp timeStamp = new Timestamp(System.currentTimeMillis() / 1000 * 1000);

        Order order = new Order(0, orderNo, user.getId(), totalPrice.doubleValue(), timeStamp, orderItems);

        int orderId = orderDao.createOrder(order);
        order.setId(orderId);

        // 将订单保存到session中，不立即清空购物车
        session.setAttribute("order", order);

        request.setAttribute("order", order);

        request.setAttribute("reclick", true); // 设置标志, 用于防止重复点击购买

        request.getRequestDispatcher("order.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    private String generateOrderNo() {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyyMMddHHmmss");
        String time = sdf.format(new java.util.Date());
        int random = (int) (Math.random() * 9000) + 1000;
        return time + random;
    }
} 