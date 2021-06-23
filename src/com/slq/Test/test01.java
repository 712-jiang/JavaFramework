package com.slq.Test;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 712
 * @function:
 * @create 2021/6/22 14:36
 */
public class test01 {
    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(123);
        list.add(234);
        list.add(345);
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        map.put(1,list);
    }
}
