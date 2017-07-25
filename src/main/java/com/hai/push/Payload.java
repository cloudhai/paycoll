package com.hai.push;

import java.util.Map;

/**推送消息内容
 * Created by cloud on 2017/6/2.
 */
public class Payload {
    //消息类型，1通知，2透传
    private byte type;
    private String title;
    private String content;
    private Map<String,String> extra;


    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getExtra() {
        return extra;
    }

    public void setExtra(Map<String, String> extra) {
        this.extra = extra;
    }
}
