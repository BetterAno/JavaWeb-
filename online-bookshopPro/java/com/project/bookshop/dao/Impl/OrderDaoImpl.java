package com.project.bookshop.dao.Impl;

import com.project.bookshop.dao.OrderDAO;
import com.project.bookshop.pojo.Order;
import com.project.bookshop.pojo.OrderItem;
import com.project.bookshop.pojo.Book;
import com.project.bookshop.util.JDBCUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDAO {
    @Override
    public int createOrder(Order order) {
        String orderSql = "INSERT INTO orders(order_no, user_id, total_price, create_time) VALUES (?, ?, ?, ?)";
        String itemSql = "INSERT INTO order_items(order_id, book_id, quantity) VALUES (?, ?, ?)";
        int orderId = -1;
        try (Connection conn = JDBCUtil.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, order.getOrderNo());
                ps.setInt(2, order.getUserId());
                ps.setDouble(3, order.getTotalPrice());
                ps.setTimestamp(4, order.getCreateTime());
                ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    orderId = rs.getInt(1);
                }
            }
            if (orderId > 0 && order.getItems() != null) {
                try (PreparedStatement ps = conn.prepareStatement(itemSql)) {
                    for (OrderItem item : order.getItems()) {
                        ps.setInt(1, orderId);
                        ps.setInt(2, item.getBookId());
                        ps.setInt(3, item.getQuantity());
                        ps.addBatch();
                    }
                    ps.executeBatch();
                }
            }
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderId;
    }

    @Override
    public Order getOrderById(int orderId) {
        String orderSql = "SELECT * FROM orders WHERE id = ?";
        String itemsSql = "SELECT oi.*, b.title, b.author, b.price FROM order_items oi " +
                         "JOIN books b ON oi.book_id = b.id WHERE oi.order_id = ?";
        
        try (Connection conn = JDBCUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(orderSql)) {
                ps.setInt(1, orderId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    Order order = new Order();
                    order.setId(rs.getInt("id"));
                    order.setOrderNo(rs.getString("order_no"));
                    order.setUserId(rs.getInt("user_id"));
                    order.setTotalPrice(rs.getDouble("total_price"));
                    order.setCreateTime(rs.getTimestamp("create_time"));
                    
                    List<OrderItem> items = new ArrayList<>();
                    try (PreparedStatement itemPs = conn.prepareStatement(itemsSql)) {
                        itemPs.setInt(1, orderId);
                        ResultSet itemRs = itemPs.executeQuery();
                        while (itemRs.next()) {
                            OrderItem item = new OrderItem();
                            item.setId(itemRs.getInt("id"));
                            item.setOrderId(itemRs.getInt("order_id"));
                            item.setBookId(itemRs.getInt("book_id"));
                            item.setQuantity(itemRs.getInt("quantity"));
                            
                            Book book = new Book();
                            book.setId(itemRs.getInt("book_id"));
                            book.setTitle(itemRs.getString("title"));
                            book.setAuthor(itemRs.getString("author"));
                            book.setPrice(itemRs.getDouble("price"));
                            item.setBook(book);
                            
                            items.add(item);
                        }
                    }
                    order.setItems(items);
                    return order;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
} 