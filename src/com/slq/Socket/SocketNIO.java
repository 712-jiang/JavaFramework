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
        ServerSocketChannel ss = ServerSocketChannel.open();    //创建客户端
        ss.bind(new InetSocketAddress(9090));
        ss.configureBlocking(false);  //重点  设置非阻塞 nonblocking!!!

        while (true){
            //等待接收client的连接
            Thread.sleep(1000);
            //一个新连接到达ServerSocketChannel时，会创建一个SocketChannel
            SocketChannel client = ss.accept();   //SocketChannel的阻塞状态定义为false
            //client没有连接进来，accept返回null
            if(client == null){
                //返回对应的内容
                System.out.println("null");
            }   //client连接进来，accept返回fd
            else{
                client.configureBlocking(false);  //建立连接后，服务端接收data非阻塞
                clients.add(client);  //list接收client
            }

            ByteBuffer buffer = ByteBuffer.allocateDirect(4096);  //创建读写缓存

            //遍历已经连接进来的客户端是否有data接收进来
            //因为非阻塞，同样存在没有data和有data两种情况
            for(SocketChannel c: clients){
                //写入Buffer
                int num = c.read(buffer);  //read()方法将数据从SocketChannel写入到Buffer中，
                // read()方法返回的int值表示读了多少个字节进Buffer里
                // read不会阻塞，返回值>0   -1   0
                if(num>0){
                    //有数据进来，将Buffer装换为读模式
                    buffer.flip();
                    while(buffer.hasRemaining()){   //保证将buffer中数据读完
                        //从Buffer中读数据
                        System.out.println((char)buffer.get());   //每次获取一个byte
                        //int bytesWritten = inChannel.write(buf);  //另一种从Buffer中读数据的方法
                    }
                    buffer.clear();  //清除buffer，准备再次写入
                }
            }
        }
    }
}
