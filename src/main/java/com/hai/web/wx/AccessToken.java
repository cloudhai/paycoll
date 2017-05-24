package com.hai.web.wx;


import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hai.web.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Created by cloud on 2017/2/24.
 */
public class AccessToken {
    private long expireTm =0L;
    private long duration = 360*19*1000;
    private String accessToken;
    private int retryCount = 0;
    private static AccessToken ourInstance = new AccessToken();
    public static AccessToken getInstance() {return ourInstance;}
    private AccessToken() {
    }

    Logger log = LoggerFactory.getLogger("wx");


    public String getAccessToken(){
        if(isAvailable()){
            return accessToken;
        }else{
            return getFromWx();
        }
    }

    private boolean isAvailable(){
        return expireTm>System.currentTimeMillis();
    }


    private synchronized  String getFromWx(){
        String url = WxConfig.WX_URL_ACCESS_TOKEN;
        String res = HttpUtils.doGet(url);
        JSONObject json = JSON.parseObject(res);
        String access = json.getString("access_token");
        if(!StringUtils.isEmpty(access)){
            this.accessToken = access;
            expireTm = System.currentTimeMillis()+duration;
            retryCount = 0;
        }else{
            log.error(res);
            if(retryCount<3){
                access = getFromWx();
                retryCount++;
            }
        }
        return access;
    }

    public static void main(String[] args) {
        AccessToken accessToken = AccessToken.getInstance();
        System.out.println(accessToken.getFromWx());
    }


}
