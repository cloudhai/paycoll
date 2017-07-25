package com.hai.web.wx;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hai.util.CryptionUtils;
import com.hai.util.HttpUtils;
import com.hai.util.LogUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by cloud on 2017/2/24.
 */
public class WxUtils {

    /**
     * 微信配置时验证
     * @param sign
     * @param
     * @return
     */
    public static boolean checkSignature(String sign, String timestamp,String nonce){
        SortedSet<String> set = new TreeSet<>();
        set.add(timestamp);
        set.add(WxConfig.TOKEN);
        set.add(nonce);
        StringBuffer sb = new StringBuffer();
        for(String str:set){
                sb.append(str);
        }
        return sign.equalsIgnoreCase(CryptionUtils.sha1Encrypt(sb.toString()));
    }


    /**
     * 微信生成签名
     * @param map
     * @return
     */
    public static String getSignature(SortedMap<String,String>map,boolean isKey){
        StringBuffer sb = new StringBuffer();
        for(Map.Entry<String,String> entry : map.entrySet()){
            if(StringUtils.isNotEmpty(entry.getValue())
                    && !"key".equals(entry.getKey())
                    &&!"sign".equals(entry.getKey())){
                sb.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        }
        String str = "";
        if(isKey){
            str = sb.append("key="+WxConfig.KEY).toString();
        }else{
            str = sb.substring(0,sb.length()-1);
        }
        return CryptionUtils.sha1Encrypt(str);
    }

    /**
     * 获取微信用户的OPENID
     * @param code
     * @return
     */
    public static WxUserInfo getUserInfo(String code){
        if(StringUtils.isEmpty(code)){
            return null;
        }
        String url = WxConfig.WX_URL_AUTH2+"&code="+code;
        String res = HttpUtils.doGet(url);
        JSONObject json = JSON.parseObject(res);
        LogUtils.log.info(json.toString());
        if(json.containsKey("errcode")){
            LogUtils.log.error("call wx auth error {}",json.getString("errmsg"));
            return null;
        }else{
            WxUserInfo user = new WxUserInfo();
            user.setOpenId(json.getString("openid"));
            user.setRefreshToken(json.getString("refresh_token"));
            user.setAccessToken(json.getString("access_token"));
            user.setExpiresIn(json.getInteger("expires_in"));
            user.setScope(json.getString("scope"));
            return user;
        }
    }

    public static void main(String[] args) {
        SortedMap<String,String> map = new TreeMap<>();
        map.put("noncestr","Wm3WZYTPz0wzccnW");
        map.put("jsapi_ticket","sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg");
        map.put("timestamp","1414587457");
        map.put("url","http://mp.weixin.qq.com?params=value");
        System.out.println(getSignature(map,false));
    }

}
