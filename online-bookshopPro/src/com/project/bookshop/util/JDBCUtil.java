package com.project.bookshop.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCUtil {
    // 创建连接池引用, 因为要提供给当前项目的全局使用, 所以创建为静态的
    private static DataSource dataSource;
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    // 在项目启动时, 即创建连接池对象, 赋值给 dataSource
    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream("db.properties");
            properties.load(inputStream);

            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // 对外提供在连接池中获取连接的方法
    public static Connection getConnection() {
        try {
            // 在 ThreadLocal 中获取 Connection
            Connection connection = threadLocal.get();
            // threadLocal 里没有存储 Connection, 就是第一次获取
            if (connection == null) {
                // 在连接池中获取一个连接, 存储在 threadLocal 里
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 对外提供回收连接的方法
    public static void release() {
        try {
            Connection connection = threadLocal.get();
            if (connection != null) {
                // 从 ThreadLocal 中移除当前已经存储的 connection 对象
                threadLocal.remove();
                // 如果开启了事务的手动提交, 操作完毕后, 归还给连接池之前, 要将事务的自动提交改为 true
                connection.setAutoCommit(true);
                // 将 Connection 对象归还给连接池
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

