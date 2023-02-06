package com.atguigu.fruit.dao.impl;

import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.base.BaseDAO;
import com.atguigu.fruit.pojo.Fruit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FruitDAOImip extends BaseDAO<Fruit> implements FruitDAO {

    @Override
    public List<Fruit> getListFruit() {
        String sql = "select * from t_fruit";
        return super.executeQuery(sql);
    }

    @Override
    public boolean addFruit(Fruit fruit) {
        String sql = "insert into t_fruit values(0,?,?,?,?)";
        return super.executeUpdate(sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark()) > 0;
    }

    @Override
    public boolean updateFruit(Fruit fruit) {
        String sql = "update t_fruit set fcount = ? where fid = ?";
        return super.executeUpdate(sql, fruit.getFcount(), fruit.getFid()) > 0;
    }

    @Override
    public Fruit getFruitByFname(String fname) {
        String sql = "select * from t_fruit where fname like ?";
        return super.load(sql, fname);
    }

    @Override
    public boolean delFruit(String fname) {

        String sql = "delete from t_fruit where fname like ?";
        return super.executeUpdate(sql, fname) > 0;
    }
}
