package com.slq.MultiThread;

import javax.crypto.spec.IvParameterSpec;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qingqing
 * @function:
 * @create 2021-03-24-14:57
 */
public class ProducerConsumer {
    public static void main(String[] args) {
        //创建仓库，共用
        List list = new ArrayList();
        Producer producer = new Producer(list);
        Consumer consumer = new Consumer(list);
        Thread tp = new Thread(producer);
        Thread tc = new Thread(consumer);
        tp.start();
        tc.start();
    }

}
//生产者类
class Producer implements Runnable{
    //创建仓库
    private List list;

    public Producer(List list) {
        this.list = list;
    }

    @Override
    public void run() {
        //一直运行
        while (true){
            //synchronized代码块中是需要保证同步线程安全的操作
            //仓库--list是锁
            synchronized (list){
                //仓库满
                if(list.size()>0){
                    try {
                        //生产者线程等待，释放锁：list
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //仓库空
                Object obj = new Object();
                list.add(obj);
                System.out.println("生产者线程："+ Thread.currentThread().getName() + "-->" + obj );
                //仓库满，唤醒消费者线程
                //此时list还在生产者手中，消费者处于就绪态，等进入if，list.wait()
                //生产者释放锁，消费者就可以运行了
                list.notify();
            }
        }
    }
}

//消费者类
class Consumer implements Runnable{
    //创建仓库
    private List list;

    public Consumer(List list) {
        this.list = list;
    }

    @Override
    public void run() {
        while(true){
            synchronized (list){
                //仓库空
                if(list.size() == 0){
                    try {
                        //消费者线程等待，释放锁：list
                        list.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //仓库满
                Object obj = list.remove(0);
                System.out.println("消费者线程："+ Thread.currentThread().getName() + "-->" + obj );
                //仓库空，唤醒生产者线程
                list.notify();
            }
        }

    }
}