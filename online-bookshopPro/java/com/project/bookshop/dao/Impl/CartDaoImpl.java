package com.project.bookshop.dao.Impl;

import com.project.bookshop.dao.CartDAO;
import com.project.bookshop.pojo.Book;
import com.project.bookshop.pojo.CartItem;
import com.project.bookshop.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDaoImpl implements CartDAO {
    public boolean addToCart(int userId, int bookId, int quantity) {
        // 重复添加某一本书籍时会更新现有数据而且数据会进行累加
        String sql = "INSERT INTO cart_items(user_id, book_id, quantity) VALUES(?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE quantity = quantity + ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);
            preparedStatement.setInt(3, quantity);
            preparedStatement.setInt(4, quantity);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CartItem> getCartItems(int userId) {
        List<CartItem> cartItems = new ArrayList<>();
        String sql = "SELECT c.*, b.title, b.author, b.price " +
                "FROM cart_items c JOIN books b ON c.book_id = b.id " +
                "WHERE c.user_id = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                CartItem item = new CartItem();
                item.setId(resultSet.getInt("id"));
                item.setUserId(resultSet.getInt("user_id"));
                item.setQuantity(resultSet.getInt("quantity"));

                Book book = new Book();
                book.setId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setPrice(resultSet.getDouble("price"));

                item.setBook(book);
                cartItems.add(item);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cartItems;
    }

    public boolean removeFromCart(int cartId) {
        String sql = "DELETE FROM cart_items WHERE id = ?";

        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, cartId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCartQuantity(int userId, int bookId, int quantity) {
        String sql = "UPDATE cart_items SET quantity = ? WHERE user_id = ? AND book_id = ?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, quantity);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, bookId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFromCartByUserAndBook(int userId, int bookId) {
        String sql = "DELETE FROM cart_items WHERE user_id = ? AND book_id = ?";
        try (Connection connection = JDBCUtil.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
