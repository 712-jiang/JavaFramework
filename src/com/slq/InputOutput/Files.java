package com.slq.InputOutput;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author qingqing
 * @function:
 * @create 2021-04-08-10:08
 */
public class Files {
    public static void main(String[] args) throws IOException {
        final File filename = new File("E:\\java\\5-SpringFramework\\myfile.txt");
        final File writefile = new File("E:\\java\\5-SpringFramework\\writefile.txt");
        final Scanner in = new Scanner(filename);
        while (in.hasNext()){
            System.out.println(in.nextLine());
        }
        final FileWriter fw = new FileWriter(filename,true);   //PrintWriter可以接收Writer类型参数
        //FileWriter简介extends Writer，可以通过FileWriter将append设为true，使用追加模式而不是覆盖模式
        final PrintWriter out = new PrintWriter(fw);   //直接利用PrintWriter会覆盖原来文件内容
        out.println("I'm waitting for you, Herry");
        out.println("你咋才来");
        out.close();   //不要忘记
    }
}
