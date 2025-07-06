package com.project.bookshop.servlet;

import com.project.bookshop.dao.Impl.BookDaoImpl;
import com.project.bookshop.pojo.Book;
import com.project.bookshop.pojo.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/view")
public class ViewServlet extends HttpServlet {
    BookDaoImpl bookDao = new BookDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String bookIdStr = request.getParameter("id");
            if (bookIdStr == null) {
                request.getRequestDispatcher("view.jsp").forward(request, response);
                return;
            }

            int bookId = Integer.parseInt(bookIdStr);
            Book book = bookDao.getBookById(bookId);

            if (book != null) {
                request.setAttribute("book", book);
            }
            request.getRequestDispatcher("view.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.getRequestDispatcher("view.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}