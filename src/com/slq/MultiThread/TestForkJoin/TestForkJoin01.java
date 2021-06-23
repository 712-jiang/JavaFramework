package com.slq.MultiThread.TestForkJoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.DoublePredicate;

/**
 * @author 712
 * @function:   测试Fork-join的功能--没搞明白【TODO】
 * @create 2021/6/22 9:51
 */
public class TestForkJoin01 {
    public static void main(String[] args) {
        final int SIZE=10000000;
        double[] number = new double[SIZE];
        //给number赋初值
        for(int i=0;i<SIZE;i++) number[i]=Math.random();
        Counter counter = new Counter(number,0,SIZE,x->x>0.5);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(counter);
        System.out.println(counter.join());
    }
    static class Counter extends RecursiveTask<Integer>{
        public static final int THRESHOLD = 1000;
        private double[] values;
        private int from;   //counter对象的开始
        private int to;     //counter对象的结束
        private DoublePredicate filter;  //?

        public Counter(double[] values, int from, int to, DoublePredicate filter) {
            this.values = values;
            this.from = from;
            this.to = to;
            this.filter = filter;
        }

        @Override
        protected Integer compute() {
            if(to-from <THRESHOLD){    //小于阈值
                int count=0;
                for(int i=from;i<to;i++){
                    if(filter.test(values[i])) count++;   //filter x<0.5
                }
                return count;   //values中的随机数有多少个通过filter的test：x<0.5
            }
            else{                       //大于阈值
                int mid = (from+to)/2;  //fork-拆半计算
                Counter first = new Counter(values, from, mid, filter);
                Counter second = new Counter(values, mid, to, filter);
                invokeAll(first,second);
                return first.join()+second.join();
            }
        }
    }
}
