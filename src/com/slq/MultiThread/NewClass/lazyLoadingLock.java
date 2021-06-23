package com.slq.MultiThread.NewClass;

/**
 * @author qingqing
 * @function:懒汉式改进一：双重检查，两次判断INSTANCE == null
 * @create 2021-04-09-18:12
 */
public class lazyLoadingLock {
    //先不实例化
    private static volatile lazyLoadingLock INSTANCE;  //加volatile防止指令重排,new一个对象，编译为class文件后有：
    // 查看加载情况、分配内存空间、调用构造函数、返回地址引用，
    // 指令重排，先给引用赋值，还没执行构造函数，线程B执行，INSTANCE不为mull，返回一个没有初始化的INSTANCE
    private lazyLoadingLock(){
    }
    //避免多线程安全问题，在getInstance上加synchronized锁
    public static lazyLoadingLock getInstance() throws InterruptedException {
        //只有在INSTANCE为null的时候，才需要竞争锁，当INSTANCE已经被创建，不需要锁，提高效率，不用每次上锁，避免上下文切换（可以不要）
        if(INSTANCE == null){
            //用同步代码块
            //双重检查，防止在if后其他线程实例化
            //比直接将synchronize添加到getInstance方法上更高效
            synchronized (lazyLoadingLock.class){   //类只加载一次，当作synchronized的类锁
                if(INSTANCE == null){   //必须判断，synchronized之前，别的线程可能实例化lazyLoadingLock()
                    Thread.sleep(10);
                    INSTANCE = new lazyLoadingLock();
                }
            }
        }
        return INSTANCE;
    }

    public static void main(String[] args){
        for(int i=0;i<100;i++){
            new Thread(()->{
                lazyLoadingLock l = null;
                try {
                    l = getInstance();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(l.hashCode());
            }).start();
        }
    }
}
