package com.digger.exercise.TankGame;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @Description : 坦克活动区域
 * @Author : 孙梦琼
 * @Date : 2021/4/18 20:20
 * @Version : 1.0
 **/
public class GameFrame extends JFrame {

    private JPanel tankPanel = null;
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new GameFrame();
    }

    public GameFrame() {
        System.out.println("请输入您的选择，1：开始新游戏，2：继续上局游戏");
        String key = scanner.next();
        tankPanel = new GamePanel(key);
        //将tankPanel放入到Thread并启动
        new Thread((Runnable) tankPanel).start();
        this.add(tankPanel);
        this.setSize(1300, 950);
        this.addKeyListener((KeyListener) tankPanel); // 监听tankPanel的键盘事件
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        //在点击关闭按钮时，调用Recorder的saveData方法保存allEnemyTankNum，监听关闭窗口
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //System.out.println("监听到关闭窗口了");
                Recorder.saveRecord();
                System.exit(0);
            }
        });
    }
}
