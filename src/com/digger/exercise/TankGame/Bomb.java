package com.digger.exercise.TankGame;

/**
 * @Description : 坦克被销毁时的炸弹
 * @Author : 孙梦琼
 * @Date : 2021/4/22 15:21
 * @Version : 1.0
 **/
public class Bomb {
    private int x;
    private int y;
    private int life = 9; //炸弹的生命周期
    private boolean isLive = true; //存活状态

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //减少生命值，用来实现炸弹的动态显示效果
    public void lifeDown(){
        if(life > 0){
            life --;
        }else {
            isLive = false;
        }
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

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
