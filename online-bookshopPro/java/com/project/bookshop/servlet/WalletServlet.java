package com.project.bookshop.servlet;

import com.project.bookshop.dao.Impl.UserDaoImpl;
import com.project.bookshop.pojo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/wallet")
public class WalletServlet extends HttpServlet {
    UserDaoImpl userDao = new UserDaoImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            double balance = userDao.getBalance(user.getId());
            req.setAttribute("balance", balance);
            req.getRequestDispatcher("wallet.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("login.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user != null) {
            double amount = Double.parseDouble(req.getParameter("amount"));
            userDao.chargeBalance(user.getId(), amount);
            resp.sendRedirect("wallet");
        } else {
            resp.sendRedirect("login.jsp");
        }
    }
} 