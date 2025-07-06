package com.project.bookshop.dao;

import com.project.bookshop.pojo.CartItem;

import java.util.List;

public interface CartDAO {
    boolean addToCart(int userId, int bookId, int quantity);

    List<CartItem> getCartItems(int userId);

    boolean removeFromCart(int cartId);

    boolean removeFromCartByUserAndBook(int userId, int bookId);

    boolean updateCartQuantity(int userId, int bookId, int quantity);
}
