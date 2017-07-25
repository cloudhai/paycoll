package com.hai.stock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by cloud on 2017/7/15.
 */
public class StockContainer {
    private Map<String,OrderQueue> stocks = new ConcurrentHashMap<>();
}
