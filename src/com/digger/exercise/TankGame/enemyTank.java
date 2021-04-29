package com.digger.exercise.TankGame;

import java.util.Vector;

/**
 * @Description : 敌人的坦克
 * @Author : 孙梦琼
 * @Date : 2021/4/19 17:06
 * @Version : 1.0
 **/
public class enemyTank extends tank implements Runnable {

    //定义敌人坦克的子弹，可以有多颗子弹，因为涉及到线程，所以定义一个Vector来存放子弹
    Vector<Shot> shots = new Vector<>();

    //创建敌人的坦克时，就初始化一个子弹对象同时启动
    public enemyTank(int x, int y) {
        super(x, y);
    }

    //让敌人的坦克可以随机自由的移动
    @Override
    public void run() {

        while (true) {

            //这里我们判断如果shots的size() == 0,则创建一颗新的子弹放入shots并启动
            if (isLive() && shots.size() < 1){
                Shot shot = null;
                switch (getDirection()) {
                    case 0:
                        shot = new Shot(getX() + 20, getY(), 0);
                        break;
                    case 1:
                        shot = new Shot(getX() + 60, getY() + 20, 1);
                        break;
                    case 2:
                        shot = new Shot(getX() + 20, getY() + 60, 2);
                        break;
                    case 3:
                        shot = new Shot(getX(), getY() + 20, 3);
                        break;
                }
                shots.add(shot);
                //启动
                new Thread(shot).start();
            }


            //按照当前方向移动
            switch (getDirection()) {
                case 0:
                    //让坦克保持一个方向走30步
                    for (int i = 0; i < 30; i++) {
                        if (getY() > 0) {
                            moveUp();
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 1:
                    for (int i = 0; i < 30; i++) {
                        if (getX() + 60 < 1000) {
                            moveRight();
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 2:
                    for (int i = 0; i < 30; i++) {
                        if (getY() + 60 < 750) {
                            moveDown();
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
                case 3:
                    for (int i = 0; i < 30; i++) {
                        if (getX() > 0) {
                            moveLeft();
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    break;
            }
            //随机改变方向
            setDirection((int) (Math.random() * 4));

            //写并发程序，必须考虑清楚线程什么时候结束
            if (!isLive()) {
                break; //退出线程
            }
        }
    }
}
