package com.atguigu.java;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Demo02 {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        String url="jdbc:mysql://localhost:3306/fruitdb?useSSL=false";
        String user="root";
        String pwd="123456";

        //加载驱动
        Class.forName("org.gjt.mm.mysql.Driver");
        //驱动管理器
        Connection conn=DriverManager.getConnection(url,user,pwd);
        
        //编写sql语句
        String sql="select * from t_fruit";
        //创建预处理命令对象
        PreparedStatement ps=conn.prepareStatement(sql);

        //执行查询，返回结果集
        ResultSet rs = ps.executeQuery();
        //解析结果集
        List<Fruit> fruitList = new ArrayList<>();
       while(rs.next()){
           //1表示读取当前行的第一列的数据
           //getInt，因为这一列是int类型，所以使用getInt
           //getInt（也可以填结果集的列名）
           int fid=rs.getInt(1);
           String fname = rs.getString(2);
           int price = rs.getInt(3);
           int fcount = rs.getInt(4);
           String remark = rs.getString(5);
           Fruit fruit =new Fruit(fid,fname,price,fcount,remark);
           fruitList.add(fruit);
       }
        //释放资源
        rs.close();
        ps.close();
        conn.close();

        fruitList.forEach(System.out::println);

    }
}
