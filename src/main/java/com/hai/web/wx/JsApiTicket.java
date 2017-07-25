package com.hai.web.wx;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hai.util.HttpUtils;
import com.hai.util.LogUtils;

/**
 * Created by cloud on 2017/3/1.
 */
public class JsApiTicket {
    private long expireTm = 0L;
    private long duration = 360*19*1000;
    private String ticket;
    private int retryCount = 0;
    private static JsApiTicket ourInstance = new JsApiTicket();
    public static JsApiTicket getInstance() {return ourInstance;}
    private JsApiTicket() {
    }

    public String getJsApiTicket(){
        if(isAvailable()){
            return ticket;
        }else{
            return getFromWx();
        }
    }

    private boolean isAvailable(){
        return expireTm>System.currentTimeMillis();
    }


    private synchronized  String getFromWx(){
        String url = String.format(WxConfig.WX_URL_JS_API_TICKET,AccessToken.getInstance().getAccessToken());
        String res = HttpUtils.doGet(url);
        JSONObject json = JSON.parseObject(res);
        String access = json.getString("ticket");
        if(!StringUtils.isEmpty(access)){
            this.ticket = access;
            expireTm = System.currentTimeMillis()+duration;
            retryCount = 0;
        }else{
            LogUtils.log.error(res);
            if(retryCount<3){
                access = getFromWx();
                retryCount++;
            }
        }
        return access;
    }
}
