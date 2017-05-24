package com.paycoll.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by cloud on 2017/3/22.
 */
public class LogTest {
    public static void main(String[] args) {
        ConcurrentSkipListMap<Double, List<String>> map = new ConcurrentSkipListMap<>();
        map.put(0.123, new ArrayList<String>() {{
            add("a");
            add("b");
        }});
        map.put(0.125,new ArrayList<String>(){{add("c");add("d");}});
        map.put(0.120,new ArrayList<String>(){{add("e");add("f");}});
        map.put(0.119,new ArrayList<String>(){{add("i");add("j");}});
        map.put(0.126,new ArrayList<String>(){{add("g");add("h");}});

//        System.out.println(map.ceilingEntry(0.124));
        System.out.println(map.lowerEntry(0.123));
        System.out.println(map.floorEntry(0.123));
//        System.out.println(map.firstEntry());
        System.out.println(LogTest.class.getName());
        System.out.println(LogTest.class.getSimpleName());
    }
}
