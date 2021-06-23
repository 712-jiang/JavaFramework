package com.slq.MultiThread.UnsafeTest;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author 712
 * @function:  Unsafe类测试
 * @create 2021/6/15 17:26
 */
public class TestUnSafe {
    static Unsafe unsafe;
    static long stateOffset = 0;
    private volatile long state=0;

    static {
        try {
            //使用反射获取Unsafe的成员变量theUnsafe
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            //设置为可取
            field.setAccessible(true);
            //获得Unsafe类型实例
            unsafe = (Unsafe) field.get(null);
            //获得state变量在TestUnsafe中的偏移量-在栈中的指针偏移量
            stateOffset = unsafe.objectFieldOffset(TestUnSafe.class.getDeclaredField("state"));

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        TestUnSafe test = new TestUnSafe();

        //将state的值从0变为1
        boolean success = unsafe.compareAndSwapInt(test, stateOffset, 0, 1);

        System.out.println(success);
    }
}
