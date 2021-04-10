package com.slq.MultiThread.Concurrent;

/**
 * @author qingqing
 * @function:
 * @create 2021-04-07-11:11
 */
public class Visibility {
    private static volatile boolean running=true;
    private static void m(){
        System.out.println("m start");
        while(running){
            System.out.println("println有syncronized关键字，会触发内部缓存同步刷新");
        }
        System.out.println("m end");
    }

    private static class A{
        boolean inner = true;
        void test(){
            System.out.println("test start");
            while(inner){
                //A的实例a由volatile修饰，但是无法同步内部字段inner
            }
            System.out.println("test end");
        }
    }
    private volatile static A a = new A();
    public static void main(String[] args) throws InterruptedException {
//        Thread t1 = new Thread(Visibility::m);
//        t1.start();
//        Thread.sleep(1000);
//        running = false;
//        System.out.println("main end");

        Thread t2 = new Thread(a::test);
        t2.start();
        Thread.sleep(1000);
        a.inner = false;   //t2是不会执行“test end的”
    }
}
