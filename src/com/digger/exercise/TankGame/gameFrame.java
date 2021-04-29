package com.digger.exercise.TankGame;

import javax.swing.*;
import java.awt.event.KeyListener;

/**
 * @Description : 坦克活动区域
 * @Author : 孙梦琼
 * @Date : 2021/4/18 20:20
 * @Version : 1.0
 **/
public class gameFrame extends JFrame {

    private JPanel tankPanel = null;

    public static void main(String[] args) {
        new gameFrame();
    }

    public gameFrame() {
        tankPanel = new tankPanel();
        //将tankPanel放入到Thread并启动
        new Thread((Runnable) tankPanel).start();
        this.add(tankPanel);
        this.setSize(1200, 950);
        this.addKeyListener((KeyListener) tankPanel); // 监听tankPanel的键盘事件
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
}
