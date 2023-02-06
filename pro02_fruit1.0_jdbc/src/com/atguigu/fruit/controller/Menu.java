package com.atguigu.fruit.controller;


import com.atguigu.fruit.dao.FruitDAO;
import com.atguigu.fruit.dao.impl.FruitDAOImip;
import com.atguigu.fruit.pojo.Fruit;

import java.util.List;
import java.util.Scanner;

/**
 * 菜单类
 */
public class Menu {
    Scanner input = new Scanner(System.in);
    FruitDAO fruitDAO = new FruitDAOImip();
    //显示主菜单
    public int showMainMenu(){
        System.out.println("==============================欢迎使用水果库存系统==============================");
        System.out.println("1.查看水果库存列表");
        System.out.println("2.添加水果库存信息");
        System.out.println("3.查看特定水果库存信息");
        System.out.println("4.水果下架");
        System.out.println("5.退出");
        System.out.println("==============================================================================");
        System.out.print("请选择：");

        int slt = input.nextInt();
        return slt;
    }
    //显示水果库存列表
    public void showFruitList(){
        List<Fruit> listFruit = fruitDAO.getListFruit();

        System.out.println("------------------------------------------------------------------------------");
        System.out.println("FID\t\t名称\t\t单价\t\t库存\t\t备注");
        if (listFruit == null || listFruit.size()<=0 ){
            System.out.println("对不起，库存为空");
        }else {
            for (int i = 0; i <listFruit.size() ; i++) {
                Fruit fruit =listFruit.get(i);
                System.out.println(fruit);

            }
        }
        System.out.println("------------------------------------------------------------------------------");
    }

    //添加水果库存信息
    public void addFruit(){
        System.out.print("请输入水果名称：");
        String fname = input.next();
        Fruit fruit = fruitDAO.getFruitByFname(fname);
        if(fruit == null){
            System.out.print("请输入水果单价：");
            int price = input.nextInt();
            System.out.print("请输入水果库存：");
            int fcount = input.nextInt();
            System.out.print("请输入水果备注：");
            String remark = input.next();
            fruit = new Fruit(0,fname,price,fcount,remark);
            System.out.println(fruitDAO.addFruit(fruit)?"添加成功":"添加失败");
        }else {
            System.out.print("请输入追加的库存量：");
            int fcount = input.nextInt();
            fruit.setFcount(fruit.getFcount()+fcount);
            System.out.println(fruitDAO.updateFruit(fruit)?"添加成功":"添加失败");

        }

    }

    //查看指定的水果库存信息
    public void showFruitInfo(){
        System.out.print("请输入水果名称：");
        String fname = input.next();
        Fruit fruit = fruitDAO.getFruitByFname(fname);
        if(fruit == null){
            System.out.println("对不起，没有找到指定的水果库存信息");
        }else {
            System.out.println("------------------------------------------------------------------------------");
            System.out.println("FID\t\t名称\t\t单价\t\t库存\t\t备注");
            System.out.println(fruit);
            System.out.println("------------------------------------------------------------------------------");
        }
    }

    public void delFruit(){
        System.out.print("请输入水果名称：");
        String fname = input.next();
        Fruit fruit = fruitDAO.getFruitByFname(fname);
        if(fruit == null){
            System.out.println("对不起，没有找到需要下架的水果信息！");
        }else {
            System.out.print("确认是否下架？（Y/N）");
            String slt = input.next();
            if ("Y".equalsIgnoreCase(slt)){
                fruitDAO.delFruit(fname);
            }
        }
    }

    //退出
    public boolean exit(){
        System.out.print("是否确认退出？（Y/N）");
        String slt = input.next();
        return !"Y".equalsIgnoreCase(slt);
    }

}
