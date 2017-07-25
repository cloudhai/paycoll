package com.hai.web.wx;

import com.hai.util.XmlUtils;
import com.hai.web.wx.msg.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;

/**
 * Created by cloud on 2017/2/26.
 */
public class MsgParser {
    public static BaseMsg msgParse(String xml){
        System.out.println("wx msg:"+xml);
        try {
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            List<Element> elems = root.elements();
            for(Element elem : elems){
                String name = elem.getName();
                if("MsgType".equals(name)){
                    switch(elem.getTextTrim()){
                        case BaseMsg.MSG_TYPE_TEXT:
                            return XmlUtils.xmlToBean(xml,TextMsg.class);
                        case BaseMsg.MSG_TYPE_IMAGE:
                            return XmlUtils.xmlToBean(xml,ImageMsg.class);
                        case BaseMsg.MSG_TYPE_VIDEO:
                            return XmlUtils.xmlToBean(xml,VideoMsg.class);
                        case BaseMsg.MSG_TYPE_VOICE:
                            return XmlUtils.xmlToBean(xml,VoiceMsg.class);
                        case BaseMsg.MSG_TYPE_LOCATION:
                            return XmlUtils.xmlToBean(xml,LocationMsg.class);
                        case BaseMsg.MSG_TYPE_LINK:
                            return XmlUtils.xmlToBean(xml,LinkMsg.class);
                        case BaseMsg.MSG_TYPE_SHORT_VIDEO:
                            return XmlUtils.xmlToBean(xml,ShortVideoMsg.class);
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }
}
