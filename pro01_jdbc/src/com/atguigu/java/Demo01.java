package com.atguigu.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Demo01 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url="jdbc:mysql://localhost:3306/fruitdb?useSSL=false";
        String user="root";
        String pwd="123456";

        //加载驱动
        Class.forName("org.gjt.mm.mysql.Driver");
        //驱动管理器
        Connection conn=DriverManager.getConnection(url,user,pwd);

        //编写sql语句
        String sql="insert into t_fruit values(0,?,?,?,?)";
        //创建预处理命令对象
        PreparedStatement ps=conn.prepareStatement(sql);
        //填充参数
        ps.setString(1,"菠萝");
        ps.setInt(2,29);
        ps.setInt(3,344);
        ps.setString(4,"这是一个菠萝");
        //执行更新（增删改），返回影响行数
        int flag =ps.executeUpdate();
        System.out.println(flag>0?"添加成功！":"添加失败！");

        //释放资源,先关闭ps.后关闭conn
        ps.close();
        conn.close();

    }
}
