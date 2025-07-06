package com.project.bookshop.servlet;

import com.project.bookshop.dao.Impl.OrderDaoImpl;
import com.project.bookshop.dao.Impl.UserDaoImpl;
import com.project.bookshop.pojo.Order;
import com.project.bookshop.pojo.User;
import com.project.bookshop.util.JDBCUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    UserDaoImpl userDao = new UserDaoImpl();
    OrderDaoImpl orderDao = new OrderDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Order order = (Order) session.getAttribute("order");

        // 获取用户当前余额
        double currentBalance = userDao.getBalance(user.getId());
        String message;
        boolean reclick = true; // 用于标记是否重新点击支付按钮
        boolean success = false;

        if (currentBalance >= order.getTotalPrice()) {
            success = processPayment(user.getId(), order.getTotalPrice());
            if (success) {
                message = "购买成功! 感谢您的购买！";
                reclick = false;
                // 更新session中的余额
                session.setAttribute("balance", currentBalance - order.getTotalPrice());
            } else {
                message = "支付处理失败，请重试！";
            }
        } else {
            message = "余额不足，购买失败！";
        }

        // 重新获取订单信息用于显示
        order = orderDao.getOrderById(order.getId());
        req.setAttribute("order", order);
        req.setAttribute("message", message);
        req.setAttribute("success", success);
        req.setAttribute("reclick", reclick);
        req.getRequestDispatcher("order.jsp").forward(req, resp);
    }

    private boolean processPayment(int userId, double amount) {
        Connection connection = JDBCUtil.getConnection();
        try {
//            使用数据库事务处理余额扣减和购物车清空操作
            connection.setAutoCommit(false);

//             1. 扣减用户余额
            String Sql = "UPDATE users SET balance = balance - ? WHERE id = ? AND balance >= ?";
            try (PreparedStatement ps = connection.prepareStatement(Sql)) {
                ps.setDouble(1, amount);
                ps.setInt(2, userId);
                ps.setDouble(3, amount);
                int row = ps.executeUpdate();
//                执行更新操作失败
                if (row == 0) {
                    connection.rollback();
                    connection.close();
                    return false;
                }
                connection.commit();
                connection.setAutoCommit(true);
            }

            // 2. 清空用户购物车
            String clearCartSql = "DELETE FROM cart_items WHERE user_id = ?";
            PreparedStatement ps = connection.prepareStatement(clearCartSql);
            ps.setInt(1, userId);

            ps.executeUpdate();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
} 