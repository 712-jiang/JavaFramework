package com.slq.MultiThread;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author qingqing
 * @function:
 * @create 2021-03-22-21:39
 */
public class Sychronized{
    public static void main(String[] args) {
        Sychronized t = new Sychronized();  //在内部创建实例
        new Thread(()->t.m1()).start();
        new Thread(()->t.m2()).start(); //实现内部类，代替下面代码
//        new Thread(t::m1).start();    //实现内部类的另一种表达
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                t.m1();
//            }
//        }).start();
    }

    public synchronized void m1(){
        System.out.println("m1 start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1 end");
    }

    public void m2() {
        System.out.println("m2 start");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 end");
    }
}



