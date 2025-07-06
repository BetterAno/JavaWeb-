package com.project.bookshop.dao;

import com.project.bookshop.pojo.User;

public interface UserDAO {
    boolean register(User user);

    User login(String username, String password);

    double getBalance(int userId);

    boolean chargeBalance(int userId, double amount);

}
