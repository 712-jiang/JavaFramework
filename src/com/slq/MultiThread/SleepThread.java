package com.slq.MultiThread;

import javax.sound.midi.SoundbankResource;

/**
 * @author qingqing
 * @function:
 * @create 2021-03-23-15:34
 */
public class SleepThread {
    public static void main(String[] args) throws InterruptedException {
        SubThread st = new SubThread();
        Thread t = new Thread(st);
        t.start();
        //希望3s后t线程被终止
        Thread.sleep(1000*3);
        st.running = false;   //更改标志位
        System.out.println("main end");
    }
}

class SubThread implements Runnable{
    boolean running = true;
    @Override
    public void run() {
        int n=0;
        //布尔标记，终止线程
        while(running){
            n++;
            System.out.println(n);
        }
        System.out.println("在结束前进行现场保护");
        return;
    }
}
