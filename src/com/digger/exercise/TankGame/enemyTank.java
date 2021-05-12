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

    //定义一个向量存储所有的敌人坦克信息
    private Vector<enemyTank> enemyTanks = new Vector<>();

    //创建敌人的坦克时，就初始化一个子弹对象同时启动
    public enemyTank(int x, int y) {
        super(x, y);
    }

    //让敌人的坦克可以随机自由的移动
    @Override
    public void run() {

        while (true) {

            //这里我们判断如果shots的size() == 0,则创建一颗新的子弹放入shots并启动
            if (isLive() && shots.size() < 1) {
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
                        if (getY() > 0 && !isTouchEnemyTank()) {
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
                        if (getX() + 60 < 1000 && !isTouchEnemyTank()) {
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
                        if (getY() + 60 < 750 && !isTouchEnemyTank()) {
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
                        if (getX() > 0 && !isTouchEnemyTank()) {
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

    public void setEnemyTanks(Vector enemyTanks) {
        this.enemyTanks = enemyTanks;
    }

    //判断当前坦克是否碰到其他敌人的坦克
    public boolean isTouchEnemyTank() {
        //判断当前敌人坦克的方向
        switch (this.getDirection()) {
            case 0://上
                //让当前的敌人坦克和其他所有敌人坦克进行比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    enemyTank enemyTank = enemyTanks.get(i);
                    //不和自己进行比较
                    if (enemyTank != this) {
                        //如果敌人坦克方向是上/下
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2) {
                            //当前坦克左上角坐标是否在当前敌人坦克范围内
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克右上角坐标是否在当前敌人坦克范围内
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3) {
                            //当前坦克左上角坐标是否在当前敌人坦克范围内
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右上角坐标是否在当前敌人坦克范围内
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 1://右
                //让当前的敌人坦克和其他所有敌人坦克进行比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    enemyTank enemyTank = enemyTanks.get(i);
                    //不和自己进行比较
                    if (enemyTank != this) {
                        //如果敌人坦克方向是上/下
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2) {
                            //当前坦克右上角坐标是否在当前敌人坦克范围内
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克右下角坐标是否在当前敌人坦克范围内
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3) {
                            //当前坦克右上角坐标是否在当前敌人坦克范围内
                            if (this.getX() + 60 >= enemyTank.getX()
                                    && this.getX() + 60 <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角坐标是否在当前敌人坦克范围内
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 2://下
                //让当前的敌人坦克和其他所有敌人坦克进行比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    enemyTank enemyTank = enemyTanks.get(i);
                    //不和自己进行比较
                    if (enemyTank != this) {
                        //如果敌人坦克方向是上/下
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2) {
                            //当前坦克左下角坐标是否在当前敌人坦克范围内
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克右下角坐标是否在当前敌人坦克范围内
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 40
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3) {
                            //当前坦克左下角坐标是否在当前敌人坦克范围内
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克右下角坐标是否在当前敌人坦克范围内
                            if (this.getX() + 40 >= enemyTank.getX()
                                    && this.getX() + 40 <= enemyTank.getX() + 60
                                    && this.getY() + 60 >= enemyTank.getY()
                                    && this.getY() + 60 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
            case 3://左
                //让当前的敌人坦克和其他所有敌人坦克进行比较
                for (int i = 0; i < enemyTanks.size(); i++) {
                    enemyTank enemyTank = enemyTanks.get(i);
                    //不和自己进行比较
                    if (enemyTank != this) {
                        //如果敌人坦克方向是上/下
                        if (enemyTank.getDirection() == 0 || enemyTank.getDirection() == 2) {
                            //当前坦克左上角坐标是否在当前敌人坦克范围内
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 60) {
                                return true;
                            }
                            //当前坦克左下角坐标是否在当前敌人坦克范围内
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 40
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 60) {
                                return true;
                            }
                        }
                        if (enemyTank.getDirection() == 1 || enemyTank.getDirection() == 3) {
                            //当前坦克左上角坐标是否在当前敌人坦克范围内
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() >= enemyTank.getY()
                                    && this.getY() <= enemyTank.getY() + 40) {
                                return true;
                            }
                            //当前坦克左下角坐标是否在当前敌人坦克范围内
                            if (this.getX() >= enemyTank.getX()
                                    && this.getX() <= enemyTank.getX() + 60
                                    && this.getY() + 40 >= enemyTank.getY()
                                    && this.getY() + 40 <= enemyTank.getY() + 40) {
                                return true;
                            }
                        }
                    }
                }
                break;
        }
        //没有碰撞返回false
        return false;
    }
}
