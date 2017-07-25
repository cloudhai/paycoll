package com.hai.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hai.web.wx.msg.TextMsg;
import org.dom4j.*;
import org.dom4j.Element;

import javax.xml.bind.*;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/**
 * Created by cloud on 2017/2/24.
 */
public class XmlUtils {

    /**
     * 将xml转成json
     * @param xml
     * @return
     */
    public static JSONObject xmlToJson(String xml){
        try {
            Document document = DocumentHelper.parseText(xml);
            return elementToJson(document.getRootElement());
        } catch (DocumentException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将xml转成bean
     */
    public  static<T> T xmlToBean(String xml,Class<T> clazz){
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T)unmarshaller.unmarshal(new StringReader(xml));
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return t;
    }

    public static void main(String[] args) {
        String xml = "<xml><ToUserName><![CDATA[gh_cd77095ad934]]></ToUserName>\n" +
                "<FromUserName><![CDATA[omm-iv0HbOZliIKyiEg9tmKTMwk8]]></FromUserName>\n" +
                "<CreateTime>1488271455</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[你在]]></Content>\n" +
                "<MsgId>6392077228188794646</MsgId>\n" +
                "</xml>";
        TextMsg msg = xmlToBean(xml, TextMsg.class);
        System.out.println(msg.getToUserName());
        System.out.println(msg.getContent());
        TextMsg outMsg = new TextMsg();
        outMsg.setContent("wqvb");
        outMsg.setToUserName("ssss");
        outMsg.setFromUserName("ss");
        outMsg.setMsgType("text");
        System.out.println(beanToXml(outMsg,"UTF-8"));
    }


    /**
     * 将bean 转成xml
     * @param obj
     * @param encoding
     * @return
     */
    public static String beanToXml(Object obj,String encoding){
        if(encoding == null){
            encoding = "UTF-8";
        }
        String result = null;
        try {
            JAXBContext context = JAXBContext.newInstance(obj.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(marshaller.JAXB_FORMATTED_OUTPUT,true);
            marshaller.setProperty(marshaller.JAXB_ENCODING,encoding);
            marshaller.setProperty(marshaller.JAXB_FRAGMENT,true);
            StringWriter writer = new StringWriter();
            marshaller.marshal(obj,writer);
            result = writer.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static String beanToXml(Object obj){
        return beanToXml(obj,"UTF-8");
    }


    private static JSONObject elementToJson(Element element){
        JSONObject json = new JSONObject();
        List<Attribute> attrs = element.attributes();
        for(Attribute attr : attrs){
            json.put(attr.getName(),attr.getValue());
        }
        List<Element> elems = element.elements();
        if(!elems.isEmpty()){
            for(Element elem : elems){
                if(elem.attributes().isEmpty() && elem.elements().isEmpty()){
                    //没有子节点，没有属性
                    json.put(elem.getName(),elem.getTextTrim());
                }else{
                    if(!json.containsKey(elem.getName())){
                        json.put(elem.getName(),new JSONArray());
                    }
                    ((JSONArray)json.get(elem.getName())).add(elementToJson(element));
                }
            }
        }
        return json;
    }
}
