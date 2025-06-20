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

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private CartDaoImpl cartDao = new CartDaoImpl();
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if ("add".equals(action)) {
            int bookId = Integer.parseInt(request.getParameter("id"));
            cartDao.addToCart(user.getId(), bookId, 1);
            response.sendRedirect("cart.jsp");
        } else if ("remove".equals(action)) {
            int cartId = Integer.parseInt(request.getParameter("id"));
            cartDao.removeFromCart(cartId);
            response.sendRedirect("cart.jsp");
        } else {
            // 显示购物车
            request.setAttribute("cartItems", cartDao.getCartItems(user.getId()));
            request.getRequestDispatcher("/cart.jsp").forward(request, response);
        }
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
