package com.hai.web.controller;

import com.alibaba.fastjson.support.spring.MappingFastJsonValue;
import com.hai.web.base.WebResult;
import com.hai.web.mapper.mysql.UserKpMapper;
import com.hai.web.mapper.mysql.bill.AssetsMapper;
import com.hai.web.model.AssetsEntity;
import com.hai.web.model.UserDAO;
import com.hai.web.model.mongo.UserModel;
import com.hai.web.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by cloud on 2017/2/23.
 */
@RestController
public class MvcTestController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserKpMapper userKpMapper;
    @Autowired
    private AssetsMapper assetsMapper;

    @RequestMapping(value="/test",method = RequestMethod.GET)
    public WebResult getList(@RequestParam("msg")String msg) throws Exception {
        return new WebResult("1");
    }
    @RequestMapping(value="/str",method = RequestMethod.POST)
    public String getString(@RequestBody String content, @RequestParam(value="type")String type){
        System.out.println("content:"+content);
        System.out.println("type:"+type);
        return "this is a test for mvc";
    }

    @RequestMapping(value="/int",method = RequestMethod.GET)
    public WebResult getInt(){
        return new WebResult("0");
    }

    @RequestMapping(value="/user/add")
    public WebResult addUser(HttpServletRequest request){
        String name = request.getParameter("name");
        if(StringUtils.isEmpty(name)){
            name = "cloud";
        }
        userService.saveUser(name,"u");
        return new WebResult("0");
    }

    @RequestMapping(value="/user/find")
    public WebResult findUser(HttpServletRequest request){
        String userId = request.getParameter("userId");
        long id = 0;
        if(StringUtils.isNotEmpty(userId)){
            id = Long.parseLong(userId);
        }
        UserModel user = userService.getUserByUserId(id);
        WebResult result = new WebResult("0");
        result.putData(user);
        result.getResult().put("total",userService.getUserCount());
        return result;
    }

    @RequestMapping(value="/user/{uid}")
    public WebResult getUser(@PathVariable("uid")long uid){
        WebResult result = new WebResult("0");
        UserDAO userDAO = userKpMapper.findUserByUserId(uid);
        AssetsEntity assets = assetsMapper.findAssetsByUid(uid);
        result.putData(userDAO);
        result.getResult().put("asset",assets);
        return result;
    }

    @RequestMapping(value="/jsonp")
    public MappingJacksonValue jsonp(@RequestParam("name")String name){
        WebResult result = new WebResult("0");
        result.putData(name);
        MappingJacksonValue json = new MappingJacksonValue(result);
        json.setJsonpFunction("callback");
        return json;
    }
}
