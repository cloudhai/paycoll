package com.hai.web.wx.msg;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cloud on 2017/7/25.
 * <xml><ToUserName><![CDATA[gh_01ee698a9d38]]></ToUserName>
 <FromUserName><![CDATA[oUA9luAj8U4XOzjHmeYXpKwFOdCM]]></FromUserName>
 <CreateTime>1500945256</CreateTime>
 <MsgType><![CDATA[event]]></MsgType>
 <Event><![CDATA[VIEW]]></Event>
 <EventKey><![CDATA[http://mp.weixin.qq.com/s/ijR296-QTekLpTnjI8zzUg]]></EventKey>
 <MenuId>433802900</MenuId>
 </xml>
 */
@XmlRootElement(name="xml")
public class WxInputMsg {
    private String ToUserName;
    private String FromUserName;
    private String CreateTime;
    private String MsgType;
    private String Event;
    private String EventKey;
    /**
     * 文本消息内容
     */
    private String Content;
    /**
     * 图片消息url
     */
    private String PicUrl;
    /**
     * 素材id
     */
    private String MediaId;
    private String MsgId;
    /**
     * 语音消息格式
     */
    private String Format;
    /**
     * 语音识别内容
     */
    private String Recognition;
    /**
     * 经度
     */
    private String Location_X;
    /**
     * 纬度
     */
    private String Location_Y;
    /**
     * 地图缩放比例
     */
    private String Scale;

    /**
     * 地理位置信息
     */

    private String Label;

    @XmlElement(name = "ToUserName")
    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }
    @XmlElement(name = "FromUserName")
    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }
    @XmlElement(name = "CreateTime")
    public String getCreateTime() {
        return CreateTime;
    }
    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }
    @XmlElement(name = "MsgType")
    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }
    @XmlElement(name = "Event")
    public String getEvent() {
        return Event;
    }

    public void setEvent(String event) {
        Event = event;
    }
    @XmlElement(name = "EventKey")
    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }
    @XmlElement(name = "Content")
    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
    @XmlElement(name = "PicUrl")
    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }
    @XmlElement(name = "MediaId")
    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }
    @XmlElement(name = "MsgId")
    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }
    @XmlElement(name = "Format")
    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }
    @XmlElement(name = "Recognition")
    public String getRecognition() {
        return Recognition;
    }

    public void setRecognition(String recognition) {
        Recognition = recognition;
    }
    @XmlElement(name = "Location_X")
    public String getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(String location_X) {
        Location_X = location_X;
    }
    @XmlElement(name = "Location_Y")
    public String getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(String location_Y) {
        Location_Y = location_Y;
    }
    @XmlElement(name = "Scale")
    public String getScale() {
        return Scale;
    }

    public void setScale(String scale) {
        Scale = scale;
    }
    @XmlElement(name = "Label")
    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    @Override
    public String toString() {
        return "WxInputMsg{" +
                "ToUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", MsgType='" + MsgType + '\'' +
                ", Event='" + Event + '\'' +
                ", EventKey='" + EventKey + '\'' +
                ", Content='" + Content + '\'' +
                ", PicUrl='" + PicUrl + '\'' +
                ", MediaId='" + MediaId + '\'' +
                ", MsgId='" + MsgId + '\'' +
                ", Format='" + Format + '\'' +
                ", Recognition='" + Recognition + '\'' +
                ", Location_X='" + Location_X + '\'' +
                ", Location_Y='" + Location_Y + '\'' +
                ", Scale='" + Scale + '\'' +
                ", Label='" + Label + '\'' +
                '}';
    }
}
