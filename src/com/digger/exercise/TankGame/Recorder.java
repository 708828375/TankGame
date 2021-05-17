package com.digger.exercise.TankGame;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

    private static BufferedWriter bw = null;

    //将allEnemyTankNum写入文件
    public static void saveRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(fileName));
            //将数据写入文件，写完数据后面加换行
            bw.write(allEnemyTankNum + "\r\t");
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
