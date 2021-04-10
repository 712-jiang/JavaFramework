package com.slq.MultiThread.Concurrent;

import java.io.IOException;

/**
 * @author qingqing
 * @function:有序对象创建过程中代码乱序，导致的多线程问题
 * @create 2021-04-07-18:39
 */
public class NoOrdering_newObject {
    private int num = 8;
    public NoOrdering_newObject(){
        new Thread(()-> System.out.println(this.num)).start();
    }

    public static void main(String[] args) throws IOException {
        new NoOrdering_newObject();
        System.in.read();
    }
}
