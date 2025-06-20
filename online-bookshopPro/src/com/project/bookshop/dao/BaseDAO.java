package com.project.bookshop.dao;


import com.project.bookshop.util.JDBCUtil;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 将共性的数据库操作代码封装到 BaseDAO 里
 */

public class BaseDAO {
    /**
     * 通用的增删改的方法
     *
     * @param sql    调用者要执行的 sql语句
     * @param params sql 语句中的占位符要赋值的参数
     * @return 受影响的行数
     */
    public int executeUpdate(String sql, Object... params) throws Exception {
        // 1. 通过 JDBCUtil 获取数据库连接
        Connection connection = JDBCUtil.getConnection();

        // 2. 预编译SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 3. 为占位符赋值, 执行SQL, 接收返回结果
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                // 占位符是从1开始的, 参数的数组是从0开始的
                preparedStatement.setObject(i + 1, params[i]);
            }
        }
        int row = preparedStatement.executeUpdate();

        // 4. 释放资源
        preparedStatement.close();
        // 如果为true, 即自动提交事务, 则直接释放资源
        // 如果为 false, 即手动提交事务, 则在调用方法之后释放资源
        if (connection.getAutoCommit()){
            JDBCUtil.release();
        }

        // 5. 返回结果
        return row;
    }

    /**
     * 通用的查询: 多行多列、单行多列、单行单列
     * 多行多列: List<SysUser>
     * 单行多列: SysUser
     * 单行单列: 封装的是一个结果, Double, Integer...
     * 封装过程:
     * 1. 返回的类型: 泛型: 类型不确定, 调用者知道, 调用时, 将此次查询的结果类型告知BaseDAO即可
     * 2. 返回的结果: 通用List, 可以存储多个结果, 也可以存储一个结果 get(0)
     * 3. 结果的封装: 反射, 要求调用者告知 BaseDAO 要封装对象的类对象, Class
     */
    public <T> List<T> executeQuery(Class<T> clazz, String sql, Object... params) throws Exception {
        // 获取连接
        Connection connection = JDBCUtil.getConnection();

        // 预编译SQL语句
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        // 设置占位符的值
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i + 1, params[i]);
            }
        }

        // 执行SQL, 并接收返回的结果集
        ResultSet resultSet = preparedStatement.executeQuery();

        // 获取结果集中的元数据对象
        // 包含了: 列的数量, 每个列的名称
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        List<T> list = new ArrayList<>();
        // 处理结果
        /*
        每while循环一次是一行数据, 要拿到的是每一个列的数据, 但不知道列名, 有多少列
        通过for语句和 metaData.getColumnCount() 拿到元数据里有多少列, 就要取多少次
        每一次通过resultSet.getObject(i+1) 顺序拿到每一个列的值, 每一个列的值都要对应一个属性,
        拿到列的名称metaData.getColumnLabel(i) 得到名字之后通过反射的方式获取对象的属性, 去掉private的语法检查
        最后赋值即可
        for 循环只做了对象的属性赋值
        while循环完一次封装了一个对象
        每循环完一次就放在List集合里
        * */
        while (resultSet.next()) {
            // 循环一次, 代表有一行数据, 通过反射创建一个对象
            T t = clazz.newInstance();
            // 循环遍历当前行的列, 循环几次, 就有多少列
            for (int i = 0; i < columnCount; i++) {
                // 通过下标获取列的值
                Object value = resultSet.getObject(i + 1);

                // 获取到的列的value值, 这个值就是 t 这个对象中的某一个属性
                // 获取当前拿到的列的名字 = 对象的属性名
                String fieldName = metaData.getColumnLabel(i + 1);
                // 通过类对象和 fieldName 获取要封装的对象的属性
                Field field = clazz.getDeclaredField(fieldName);
                // 突破封装的private, 取消属性的封装检查 private
                field.setAccessible(true);
                field.set(t, value);
            }
            list.add(t);
        }
        resultSet.close();
        preparedStatement.close();
        if (connection.getAutoCommit()){
            JDBCUtil.release();
        }

        return list;
    }

    /**
     * 通用查询: 在上面查询的集合结果中获取第一个结果, 简化了单行单列的获取和单行多列的获取
     */
    public <T> T executeQueryBean(Class<T> clazz, String sql, Object... params) throws Exception {
        List<T> list = this.executeQuery(clazz, sql, params);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }
}
