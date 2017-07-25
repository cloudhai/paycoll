package com.hai.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by cloud on 2017/7/16.
 */
public class CuratorConfig {
    private static final int TIME_OUT = 30000;
    private static final String ZK_SERVER = "10.116.96.211:2181";
    private static CuratorFramework client = null;

    public synchronized  static CuratorFramework getClient(){
        if(client == null){
            RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000,3);
            client = CuratorFrameworkFactory.builder()
                    .connectString(ZK_SERVER)
                    .retryPolicy(retryPolicy)
                    .sessionTimeoutMs(6000)
                    .connectionTimeoutMs(3000)
                    .namespace("cloud")
                    .build();
            client.start();
        }
        return client;
    }

    public static void close(){
        if(client != null){
            client.close();
        }
    }
}
