package com.slq.Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author qingqing
 * @function:BIO
 * @create 2021-03-30-17:53
 */
public class TestSocket {
    public static void main(String[] args) throws IOException {
        //服务端监听端口
        ServerSocket server = new ServerSocket(8090);
        System.out.println("step1");
        while(true){
            //可能产生阻塞：用户端一直不发信息
            Socket client = server.accept();
            System.out.println("step2 "+client.getPort());

            //将client读取抛到另外一个线程中，避免当前client一直不发数据，
            //下面while(true)中readLine阻塞，导致无法和其他client建立连接
            new Thread(new Runnable() {
                //用于接收client传入数据
                Socket ss;
                //读入client的输入
                public Runnable setSS(Socket s){
                    ss = s;
                    return this;
                }
                @Override
                public void run() {
                    try {
                        //获取输入流
                        InputStream in = ss.getInputStream();
                        BufferedReader reader = new BufferedReader((new InputStreamReader(in)));
                        while(true){
                            //用户一直不发东西，可能阻塞
                            System.out.println(reader.readLine());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }.setSS(client)
            ).start();
        }
    }
}
