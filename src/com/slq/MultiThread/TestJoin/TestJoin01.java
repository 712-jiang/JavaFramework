package com.slq.MultiThread.TestJoin;

/**
 * @author 712
 * @function:   测试join的特性
 * @create 2021/6/21 20:45
 */
public class TestJoin01 {
    public static void main(String[] args) throws InterruptedException {
        Thread threadone = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread());
            }
        });
        Thread threadtwo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread());
            }
        });
        threadone.start();
        threadtwo.start();
        System.out.println(Thread.currentThread() + "is start");
        threadone.join();  //main等threadone结束再执行
        threadtwo.join(3000);  //如果3000ms内threadtwo提前结束，main立刻继续；threadtwo没结束，main在3000ms后继续；
        System.out.println(Thread.currentThread() + "is end");
    }

//    public final synchronized void join(long millis)
//            throws InterruptedException {
//        long base = System.currentTimeMillis();
//        long now = 0;
//
//        if (millis < 0) {
//            throw new IllegalArgumentException("timeout value is negative");
//        }
//
//        if (millis == 0) {
//            while (isAlive()) {   //threadtwo.join()判断threadtwo线程是否存活
//                wait(0);   //main线程自旋，等待threadtwo结束
//            }
//        } else {                   //millis是join最长阻塞时间
//            while (isAlive()) {     //millis到达前，threadtwo结束，main线程提前启动
//                long delay = millis - now;
//                if (delay <= 0) {
//                    break;          //millis到达，threadtwo没结束，threadtwo.join()失效，main线程启动
//                }
//                wait(delay);
//                now = System.currentTimeMillis() - base;
//            }
//        }
//    }
}
