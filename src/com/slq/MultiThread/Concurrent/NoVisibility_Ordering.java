package com.slq.MultiThread.Concurrent;

/**
 * @author qingqing
 * @function:
 * @create 2021-04-07-16:46
 */
public class NoVisibility_Ordering {
    private static /*volatile*/ boolean ready = false;   //可见性问题
    private static int number;
    private static class ReaderThread extends Thread{
        @Override
        public void run() {
            while(!ready){
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t = new ReaderThread();
        t.start();
        number = 42;     //number和ready在主线程中可能乱序，导致t线程中输出number值为0；
        ready = true;    //ready的改变t可能没有马上看到
        t.join();
    }
}
