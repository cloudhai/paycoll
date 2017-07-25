package com.hai.web.routing;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by cloud on 2017/6/5.
 */
public class RoutingToken {

    public static final String TOKEN_KP = "kp";
    public static final String TOKEN_XF = "bill";
    private static ThreadLocal<String> token = new ThreadLocal<>();
    private String defaultToken;
    public static String getToken(){
        String res = token.get();
        if(StringUtils.isEmpty(res)){
            return "kp";
        }
        return res;
    }

    public static void setToken(String t){
        token.set(t);
    }

    public static void clean(){
        token.remove();
    }
}
