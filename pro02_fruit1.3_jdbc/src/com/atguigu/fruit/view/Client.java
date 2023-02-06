package com.atguigu.fruit.view;

import com.atguigu.fruit.controller.Menu;

public class Client {
    public static void main(String[] args) {
        Menu m = new Menu();

        boolean flag = true;
        while (flag) {
            int slt = m.showMainMenu();
            switch (slt) {
                case 1:
                    m.showFruitList();
                    break;
                case 2:
                    m.addFruit();
                    break;
                case 3:
                    m.showFruitInfo();
                    break;
                case 4:
                    m.delFruit();
                    break;
                case 5:
                    flag = m.exit();
                    break;
                default:
                    System.out.println("你不按套路出牌！");
                    break;

            }

        }
    }
}