package com.atguigu.fruit.dao.base;

import com.atguigu.fruit.pojo.Fruit;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDAO<T> {
    protected String DRIVER = "com.mysql.jdbc.Driver";
    protected String URL = "jdbc:mysql://localhost:3306/fruitdb?useSSL=false";
    protected String USER = "root";
    protected String PWD = "123456";

    protected PreparedStatement ps;
    protected ResultSet rs;
    protected Connection conn;

    //T的Class对象
    private Class entityClass;

    public BaseDAO() {
        //getClass获取Class对象，当前我们执行的是new FruitDAOImpl（），创建的是FruitDAOImpl的实列
        //那么子类构造方法内部首先会调用父类（BaseDAOI）的无参构造方法
        //因此此处的getClass（）会被执行，但是getClass会的是FruitDAOImpl的Class
        //所以getGenericSuperclass()获取到的是BaseDAO的Class
        Type genericType = getClass().getGenericSuperclass();
        //gParameterizedType参数化类型
        Type[] actualTypeArguments = ((ParameterizedType) genericType).getActualTypeArguments();
        //获取到的<T>中的T的真实类型
        Type actualType = actualTypeArguments[0];

        try {
            entityClass = Class.forName(actualType.getTypeName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Connection getConn() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL, USER, PWD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void close(Connection conn, ResultSet rs, PreparedStatement ps) {
        try {
            if (rs != null)
                rs.close();
            if (ps != null)
                ps.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //执行更新操作，返回影响行数
    protected int executeUpdate(String sql, Object... params) {
        boolean insertFlag = false;
        insertFlag = sql.trim().toUpperCase().startsWith("INSERT");
        try {
            conn = getConn();
            if (insertFlag) {
                ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            } else {
                ps = conn.prepareStatement(sql);
            }
            setParams(ps, params);

            int count = ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                //insert语句返回的是自增列的值，而不是影响行数
                return ((Long)rs.getLong(1)).intValue();
            }


            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, rs, ps);
        }
        return 0;
    }


    //给预处理命令对象设置参数
    private void setParams(PreparedStatement ps, Object... params) throws SQLException {
        if (params != null && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
        }

    }

    //通过反射技术给obj对象的property属性赋propertyValue值
    private void setValue(Object obj, String property, Object propertyValue) {
        Class clazz = obj.getClass();
        try {
            //获取property这个字符串对应的属性名，比如“fid”去找obj对象中的fid属性
            Field field = clazz.getDeclaredField(property);
            if (field != null) {
                field.setAccessible(true);
                field.set(obj, propertyValue);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    //执行所有查询操作
    protected List executeQuery(String sql, Object... params) {
        List<T> list = new ArrayList<>();
        try {
            conn = getConn();
            ps = conn.prepareStatement(sql);
            setParams(ps, params);
            rs = ps.executeQuery();
            //通过rs可以获取结果集的元数据
            //元数据：描述结果集的数据，简单讲，就是这个结果集有哪些列，什么类型等等
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                T entity = (T) entityClass.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    Object columnValue = rs.getObject(i + 1);
                    setValue(entity, columnName, columnValue);
                }

                list.add(entity);

            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            close(conn, rs, ps);
        }
        return list;
    }

    //执行单个数据查询
    protected T load(String sql, Object... params) {
        try {
            conn = getConn();
            ps = conn.prepareStatement(sql);
            setParams(ps, params);
            rs = ps.executeQuery();
            //通过rs可以获取结果集的元数据
            //元数据：描述结果集的数据，简单讲，就是这个结果集有哪些列，什么类型等等
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                T entity = (T) entityClass.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    String columnName = rsmd.getColumnName(i + 1);
                    Object columnValue = rs.getObject(i + 1);
                    setValue(entity, columnName, columnValue);
                }
                return entity;
            }
        } catch (SQLException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            close(conn, rs, ps);
        }
        return null;

    }

    //执行复复杂查询，返回例如统计结果
    protected Object[] executeComplexQuery(String sql, Object... params) {
        try {
            conn = getConn();
            ps = conn.prepareStatement(sql);
            setParams(ps, params);
            rs = ps.executeQuery();
            //通过rs可以获取结果集的元数据
            //元数据：描述结果集的数据，简单讲，就是这个结果集有哪些列，什么类型等等
            ResultSetMetaData rsmd = rs.getMetaData();
            //获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            Object[] columnCountArr = new Object[columnCount];

            while (rs.next()) {


                for (int i = 0; i < columnCount; i++) {

                    Object columnValue = rs.getObject(i + 1);
                    columnCountArr[i] = columnCount;
                }
                return columnCountArr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, rs, ps);
        }
        return null;

    }
}


