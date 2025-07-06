package com.project.bookshop.servlet;

import com.project.bookshop.dao.Impl.BookDaoImpl;
import com.project.bookshop.dao.Impl.CategoryDaoImpl;
import com.project.bookshop.pojo.Book;
import com.project.bookshop.pojo.Category;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    BookDaoImpl bookDao = new BookDaoImpl();
    CategoryDaoImpl categoryDao = new CategoryDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String categoryIdStr = request.getParameter("categoryId");
            List<Book> books;
            if (categoryIdStr != null && !categoryIdStr.isEmpty()) {
                int categoryId = Integer.parseInt(categoryIdStr);
                books = bookDao.getBooksByCategory(categoryId);
            } else {
                books = bookDao.getAllBooks();
            }
            request.setAttribute("books", books);
            // 获取所有种类
            List<Category> categories = categoryDao.getAllCategories();
            request.setAttribute("categories", categories);
            // 转发到首页
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "获取图书信息失败");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
