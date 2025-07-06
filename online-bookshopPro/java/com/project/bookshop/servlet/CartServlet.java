package com.project.bookshop.servlet;

import com.project.bookshop.dao.Impl.CartDaoImpl;
import com.project.bookshop.pojo.CartItem;
import com.project.bookshop.pojo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.io.IOException;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    CartDaoImpl cartDao = new CartDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

//        检查用户是否登录, 如未登录输入地址浏览购物车会显示登录页面
        if (user == null) {
            response.sendRedirect("login");
            return;
        }

        // 显示购物车
        List<CartItem> cartItems = cartDao.getCartItems(user.getId());
        request.setAttribute("cartItems", cartItems);

        BigDecimal totalPrice = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            BigDecimal price = new BigDecimal(String.valueOf(item.getBook().getPrice()));
            BigDecimal quantity = new BigDecimal(item.getQuantity());
            totalPrice = totalPrice.add(price.multiply(quantity));
        }
        totalPrice = totalPrice.setScale(2, RoundingMode.HALF_UP);
        double result = totalPrice.doubleValue();

        request.setAttribute("totalPrice", result);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

//        在购物车修改书本数量的操作
        try {
            int bookId = Integer.parseInt(request.getParameter("bookId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            cartDao.updateCartQuantity(user.getId(), bookId, quantity);
            session.setAttribute("message", "数量已更新");
        } catch (Exception e) {
            session.setAttribute("error", "数量更新失败");
        }
        response.sendRedirect("cart");
    }
}
