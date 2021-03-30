package com.slq.MultiThread;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author qingqing
 * @function:
 * @create 2021-03-23-18:59
 */
public class TimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer();
//        timer.schedule(执行方法, 第一次执行时间, 间隔时间);
        Date date = new Date();
        LogTimer logTimer = new LogTimer();
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        String format = dateFormat.format(new Date());
        timer.schedule(logTimer, new Date(), 1000*10);
    }
}

//新建定时任务类
class LogTimer extends TimerTask{
    @Override
    //编写需要定时执行的任务
    public void run() {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        String format = dateFormat.format(new Date());
        System.out.println("在时刻："+ format + "触发日志定时保存！");
    }
}


