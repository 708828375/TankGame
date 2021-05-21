package com.digger.exercise.TankGame;

/**
 * @Description : 坦克的基类
 * @Author : 孙梦琼
 * @Date : 2021/4/18 20:18
 * @Version : 1.0
 **/
public class Tank {
    private int x;
    private int y;
    private int direction = 0; //0上 1右 2下 3左
    private int speed = 1; //控制坦克的速度
    private boolean isLive = true;

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //上右下左移动
    public void moveUp() {
        y -= speed;
    }

    public void moveRight() {
        x += speed;
    }

    public void moveDown() {
        y += speed;
    }

    public void moveLeft() {
        x -= speed;
    }

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

}
