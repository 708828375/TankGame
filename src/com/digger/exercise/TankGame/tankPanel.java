package com.digger.exercise.TankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @Description : 坦克绘制区域
 * @Author : 孙梦琼
 * @Date : 2021/4/18 20:17
 * @Version : 1.0
 **/

//为了监听键盘事件
//将tankPanel做成线程，让其一直重绘面板
public class tankPanel extends JPanel implements KeyListener, Runnable {

    //定义我的坦克
    myTank tank = null;

    //敌人的坦克，放入到集合中---考虑到多线程问题，所以此处使用Vector
    Vector<enemyTank> enemyTanks = new Vector<>();
    //存放恢复敌人坦克信息的Vector
    Vector<Node> nodes = null;
    private int tankSize = 3; //指定敌人坦克的数量

    //定义一个数组来存放炸弹
    Vector<Bomb> bombs = new Vector<>();

    //定义三张炸弹显示图片
    private Image bombImage1 = null;
    private Image bombImage2 = null;
    private Image bombImage3 = null;

    public tankPanel(String key) {
        //将enemyTanks赋值给Recorder的enemyTanks
        Recorder.setEnemyTanks(enemyTanks);

        switch (key) {
            case "1"://开始新游戏
                //初始化自己的坦克
                tank = new myTank(400, 100);
                //将我方坦克赋给Recorder的myTank
                Recorder.setMyTank(tank);
                //设置坦克速度
                //tank.setSpeed(5);
                //初始化敌人的坦克
                for (int i = 0; i < tankSize; i++) {
                    enemyTank enemyTank = new enemyTank(100 * (i + 1), 0);
                    //将所有的敌人坦克信息设置给每一个敌人坦克
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirection(2);//设置方向向下
                    //启动敌人坦克线程，让敌人坦克自由移动
                    new Thread(enemyTank).start();
                    enemyTanks.add(enemyTank);
                    //创建敌人坦克的同时创建子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    enemyTank.shots.add(shot);
                    //启动子弹线程
                    new Thread(shot).start();
                }
                break;
            case "2"://继续上局游戏
                //继续游戏，就进行数据恢复
                nodes = Recorder.getNodesAndMyTankAndGrades();
                tank = Recorder.getMyTank();
                //初始化敌人的坦克
                for (int i = 0; i < nodes.size(); i++) {
                    Node node = nodes.get(i);
                    enemyTank enemyTank = new enemyTank(node.getX(), node.getY());
                    //将所有的敌人坦克信息设置给每一个敌人坦克
                    enemyTank.setEnemyTanks(enemyTanks);
                    enemyTank.setDirection(node.getDirection());
                    //启动敌人坦克线程，让敌人坦克自由移动
                    new Thread(enemyTank).start();
                    enemyTanks.add(enemyTank);
                    //创建敌人坦克的同时创建子弹
                    Shot shot = new Shot(enemyTank.getX() + 20, enemyTank.getY() + 60, enemyTank.getDirection());
                    enemyTank.shots.add(shot);
                    //启动子弹线程
                    new Thread(shot).start();
                }
                break;
            default:
                System.out.println("输入有误！");
        }

        //加载爆炸效果的图片
        bombImage1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
        bombImage2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
        bombImage3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
    }

    //显示游戏相关信息
    public void showInfo(Graphics g) {
        //绘制提示信息
        g.setColor(Color.BLACK);
        //设置字体
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);
        g.drawString("您累积击毁敌方坦克", 1020, 30);
        //绘制一个敌方坦克
        drawTank(1020, 50, g, 0, 1);
        //显示击毁敌人坦克的数量
        g.setColor(Color.BLACK);
        g.drawString(Recorder.getAllEnemyTankNum() + "", 1080, 85);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.fillRect(0, 0, 1000, 750);//默认黑色填充背景

        //显示摧毁敌方坦克的提示信息
        showInfo(g);

        if (tank != null && tank.isLive()) {
            //绘制自己的坦克--封装方法
            drawTank(tank.getX(), tank.getY(), g, tank.getDirection(), 0);
        }

