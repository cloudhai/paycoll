package com.hai.web.controller.wx;

import com.hai.web.base.WebResult;
import com.hai.util.LogUtils;
import com.hai.util.RandomUtils;
import com.hai.util.XmlUtils;
import com.hai.web.wx.JsApiTicket;
import com.hai.web.wx.MsgParser;
import com.hai.web.wx.WxUserInfo;
import com.hai.web.wx.WxUtils;
import com.hai.web.wx.msg.BaseMsg;
import com.hai.web.wx.msg.TextMsg;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by cloud on 2017/2/24.
 */
@Controller()
public class WxController {

    @RequestMapping(value = "/wx/message", method = RequestMethod.GET)
    @ResponseBody
    public String getMessage(
            @RequestParam(value = "signature") String signature,
            @RequestParam(value = "timestamp") String timestamp,
            @RequestParam(value = "nonce") String nonce,
            @RequestParam(value = "echostr") String echostr)
    {
        if(WxUtils.checkSignature(signature,timestamp,nonce)){
            return echostr;
        }else{
            return "";
        }
    }


    /**
     * 接受微信服务器的消息
     * @param signature
     * @param timestamp
     * @param nonce
     * @param xml
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/wx/message",method = RequestMethod.POST)
    public String fromWx(
            @RequestParam(value = "signature") String signature,
            @RequestParam(value = "timestamp") String timestamp,
            @RequestParam(value = "nonce") String nonce, @RequestBody String xml)
    {
        if(!WxUtils.checkSignature(signature,timestamp,nonce)){
            LogUtils.log.error("the msg is invalid");
            return "";
        }
        BaseMsg msg = MsgParser.msgParse(xml);
        if(msg instanceof TextMsg){
            TextMsg inMsg = (TextMsg) msg;
            TextMsg outMsg = new TextMsg();
            outMsg.setToUserName(inMsg.getFromUserName());
            outMsg.setFromUserName(inMsg.getToUserName());
            outMsg.setCreateTime(System.currentTimeMillis()+"");
            outMsg.setMsgType(BaseMsg.MSG_TYPE_TEXT);
            outMsg.setContent(inMsg.getContent());
            String res = XmlUtils.beanToXml(outMsg,"UTF-8");
            LogUtils.log.info(res);
            return res;
        }
        return "";
    }
    @RequestMapping(value = "/wx/auth")
    public String auth(@RequestParam(value="state")String state,
                       @RequestParam(value="code",required = false)String code,
                       HttpServletResponse response)
    {
        LogUtils.log.info("code:{}",code);
        WxUserInfo user = WxUtils.getUserInfo(code);
        if(user != null){
            LogUtils.log.info(user.toString());
            StringBuffer redirUrl = new StringBuffer(state);
            redirUrl.append("?token=").append(user.getAccessToken());
            redirUrl.append("&openId=").append((user.getOpenId()));
            return "redirect:"+redirUrl.toString();
        }
        return "redirect:/weixin.html";
    }

    @ResponseBody
    @RequestMapping(value="/wx/jssign",method = RequestMethod.GET)
    public WebResult getJsSign(@RequestParam(value="url")String url){
        SortedMap<String,String> map = new TreeMap<>();
        map.put("noncestr",RandomUtils.getRandomStr(10));
        map.put("timestamp",""+System.currentTimeMillis()/100);
        map.put("jsapi_ticket",JsApiTicket.getInstance().getJsApiTicket());
        map.put("url",url);
        map.put("signature",WxUtils.getSignature(map,false));
        WebResult result = new WebResult("1","success");
        result.getResult().put("data",map);
        return result;
    }


}
