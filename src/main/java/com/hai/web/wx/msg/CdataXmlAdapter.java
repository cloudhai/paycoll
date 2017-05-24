package com.hai.web.wx.msg;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by cloud on 2017/2/28.
 * 将bean转换成xml  增加<!CDATA[]]>
 */
public class CdataXmlAdapter extends XmlAdapter<String,String>{
    @Override
    public String unmarshal(String v) throws Exception {
        return v;
    }

    @Override
    public String marshal(String v) throws Exception {
        return "<![CDATA[" +v+ "]]>";
    }
}
