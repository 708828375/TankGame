package com.digger.exercise.TankGame;

/**
 * @Description : 用来恢复文件中存储的数据---敌人坦克信息
 * @Author : 孙梦琼
 * @Date : 2021/5/18 9:54
 * @Version : 1.0
 **/
public class Node {
    private int x;
    private int y;
    private int direction;

    public Node(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
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
