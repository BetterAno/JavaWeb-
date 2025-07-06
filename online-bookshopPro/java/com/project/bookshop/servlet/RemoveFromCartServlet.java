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

@WebServlet("/removeFromCart")
public class RemoveFromCartServlet extends HttpServlet {
    CartDaoImpl cartDao = new CartDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            cartDao.removeFromCartByUserAndBook(user.getId(), bookId);

            // 设置成功消息
            session.setAttribute("message", "商品已从购物车中删除");
            response.sendRedirect("cart");
        } catch (NumberFormatException e) {
            session.setAttribute("error", "删除失败，请重试");
            response.sendRedirect("cart");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
} 