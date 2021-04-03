package com.slq.Socket;

import javax.sound.midi.SoundbankResource;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author qingqing
 * @function:  多路复用器的实现，可以实现select、poll、epoll三种方式
 * @create 2021-04-02-16:32
 */
public class Multiplexer {
    private ServerSocketChannel server = null;
    private Selector selector = null;  //Linux多路复用器（select poll epoll kqueue）
    int port =9090;  //server端要绑定端口号
    public void initServer(){
        try {
            //创建服务端server、设置非阻塞、绑定接口
            server = ServerSocketChannel.open();  //fd4
            server.configureBlocking(false);  //accept()接收客户端不会阻塞
            server.bind(new InetSocketAddress(port));  //创建一个listen--fd4

            //server 约等于 listen状态的 fd4
            //Selector：
            //select、poll：不对内核做改变，只在当前进程中开辟空间
            //epoll:调用系统函数epoll_create ->fd7 内核开辟空间，可以通过fd7访问该空间
            selector = Selector.open();  //创建一个selector,fd7,开辟空间

            //register：
            //将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件,注册该事件后，
            //当该事件到达时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。
            //原码判断selector=？null

            //select，poll：把新建的fd存入进程空间（Selector.open()的时候开辟的空间）
            // 以便能够将所有fd一次性传入内核
            //epoll：调用系统函数epoll_ctl(7,EPOLL_CTL_ADD,4,EPOLLIN...   通过fd4将server的listen放入内核空间--fd7
            server.register(selector, SelectionKey.OP_ACCEPT); //xxx.refister(yyy,...)将xxx注册到yyy中

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start() throws IOException {
        initServer();
        System.out.println("服务器启动");
        while(true){
            Set<SelectionKey> keys = selector.keys();
            System.out.println(keys.size()+"  size");

            //select():
            //select、poll：执行内核程序select(fd4) poll(fd4),将前面register的fds都转到内核中
            //epoll：执行内核程序epoll_wait()，因为epoll在register的时候，已经将文件放入内核了，这个API就是等着
            while(selector.select(500)>0){
                //开始fd7中只有fd4-服务端listen的fd，selectedKeys返回的是listen返回的状态
                Set<SelectionKey> selectionKeys = selector.selectedKeys();  //内核程序返回有状态的fds
                Iterator<SelectionKey> iter = selectionKeys.iterator();   //创建迭代器，逐一遍历每一个有状态的IO
                //同步IO，需要app自己去遍历IO
                while(iter.hasNext()){
                    SelectionKey key = iter.next();
                    iter.remove();  //移除，不要重复
                    //注意，内核空间中有所有连接的fd和server----内核遍历后返回的，可能是isAcceptable,也可能是isReadable
                    if(key.isAcceptable()){   //isAcceptable是指当前遍历到的key--有状态的fd是client连接
                        //需要接收新连接
                        //select、poll
                        //epoll
                        accrptHandler(key);
                        //客户端返回的状态，连接要传数据了
                    }else if(key.isReadable()){  //读取client传入数据
                        readHandler(key);  //如果read的过程很慢，后面的连接效率就低，尽量在这个线程中只接收连接，不读写数据
                        //引申，Netty中，接收和建立连接放在两个线程中
                    }else if(key.isWritable()){
                        writeHandler(key);       //向client写数据
                    }
                }
            }

        }
    }

    private void writeHandler(SelectionKey key) {
    }

    private void readHandler(SelectionKey key) {
    }

    private void accrptHandler(SelectionKey key) {
        //从key中拿到channel
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        try {
            SocketChannel client = ssc.accept();  //调用系统方法：accept接口客户端 fd4
            client.configureBlocking(false);  //定义客户端连接非阻塞

            ByteBuffer buffer = ByteBuffer.allocate(8192);//创建读写缓存

            //select,poll：在jvm中开辟一个数组 fd7 放进去
            //epoll：调用系统函数epoll_ctl(fd3,ADD,fd7,EPOLLIN...将fd7导入内核开辟的空间
            //将接收到的client连接通过buffer，注册到多路复用器中
            client.register(selector, SelectionKey.OP_READ,buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
