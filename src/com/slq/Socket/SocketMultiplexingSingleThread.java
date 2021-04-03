package com.slq.Socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author qingqing
 * @function:
 * @create 2021-04-02-14:31
 */
public class SocketMultiplexingSingleThread {
    private ServerSocketChannel server = null;
    private Selector selector = null;  //linux 多路复用器 （可能是select poll   epoll中的任何一个）
    int port = 9090;

    public void initServer(){
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            //如果是在epoll模式下， open（） -> epoll_create -> fd3
            selector = Selector.open();

            // server约等于 listen状态的 fd4，
            /*
             * register:
             * 如果系统调用为 select， poll， jvm开辟一个数组，把fd放进去
             * 如果系统调用为 epoll: epoll_ctl(fd3, ADD, fd4, EPOLLIN)
             * */
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        initServer();
        System.out.println("服务已启动");
        try{
            while (true){
                Set<SelectionKey> keys = selector.keys();
                System.out.println(keys.size()+ " size");

                /*
                 * 1. 调用多路复用器（select， poll， epoll）
                 * 如果系统调用为 select， poll， select(fd4)传递给内核，让内核判断该文件描述符就绪否
                 *
                 * 如果系统调用为 epoll:此时的selector.select()方法相当于调用了epoll_wait()系统调用
                 * */
                while (selector.select(500) > 0){
                    //开始fd
                    Set<SelectionKey> selectionKeys = selector.selectedKeys(); //返回就绪的fd集合
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey next = iterator.next();
                        iterator.remove();
                        if (next.isAcceptable()){
                            //select() 和 poll() 放到数组里
                            //epoll() 调用 epoll_create()放到内核空间的红黑树里
                            acceptHandler(next);
                        }else if (next.isReadable()){
                            readHandler(next);
                        }else if (next.isWritable()){
                            writeHandler(next);
                        }
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeHandler(SelectionKey next) {

    }

    private void readHandler(SelectionKey selectionKey) {

    }

    private void acceptHandler(SelectionKey selectionKey) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
            SocketChannel client = ssc.accept(); // 接受客户端连接
            client.configureBlocking(false);
            ByteBuffer buffer = ByteBuffer.allocate(8192); // 设置最大空间
            //select() 和 poll() 放到数组里
            //epoll() 调用 epoll_create()放到内核空间的红黑树里
            client.register(selector, SelectionKey.OP_READ, buffer);
            System.out.println("新客户端： "+ client.getRemoteAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