        //绘制敌人的坦克
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTank enemy = enemyTanks.get(i);
            if (enemy.isLive()) { //敌方坦克存活再绘制
                drawTank(enemy.getX(), enemy.getY(), g, enemy.getDirection(), 1);
                //绘制敌人坦克的所有子弹
                for (int j = 0; j < enemy.shots.size(); j++) {
                    Shot shot = enemy.shots.get(j);
                    if (shot.isLive()) {
                        g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
                    } else {
                        //从vector中移除子弹
                        enemy.shots.remove(shot);
                    }
                }
            }
        }

        //绘制炸弹
        for (int i = 0; i < bombs.size(); i++) {
            Bomb bomb = bombs.get(i);
            if (bomb.getLife() > 6) {
                g.drawImage(bombImage1, bomb.getX(), bomb.getY(), 60, 60, this);
            } else if (bomb.getLife() > 3) {
                g.drawImage(bombImage2, bomb.getX(), bomb.getY(), 60, 60, this);
            } else {
                g.drawImage(bombImage3, bomb.getX(), bomb.getY(), 60, 60, this);
            }
            //让炸弹生命周期减少
            bomb.lifeDown();
            if (bomb.getLife() == 0) { //若bomb生命周期为0，删除
                bombs.remove(bomb);
            }

        }

        //绘制我方坦克的子弹(一颗子弹)
        /*if (tank.shot != null && tank.shot.isLive()) { //子弹不为空且当前状态为存活，绘制子弹
            //g.fill3DRect(tank.shot.getX(), tank.shot.getY(), 1, 1, false);
            g.draw3DRect(tank.shot.getX(), tank.shot.getY(), 1, 1, false);
        }*/

        //绘制我方坦克的所有子弹（多颗子弹）
        g.setColor(Color.yellow);
        for (int i = 0; i < tank.shots.size(); i++) {
            Shot shot = tank.shots.get(i);
            if (shot != null && shot.isLive()) { //子弹不为空且当前状态为存活，绘制子弹
                //g.fill3DRect(tank.shot.getX(), tank.shot.getY(), 1, 1, false);
                g.draw3DRect(shot.getX(), shot.getY(), 1, 1, false);
            } else { //该子弹已经无效，则从shots中移除
                tank.shots.remove(shot);
            }
        }
    }

    /**
     * 绘制坦克的方法
     *
     * @param x         坦克的左上角x坐标
     * @param y         坦克的左上角y坐标
     * @param g         画笔
     * @param direction 坦克的方向
     * @param type      坦克的类型
     */
    public void drawTank(int x, int y, Graphics g, int direction, int type) {
        //根据坦克的类型，设置坦克的颜色
        switch (type) {
            case 0: //
                g.setColor(Color.yellow);
                break;
            case 1: //敌人的坦克
                g.setColor(Color.cyan);
                break;
        }

        //根据坦克的方向，来绘制对应形状的坦克
        //direction表示方向（0：向上 1：向右 2：向下 3：向左）
        switch (direction) {
            case 0: //向上
                g.fill3DRect(x, y, 10, 60, false); //绘制坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false); //绘制坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false); //绘制坦克的身子
                g.fillOval(x + 10, y + 20, 20, 20); //绘制坦克圆盖
                g.drawLine(x + 20, y, x + 20, y + 30);//绘制炮筒
                break;
            case 1: //向右
                g.fill3DRect(x, y, 60, 10, false); //绘制坦克上边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false); //绘制坦克下边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false); //绘制坦克的身子
                g.fillOval(x + 20, y + 10, 20, 20); //绘制坦克圆盖
                g.drawLine(x + 60, y + 20, x + 30, y + 20);//绘制炮筒
                break;
            case 2: //向下
                g.fill3DRect(x, y, 10, 60, false); //绘制坦克左边的轮子
                g.fill3DRect(x + 30, y, 10, 60, false); //绘制坦克右边的轮子
                g.fill3DRect(x + 10, y + 10, 20, 40, false); //绘制坦克的身子
                g.fillOval(x + 10, y + 20, 20, 20); //绘制坦克圆盖
                g.drawLine(x + 20, y + 60, x + 20, y + 30);//绘制炮筒
                break;
            case 3: //向左
                g.fill3DRect(x, y, 60, 10, false); //绘制坦克上边的轮子
                g.fill3DRect(x, y + 30, 60, 10, false); //绘制坦克下边的轮子
                g.fill3DRect(x + 10, y + 10, 40, 20, false); //绘制坦克的身子
                g.fillOval(x + 20, y + 10, 20, 20); //绘制坦克圆盖
                g.drawLine(x, y + 20, x + 30, y + 20);//绘制炮筒
                break;
            /*default:
                System.out.println("其他方向暂时不做处理");*/
        }
    }

    //判断敌方坦克的子弹是否击中我方坦克
    public void beHittedByAllEnemy() {
        for (int i = 0; i < enemyTanks.size(); i++) {
            enemyTank enemyTank = enemyTanks.get(i);
            Vector<Shot> shots = enemyTank.shots;
            for (int j = 0; j < shots.size(); j++) {
                Shot shot = shots.get(j);
                hitTank(shot, tank);
            }
        }

    }

    //判断一颗子弹是否击中坦克
    public void hitTank(Shot shot, tank tank) {
        switch (tank.getDirection()) {
            case 0:
            case 2:
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 40 && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 60) {
                    //子弹击中坦克
                    shot.setLive(false);
                    tank.setLive(false);
                    //击中敌方坦克时，allEnemyTankNum++
                    if (tank instanceof enemyTank) {
                        //被击中的是敌方坦克
                        Recorder.addAllEnemyTankNum();
                    }
                    //击中敌方坦克时，将被击中的敌方坦克删除
                    enemyTanks.remove(tank);
                    //子弹击中坦克时，创建一个炸弹对象放入bombs中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3:
                if (shot.getX() > tank.getX() && shot.getX() < tank.getX() + 60 && shot.getY() > tank.getY() && shot.getY() < tank.getY() + 40) {
                    //子弹击中坦克
                    shot.setLive(false);
                    tank.setLive(false);
                    //击中敌方坦克时，allEnemyTankNum++
                    if (tank instanceof enemyTank) {
                        //被击中的是敌方坦克
                        Recorder.addAllEnemyTankNum();
                    }
                    //击中敌方坦克时，将被击中的敌方坦克删除
                    enemyTanks.remove(tank);
                    //子弹击中坦克时，创建一个炸弹对象放入bombs中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    //判断我们坦克发出的所有子弹是否击中
    public void hitEnemyTank() {
        //判断每一颗子弹是否击中坦克
        for (int i = 0; i < tank.shots.size(); i++) {
            Shot shot = tank.shots.get(i);
            if (shot != null && shot.isLive()) {
                //循环遍历当前所有的敌方坦克看子弹是否击中
                for (int j = 0; j < enemyTanks.size(); j++) {
                    enemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    //处理WDSA键按下的情况
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) { // 向上
            //改变坦克的方向
            tank.setDirection(0);
            if (tank.getY() > 0) {
                //移动坦克
                tank.moveUp();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_D) { //向右
            tank.setDirection(1);
            if (tank.getX() + 60 < 1000) {
                tank.moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_S) {//向下
            tank.setDirection(2);
            if (tank.getY() + 60 < 750) {
                tank.moveDown();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_A) {//向左
            tank.setDirection(3);
            if (tank.getX() > 0) {
                tank.moveLeft();
            }
        }

        //处理玩家按下J键的情况，按下J键后发射子弹(启动一个线程)
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //发射的子弹消亡后，才可以发射新的子弹（发射一颗子弹）
            /*if (tank.shot == null || !tank.shot.isLive()) {
                //发射子弹
                tank.Shot();
            }*/
            tank.Shot();
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {
        while (true) {
            //休眠100毫秒
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /*//判断子弹是否击中坦克
            if (tank.shot != null && tank.shot.isLive()) {
                //循环遍历当前所有的敌方坦克看子弹是否击中
                for (int i = 0; i < enemyTanks.size(); i++) {
                    enemyTank enemyTank = enemyTanks.get(i);
                    hitTank(tank.shot, enemyTank);
                }
            }*/

            //我们坦克发出的子弹是否击中敌人的坦克
            hitEnemyTank();

            //敌人坦克的子弹是否击中我方坦克
            beHittedByAllEnemy();

            //重绘面板
            this.repaint();
        }
    }
}
