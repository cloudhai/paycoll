package com.hai.web.model.mongo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hai.web.annotation.GeneratedValue;
import com.hai.web.model.BaseModel;
import com.hai.web.wx.msg.BaseMsg;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * Created by cloud on 2017/5/6.
 */
@Document(collection = "User")
public class UserModel extends BaseModel {
    private static final long serialVersionUID = -7624102091117062334L;
    @Id
    @GeneratedValue
    @Field("_id")
    private long userId;
    @Field("nickName")
    private String nickName;
    @Field("icon")
    private String icon;
    @Field("userType")
    private String userType;
    @Field("registTm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss SSS",timezone="GMT+8")
    private Date registTm;
    @Field("lastLogintm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss SSS",timezone="GMT+8")
    private Date lastLoginTm;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Date getRegistTm() {
        return registTm;
    }

    public void setRegistTm(Date registTm) {
        this.registTm = registTm;
    }

    public Date getLastLoginTm() {
        return lastLoginTm;
    }

    public void setLastLoginTm(Date lastLoginTm) {
        this.lastLoginTm = lastLoginTm;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", icon='" + icon + '\'' +
                ", userType='" + userType + '\'' +
                ", registTm=" + registTm +
                ", lastLoginTm=" + lastLoginTm +
                "} " + super.toString();
    }
}
