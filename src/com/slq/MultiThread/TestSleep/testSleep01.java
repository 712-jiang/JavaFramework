package com.slq.MultiThread.TestSleep;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 712
 * @function:   测试sleep不释放锁；sleep期间不参与cpu的调度
 * @create 2021/6/21 22:12
 */
public class testSleep01 {
    private static final Lock lock = new ReentrantLock();  //threadone和threadtwo争抢同一把锁
    public static void main(String[] args) throws InterruptedException {
        Thread threadone = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread() + "is in sleep");
                    Thread.sleep(1000);
                    System.out.println(Thread.currentThread()+ "is wake");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //释放锁
                    lock.unlock();
                }
            }
        });

        Thread threadtwo = new Thread(new Runnable() {
            @Override
            public void run() {
                lock.lock();
                System.out.println(Thread.currentThread());
                lock.unlock();
            }
        });

        //threadthree不需要锁
        Thread threadthree = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread());
            }
        });

        threadone.start();
        threadtwo.start();
        Thread.sleep(10);  //故意延迟threadthree进入就绪态，让threadone先获得cup使用权
        threadthree.start();
    }
}
