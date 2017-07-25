package com.hai.push;

import java.util.List;

/**
 * Created by cloud on 2017/6/2.
 */
public interface IPushClient {
    public String singlePush(String id,Payload payload);
    public String batchPush(List<String> ids, Payload payload);
    public String pushByTag(String tag,Payload payload);
    public String broadcast(Payload payload);
}
