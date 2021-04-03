package com.slq.MultiThread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author qingqing
 * @function:
 * @create 2021-03-24-14:04
 */
public class CallableThread {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("main start");
        //创建FutureTask，传入Callable中的call--相当于run
        //FutureTask-->继承RunnableFuture-->继承Runnable
        FutureTask task = new FutureTask(new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("task start");
                Thread.sleep(1000*3);
                int a = 100;
                int b = 300;
                System.out.println("task end");
                return a+b;
            }
        });
        Thread t = new Thread(task);
        t.start();
        //如果call中的方法没有执行完，将阻塞main方法的执行
        Object res = task.get();
        System.out.println("result is " + res);
        System.out.println("main end");
    }
}
