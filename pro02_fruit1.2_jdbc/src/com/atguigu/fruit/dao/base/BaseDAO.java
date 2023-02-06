package com.atguigu.fruit.dao.base;

import java.sql.*;

public abstract class BaseDAO {
    protected String DRIVER = "com.mysql.jdbc.Driver";
    protected String URL = "jdbc:mysql://localhost:3306/fruitdb?useSSL=false";
    protected String USER = "root";
    protected String PWD = "123456";

    protected PreparedStatement ps;
    protected ResultSet rs;
    protected Connection conn;

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

    protected int executeUpdate(String sql, Object... params) {
        try {

            conn = getConn();
            ps = conn.prepareStatement(sql);

            if (params != null && params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    ps.setObject(i + 1, params[i]);
                }
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn, rs, ps);
        }
        return 0;
    }

}


