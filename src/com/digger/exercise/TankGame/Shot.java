package com.digger.exercise.TankGame;

/**
 * @Description : 子弹射击（线程）
 * @Author : 孙梦琼
 * @Date : 2021/4/21 19:04
 * @Version : 1.0
 **/
public class Shot implements Runnable {
    //子弹的位置
    private int x;
    private int y;
    //子弹的方向
    private int direction = 0; //默认方向向上
    //子弹的速度
    private int speed = 2;
    //子弹存活状态
    private boolean isLive = true;

    //private Shot shot = null;

    //构造器
    public Shot(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    //单例模式，获取实例化对象
    /*public Shot getShotInstance(int x, int y, int direction) {
        if (shot != null) {
            shot.setX(x);
            shot.setY(y);
            shot.setDirection(direction);
            return shot;
        } else {
            return new Shot(x, y, direction);
        }
    }*/

    @Override
    public void run() {
        while (true) {
            //不断更新子弹的位置
            switch (direction) {
                case 0: //向上
                    y -= speed;
                    break;
                case 1: //向右
                    x += speed;
                    break;
                case 2: //向下
                    y += speed;
                    break;
                case 3: //向左
                    x -= speed;
                    break;
            }
            System.out.println("子弹坐标x=" + x + "y=" + y);

            //休眠50毫秒
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //当子弹超出边界时，停止更新子弹位置
            //当子弹击中坦克时，停止更新子弹位置
            if (!(x > 0 && x < 1000 && y > 0 && y < 750 && isLive)) {
                System.out.println("射击线程终止");
                //更改子弹存活状态
                isLive = false;
                break;
            }
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

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }
}
