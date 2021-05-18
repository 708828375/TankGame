package com.digger.exercise.TankGame;

import java.io.*;
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
    private static String fileName = "src\\myRecord.txt";

    //定义一个Vector来保存要存储的敌人坦克信息
    private static Vector<enemyTank> enemyTanks = null;

    //定义一个Vector用来保存从文件中恢复的敌人坦克信息
    private static Vector<Node> nodes = new Vector<>();

    //定义一个myTank保存我方坦克信息
    private static myTank myTank;

    private static BufferedWriter bw = null;
    private static BufferedReader br = null;

    public static String getFileName() {
        return fileName;
    }

    public static void setMyTank(com.digger.exercise.TankGame.myTank myTank) {
        Recorder.myTank = myTank;
    }

    public static com.digger.exercise.TankGame.myTank getMyTank() {
        return myTank;
    }

    //从文件中恢复敌人坦克信息到nodes中  同时恢复我方坦克信息和击中敌人的坦克数量
    public static Vector<Node> getNodesAndMyTankAndGrades() {
        try {
            br = new BufferedReader(new FileReader(fileName));
            String record = br.readLine();
            //将存储的游戏成绩读取出来赋值给allEnemyTankNunm
            Recorder.setAllEnemyTankNum(Integer.parseInt(record));
            //恢复文件中我方坦克的信息
            record = br.readLine();
            String[] xyd = record.split(" ");
            Recorder.setMyTank(new myTank(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1])));
            myTank.setDirection(Integer.parseInt(xyd[2]));
            Node node = null;
            while ((record = br.readLine()) != null) {
                //根据读取的信息创建一个Node
                xyd = record.split(" ");
                node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                //将node添加到Nodes中
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //返回从文件中恢复的坦克信息
        return nodes;
    }

    public static void setEnemyTanks(Vector<enemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    //将allEnemyTankNum写入文件
    //对saveRecord进行升级，增加保存敌人坦克的坐标和方向
    //保存我方的坦克信息
    public static void saveRecord() {
        try {
            bw = new BufferedWriter(new FileWriter(fileName));
            //将数据写入文件，写完数据后面加换行
            bw.write(allEnemyTankNum + "\r\n");
            //写入我方坦克信息
            bw.write(myTank.getX() + " " + myTank.getY() + " " + myTank.getDirection() + "\r\n");
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
