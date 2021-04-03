package com.slq.MultiThread;

/**
 * @author qingqing
 * @function: 1.3 Interrupt 笔记
 * @create 2021-04-03-20:13
 */
public class Interrupt {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            while(true){
                while (Thread.currentThread().isInterrupted()){
                    System.out.println("Thread is interrupted");
                    System.out.println(Thread.currentThread().isInterrupted());  //调用isInterrupted后状态不会被重置
                }
            }
        });

        Thread t2 = new Thread(()->{
            while(true){
                if(Thread.interrupted()){
                    System.out.println("Thread is interrupted");
                    System.out.println(Thread.interrupted());  //调用interrupted之后状态会被重置会false
                }
            }
        });
//        t1.start();
//        Thread.sleep(1000);
//        t1.interrupt();  //程序并不会暂停

        t2.start();
        Thread.sleep(1000);
        t2.interrupt();

        System.out.println("main:"+t2.interrupted());;  //查询当前线程！查询的是main,false
    }
}
