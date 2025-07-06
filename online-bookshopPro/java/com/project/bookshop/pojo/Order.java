package com.project.bookshop.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private int id;
    private String orderNo;
    private int userId;
    private double totalPrice;
    private Timestamp createTime;
    private List<OrderItem> items;
} 