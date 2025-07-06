-- 创建数据库
CREATE DATABASE IF NOT EXISTS bkpro;

-- 使用数据库
USE bkpro;

-- 用户表
CREATE TABLE users
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(100) NOT NULL,
    email    VARCHAR(100),
    balance  DOUBLE DEFAULT 0
);

-- 种类表
CREATE TABLE categories
(
    category_id   INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE
);

-- 图书表
CREATE TABLE books
(
    id          INT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(200)   NOT NULL,
    author      VARCHAR(100)   NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    category_id INT,
    description TEXT,
    FOREIGN KEY (category_id) REFERENCES categories (category_id)
);

-- 购物车表
CREATE TABLE cart_items
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    user_id  INT NOT NULL,
    book_id  INT NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    UNIQUE KEY unique_user_book (user_id, book_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE
);


-- 插入图书种类数据
INSERT INTO categories (category_name)
VALUES ('历史学类'),
       ('管理学类'),
       ('金融学类'),
       ('计算机传'),
       ('人物自传');

# 订单表
CREATE TABLE orders
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    order_no    VARCHAR(32)    NOT NULL UNIQUE,
    user_id     INT            NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    create_time DATETIME       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);

-- 订单项表
CREATE TABLE order_items
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    order_id INT NOT NULL,
    book_id  INT NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (book_id) REFERENCES books (id)
);
