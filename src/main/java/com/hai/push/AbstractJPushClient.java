package com.hai.push;

import java.util.List;

/**
 * Created by cloud on 2017/6/2.
 */
public abstract class AbstractJPushClient implements IPushClient {

    private final static String appkey="";
    private final static String secket="";
    @Override
    public String singlePush(String id, Payload payload) {
        return null;
    }

    @Override
    public String batchPush(List<String> ids, Payload payload) {
        return null;
    }

    @Override
    public String pushByTag(String tag, Payload payload) {
        return null;
    }

    @Override
    public String broadcast(Payload payload) {
        return null;
    }
}
