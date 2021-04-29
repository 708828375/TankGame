package com.digger.exercise.TankGame;

import java.util.Vector;

/**
 * @Description : 我的坦克类
 * @Author : 孙梦琼
 * @Date : 2021/4/18 20:19
 * @Version : 1.0
 **/
public class myTank extends tank {
    //定义一个射击对象
    Shot shot = null;

    //可以发射多颗子弹
    Vector<Shot> shots = new Vector<>();

    public myTank(int x, int y) {
        super(x, y);
    }

    //射击子弹
    public void Shot() {

        //在面板上最多发射五颗子弹
        if (shots.size() < 5) {
            switch (getDirection()) {
                case 0: //向上
                    shot = new Shot(getX() + 20, getY(), 0);
                    break;
                case 1: //向右
                    shot = new Shot(getX() + 60, getY() + 20, 1);
                    break;
                case 2: //向下
                    shot = new Shot(getX() + 20, getY() + 60, 2);
                    break;
                case 3: //向左
                    shot = new Shot(getX(), getY() + 20, 3);
                    break;
            }
            shots.add(shot);
            //启动子弹射击线程
            new Thread(shot).start();
        }
        /*if (shot == null || !shot.isLive()) { //发射的子弹消亡后，才可以发射新的子弹
            switch (getDirection()) {
                case 0: //向上
                    shot = new Shot(getX() + 20, getY(), 0);
                    break;
                case 1: //向右
                    shot = new Shot(getX() + 60, getY() + 20, 1);
                    break;
                case 2: //向下
                    shot = new Shot(getX() + 20, getY() + 60, 2);
                    break;
                case 3: //向左
                    shot = new Shot(getX(), getY() + 20, 3);
                    break;
            }
            //启动子弹射击线程
            new Thread(shot).start();
        }else{
            shot.setLive(false);
        }*/
    }

}
