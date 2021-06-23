package com.slq.MultiThread.NewClass;

import java.util.ArrayList;

/**
 * @author 712
 * @function: jdk的类加载机制
 * @create 2021/6/15 19:47
 */
public class TestNewClass {
    public static volatile int a;
    public static void main(String[] args) {

        ArrayList<Integer> qinqing = new ArrayList<>();

        qinqing.add(4);

        a = qinqing.size();

        System.out.println(qinqing);
    }
}
