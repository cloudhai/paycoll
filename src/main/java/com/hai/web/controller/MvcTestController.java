package com.hai.web.controller;

import com.hai.web.base.WebResult;
import com.hai.web.model.TestModel;
import com.hai.web.model.mongo.UserModel;
import com.hai.web.service.kafka.KfkProducer;
import com.hai.web.service.user.UserService;
import com.hai.web.util.LogUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloud on 2017/2/23.
 */
@RestController
public class MvcTestController {

    @Autowired
    private UserService userService;

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
}
