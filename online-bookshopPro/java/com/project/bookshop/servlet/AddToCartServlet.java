package com.project.bookshop.servlet;

import com.project.bookshop.dao.Impl.CartDaoImpl;
import com.project.bookshop.pojo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/addToCart")
public class AddToCartServlet extends HttpServlet {
    CartDaoImpl cartDao = new CartDaoImpl();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            int bookId = Integer.parseInt(request.getParameter("id"));
            int quantity = 1;
            String quantityStr = request.getParameter("quantity");
            if (quantityStr != null) {
                try {
                    quantity = Integer.parseInt(quantityStr);
                } catch (NumberFormatException ignored) {
                    ignored.printStackTrace();
                }
            }
            boolean success = cartDao.addToCart(user.getId(), bookId, quantity);

            if (success) {
                // 设置成功消息
                session.setAttribute("message", "商品已成功添加到购物车！");
            } else {
                // 设置失败消息
                session.setAttribute("error", "添加到购物车失败，请重试！");
            }

            // 根据来源页面决定重定向
            String from = request.getParameter("from");
            if ("detail".equals(from)) {
                // 从详情页添加，重定向回详情页
                response.sendRedirect("view?id=" + bookId);
            } else {
                // 从首页添加，重定向回首页
                response.sendRedirect("index");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("error", "添加失败，请重试");
            response.sendRedirect("index");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
} 