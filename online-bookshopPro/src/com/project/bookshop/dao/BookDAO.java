package com.project.bookshop.dao;


import com.project.bookshop.pojo.Book;

import java.util.List;

public interface BookDAO {
    List<Book> getAllBooks();

    Book getBookById(int id);
}
