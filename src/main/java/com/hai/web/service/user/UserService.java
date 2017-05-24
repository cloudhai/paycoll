package com.hai.web.service.user;

import com.hai.web.mapper.mongo.UserMapper;
import com.hai.web.model.mongo.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by cloud on 2017/5/6.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public void saveUser(String nickName,String userType){
        UserModel user =  new UserModel();
        user.setNickName(nickName);
        user.setUserType(userType);
        user.setIcon("icon");
        user.setRegistTm(new Date());
        user.setLastLoginTm(new Date());
        userMapper.saveUser(user);
//        user = null;
    }

    public UserModel getUserByUserId(long userId){
        return userMapper.getUser(userId);
    }

    public void updateUser(UserModel user){
        userMapper.updateUser(user);
    }

    public long getUserCount(){
        return userMapper.getTotal();
    }

}
