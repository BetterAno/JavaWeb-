package com.project.bookshop.servlet;

import com.project.bookshop.dao.Impl.BookDaoImpl;
import com.project.bookshop.pojo.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/view")
public class ViewServlet extends HttpServlet {
    private BookDaoImpl bookDao = new BookDaoImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String bookIdStr = request.getParameter("id");
            if (bookIdStr != null && !bookIdStr.trim().isEmpty()) {
                int bookId = Integer.parseInt(bookIdStr);
                Book book = bookDao.getBookById(bookId);

                if (book != null) {
                    request.setAttribute("book", book);
                } else {
                    request.setAttribute("error", "图书不存在");
                }
            } else {
                request.setAttribute("error", "图书ID不能为空");
            }

            // 转发到图书详情页面
            request.getRequestDispatcher("/view.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "无效的图书ID");
            request.getRequestDispatcher("view.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "获取图书信息失败");
            request.getRequestDispatcher("view.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}