package com.digger.exercise.TankGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 * @Description : 记录游戏相关数据信息并且与文件进行交互
 * @Author : 孙梦琼
 * @Date : 2021/5/17 20:58
 * @Version : 1.0
 **/
public class Recorder {

    //记录我方坦克击毁的敌人坦克数
    private static int allEnemyTankNum = 0;
    //数据写入文件存储位置
    private static String fileName = "e:\\myRecord.txt";

    //定义一个Vector来保存要存储的敌人坦克信息
    private static Vector<enemyTank> enemyTanks = null;

    private static BufferedWriter bw = null;

    public static void setEnemyTanks(Vector<enemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //将allEnemyTankNum写入文件
    //对saveRecord进行升级，增加保存敌人坦克的坐标和方向
    public static void saveRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(fileName));
            //将数据写入文件，写完数据后面加换行
            bw.write(allEnemyTankNum + "\r\n");
            //遍历敌人坦克的Vector，然后根据情况保存
            for (int i = 0; i < enemyTanks.size(); i++) {
                enemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive()) {
                    //写入坦克信息
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirection();
                    bw.write(record + "\r\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                    //关闭流
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static int getAllEnemyTankNum() {
        return allEnemyTankNum;
    }

    public static void setAllEnemyTankNum(int allEnemyTankNum) {
        Recorder.allEnemyTankNum = allEnemyTankNum;
    }

    //击毁敌人坦克数量+1
    public static void addAllEnemyTankNum() {
        Recorder.allEnemyTankNum++;
    }
}
