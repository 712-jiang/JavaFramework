package com.slq.Socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * @author qingqing
 * @function:NIO  一个线程就可以把所有的连接都接受了
 * @create 2021-04-01-14:57
 */
public class SocketNIO {
    public static void main(String[] args) throws IOException, InterruptedException {
        LinkedList<SocketChannel> clients = new LinkedList<>(); //存放接收到的client数据
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(9090));
        ss.configureBlocking(false);  //重点  设置非阻塞 nonblocking!!!

        while (true){
            //等待接收client的连接
            Thread.sleep(1000);
            SocketChannel client = ss.accept();   //SocketChannel的阻塞状态定义为false
            //client没有连接进来，accept返回null
            if(client == null){
                //返回对应的内容
                System.out.println("null");
            }//client连接进来，accept返回fd
            else{
                client.configureBlocking(false);  //建立连接后，服务端接收data非阻塞
                clients.add(client);  //list接收client
            }

            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);  //

            //遍历已经连接进来的客户端是否有data接收进来
            //因为非阻塞，同样存在没有data和有data两中情况
            for(SocketChannel c: clients){
                int num = c.read(buffer);  //read不会阻塞，返回值>0   -1   0
                if(num>0){
                    //有数据进来
                }
            }
        }
    }
}
