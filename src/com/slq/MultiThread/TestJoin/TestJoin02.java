package com.slq.MultiThread.TestJoin;

/**
 * @author 712
 * @function:  测试join的特性
 * @create 2021/6/21 21:28
 */
public class TestJoin02 {
    public static void main(String[] args) throws InterruptedException {
        Thread threadone = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
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
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                threadone.interrupt();   //在threadone结束前打断，如果没有threadone.join没事
                                         //但是join是synchronized，中断报错
                System.out.println(Thread.currentThread());
            }
        });
        threadone.start();
        threadtwo.start();
        System.out.println(Thread.currentThread() + "is start");
        threadone.join();  //main等threadone结束再执行
        threadtwo.join();
        System.out.println(Thread.currentThread() + "is end");
    }
}
