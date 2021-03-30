package com.slq.MultiThread;

/**
 * @author qingqing
 * @function:
 * @create 2021-03-22-16:46
 */
public class newThread {
    public static void main(String[] args) {
//        //new一个Runnable类型的实例--创建一个可运行的对象
//        AnotherRunable r = new AnotherRunable();
//        //将Runnable实例传入Thread，tr就是一个线程
//        //将可运行对象封装成一个线程对象
//        Thread tr = new Thread(r);
        //合并代码
        Thread tr = new Thread(new AnotherRunable());
        //启动线程，跑的是AnotherRunable的run方法
        tr.start();

        //继承Thread类创建线程
        Thread t = new MyRunnable();
        t.start();
        System.out.println("main end");

        //采用匿名内部类,创建线程对象
        //{}内是匿名内部类，继承Runnable接口--new [匿名类] implement Runnable{...}
        Thread tn = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类创建线程");
            }
        });
        tn.start();
        //启动线程
        t.start();
    }
}

class Counter {
    public static final Object lock = new Object();
    public static int count = 0;
}

class MyRunnable extends Thread{
    @Override
    public void run() {
        super.run();
        synchronized(Counter.lock){
            System.out.println("Thread start");
            System.out.println("Thread end");
        }

    }
}

//这并不是一个线程类，只是一个继承了Runnable的类
//用于传入public Thread(Runnable target)构造方法中的target
class AnotherRunable implements Runnable{
    @Override
    public void run() {
        System.out.println("AnotherRunable start");
    }
}
