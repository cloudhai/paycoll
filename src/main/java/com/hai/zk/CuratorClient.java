package com.hai.zk;

import com.mysql.jdbc.TimeUtil;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.TimeUnit;

/**
 * Created by cloud on 2017/7/16.
 */
public class CuratorClient {


    public void createPersistentPath(String path,String data){
        CuratorFramework client = CuratorConfig.getClient();
        try {

            if(data == null){
                client.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(path);
            }else{
                client.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT)
                        .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                        .forPath(path,data.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createPersistentPath(String path){
        createPersistentPath(path,null);
    }


    public void createEphemeralPath(String path){
        CuratorFramework client = CuratorConfig.getClient();
        try {
            client.create().creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
                    .forPath(path);
        } catch (Exception e) {

        }
    }

    public void setData(String path,String data){
        CuratorFramework client = CuratorConfig.getClient();
        try {
            client.setData().inBackground().forPath(path,data.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getData(String path){
        CuratorFramework client = CuratorConfig.getClient();
        try {
            byte[] bytes = client.getData().forPath(path);
            if(bytes != null){
                return new String(bytes);
            }else{
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkExists(String path) throws  Exception{
        CuratorFramework client = CuratorConfig.getClient();
        Stat stat = client.checkExists().forPath(path);
        if(stat == null){
            return false;
        }else {
            return true;
        }
    }

    public void deletePath(String path){
        CuratorFramework client = CuratorConfig.getClient();
        try {
            client.delete().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCacheListener(String path){
        CuratorFramework client = CuratorConfig.getClient();
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client,path,true);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
                System.out.println("listener get event");
                System.out.println("new data:"+new String(event.getData().getData()));
            }
        });
        try {
            pathChildrenCache.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CuratorClient client = new CuratorClient();
        try {
            client.setCacheListener("/test/curator");
            if(client.checkExists("/test/curator")){
                client.setData("/test/curator","data for test");
                TimeUnit.SECONDS.sleep(2);
                client.setData("/test/curator","second");
                TimeUnit.SECONDS.sleep(2);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
