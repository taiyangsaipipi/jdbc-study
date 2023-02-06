package com.atguigu.fruit.dao.impl;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.pojo.Fruit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FruitDAOImip implements FruitDAO {
    final String DRIVER = "com.mysql.jdbc.Driver";
    final String URL = "jdbc:mysql://localhost:3306/fruitdb?useSSL=false";
    final String USER = "root";
    final String PWD = "123456";

    PreparedStatement ps;
    ResultSet rs;
    Connection conn;


    @Override
    public List<Fruit> getListFruit() {
        List<Fruit> fruitList = new ArrayList<>();

        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PWD);
            String sql = "select * from t_fruit";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int fid = rs.getInt(1);
                String fname = rs.getString(2);
                int price = rs.getInt(3);
                int fcount = rs.getInt(4);
                String remark = rs.getString(5);
                fruitList.add(new Fruit(fid, fname, price, fcount, remark));
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {

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
        return fruitList;
    }

    @Override
    public boolean addFruit(Fruit fruit) {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            String sql = "insert into t_fruit values(0,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, fruit.getFname());
            ps.setInt(2, fruit.getPrice());
            ps.setInt(3, fruit.getFcount());
            ps.setString(4, fruit.getRemark());

            return ps.executeUpdate() > 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean updateFruit(Fruit fruit) {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            String sql = "update t_fruit set fcount = ? where fid = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, fruit.getFcount());
            ps.setInt(2, fruit.getFid());
            return ps.executeUpdate() > 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Fruit getFruitByFname(String fname) {
        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            String sql = "select * from t_fruit where fname like ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fname);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int fid = rs.getInt(1);
                int price = rs.getInt(3);
                int fcount = rs.getInt(4);
                String remark = rs.getString(5);
                return new Fruit(fid, fname, price, fcount, remark);
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
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
        return null;
    }

    @Override
    public boolean delFruit(String fname) {

        try {
            Class.forName(DRIVER);
            Connection conn = DriverManager.getConnection(URL, USER, PWD);
            String sql = "delete from t_fruit where fname like ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fname);
            return ps.executeUpdate() > 0;

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
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
        return false;
    }
}
