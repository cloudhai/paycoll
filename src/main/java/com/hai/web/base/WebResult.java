package com.hai.web.base;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cloud on 2017/2/24.
 */
public class WebResult implements Serializable {
    private static final long serialVersionUID = -4680696224476504878L;
    private String code;
    private String msg;
    private Map<String,Object> result = new HashMap<String,Object>();

    public WebResult(){}
    public WebResult(String code){this.code = code;}
    public WebResult(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public WebResult(String code,String msg,Object o){
        this.code=code;
        this.msg = msg;
        this.result.put("data",o);
    }
    public WebResult(String code,Object o){
        this.code=code;
        this.result.put("data",o);
    }
    public void putData(Object o){
        this.result.put("data",o);
    }



    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
