package com.hai.web.wx.msg;

import java.io.Serializable;

/**
 * Created by cloud on 2017/2/24.
 */

public abstract class BaseMsg implements Serializable{
    public static final String MSG_TYPE_TEXT = "text";//文本消息
    public static final String MSG_TYPE_IMAGE = "image";//图片消息
    public static final String MSG_TYPE_VOICE = "voice";//声音消息
    public static final String MSG_TYPE_VIDEO = "video";//视频消息
    public static final String MSG_TYPE_LOCATION = "location";//位置消息
    public static final String MSG_TYPE_LINK = "link";//链接消息
    public static final String MSG_TYPE_EVENT = "event";//事件消息
    public static final String MSG_TYPE_SHORT_VIDEO = "shortvideo";//小视频消息
}
