package com.project.bookshop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private int id;
    private int orderId;
    private int bookId;
    private int quantity;
    private Book book;
} 