package com.slq.MultiThread.Executor;

import javax.sound.midi.SoundbankResource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author qingqing
 * @function: 执行器：executors 的作用
 * @create 2021-04-06-16:15
 */
public class ExecutorDemo {
    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        try(Scanner in = new Scanner(System.in)){     //
            System.out.println("enter base directory:");
            String start = in.nextLine();             //
            System.out.println("enter keyword:");
            String word = in.nextLine();
            System.out.println(word);
            Set<Path> files = descendants(Paths.get(start));  //
            List<Callable<Long>> tasks = new ArrayList<Callable<Long>>();   //Callable集合
            for(Path file:files){
                Callable<Long> task = () -> occurrences(word,file);
                tasks.add(task);
            }
            ExecutorService executor = Executors.newCachedThreadPool();  //创建一个线程池，立刻抛出线程一起执行所有任务
            //如果有空闲线程，使用空闲线程执行任务，没有则创建新线程

            //final ExecutorService executor = Executors.newSingleThreadExecutor();  //只有一个线程的线程池executor
            final Instant startTime = Instant.now();
            final List<Future<Long>> results = executor.invokeAll(tasks);
            long total = 0;
            for(Future<Long> result: results){
                total += result.get();
            }
            final Instant endTime = Instant.now();
            System.out.println("Search "+word+" :"+total);
            System.out.println("Time elapsed:"+ Duration.between(startTime,endTime).toMillis()+"ms");

            //invokeAny
            //为啥要重新创建Callable集合？
            List<Callable<Path>> searchTasks = new ArrayList<Callable<Path>>();
            for(Path file:files){
                searchTasks.add(searchForTask(word,file));
            }
            Path found = executor.invokeAny(searchTasks);
        }
    }

    public static Set<Path> descendants(Path rootDir) throws IOException {
        try(Stream<Path> entries = Files.walk(rootDir)) {   //
            return entries.filter(Files::isRegularFile).collect(Collectors.toSet());   //
        }
    }

    public static long occurrences(String word, Path path) throws IOException {
        System.out.println('1');
        try(Scanner in = new Scanner(path)){
            int count = 0;
            while(in.hasNext()){
                if(in.next().equals(word)) count++;
            }
            return count;
        }
        catch(IOException ex){
            return 0;
        }
    }

    public static Callable<Path> searchForTask(String word, Path path){
        return ()->{
            try(final Scanner in = new Scanner(path)){
                while(in.hasNext()){
                    if(in.next().equals(word)) return path;
                    if(Thread.currentThread().isInterrupted()){
                        System.out.println("Search in "+path+" cancled.");
                        return null;
                    }
                }
                throw new NoSuchElementException();
            }
        };
    }

}
