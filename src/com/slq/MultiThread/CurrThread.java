package com.slq.MultiThread;

import com.sun.org.apache.xml.internal.res.XMLErrorResources_tr;

/**
 * @author qingqing
 * @function:
 * @create 2021-03-23-14:53
 */
//
public class CurrThread {
    public static void main(String[] args) throws InterruptedException {
        MyThread t = new MyThread();
        t.start();
        t.sleep(1000*3);

        System.out.println("main Thread");
    }


}
class MyThread extends Thread{
    @Override
    public void run() {
        for(int i=0; i<100; i++){
            System.out.println("MyThread-->"+i);
        }
    }
}