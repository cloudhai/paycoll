package com.hai.zk;

import com.hai.util.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;

/**
 * Created by cloud on 2017/6/2.
 */
public class ZkConfig {
    private static final int TIME_OUT = 30000;
    private static final String ZK_SERVER = "10.116.96.211:2181";
    private ZooKeeper zk;

    public void connect() {
        try {
            zk = new ZooKeeper(ZK_SERVER, TIME_OUT, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    LogUtils.log.info("process:"+watchedEvent.getType());
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if(zk != null){
            try {
                zk.close();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean createPersistentNode(String path,String data){
        String result = "";
        try {
            result = zk.create(path,data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(StringUtils.isNotEmpty(result)){
            return true;
        }else{
            return false;
        }
    }

    public String getData(String path){
        try {
            byte[] res = zk.getData(path,null,null);
            return new String(res);
        } catch (Exception e) {
            return "";
        }
    }

    public void setData(String path,String data){
        try {
            zk.setData(path,data.getBytes(),-1);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void exists(String path){
        try {
            Stat stat = zk.exists(path,false);

        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ZkConfig config = new ZkConfig();
        config.connect();
    }
}
