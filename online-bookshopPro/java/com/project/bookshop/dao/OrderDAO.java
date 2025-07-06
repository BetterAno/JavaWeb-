package com.project.bookshop.dao;

import com.project.bookshop.pojo.Order;

public interface OrderDAO {
    int createOrder(Order order);
    Order getOrderById(int orderId);
} 