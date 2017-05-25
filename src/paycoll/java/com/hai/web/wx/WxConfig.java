package com.hai.web.wx;

/**
 * Created by cloud on 2017/2/24.
 */
public class WxConfig {

    private final static String appid="wxc14159166c3aa587";
    private final static String appsecred="0f5f77eb21eb507c944bfebe13ed0bd9";
    public final static String TOKEN = "PAYCOLL";
    public final static String KEY = "KEY";


    public static String WX_URL_ACCESS_TOKEN = String.format("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s"
            ,appid,appsecred);
    public final static String WX_URL_AUTH2 = String.format("https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&grant_type=authorization_code"
            ,appid,appsecred);
    public final static String WX_URL_JS_API_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";

}
