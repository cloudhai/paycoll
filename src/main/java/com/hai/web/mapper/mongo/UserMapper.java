package com.hai.web.mapper.mongo;

import com.hai.web.model.mongo.UserModel;
import com.hai.util.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;


/**
 * Created by cloud on 2017/5/6.
 */
@Repository
public class UserMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void saveUser(UserModel user){
        mongoTemplate.save(user);
    }
    public void updateUser(UserModel user){
        Query query = new Query();
        query.addCriteria(new Criteria("userId").is(user.getUserId()));
        Update update = new Update();
        update.set("nickName",user.getNickName());
        LogUtils.log.info(user.toString());
        mongoTemplate.updateFirst(query,update,UserModel.class);
    }

    public UserModel getUser(long userId){
        Query query = new Query();
        query.addCriteria(new Criteria("_id").is(userId));
        return mongoTemplate.findOne(query,UserModel.class);
    }

    public long getTotal(){
        Query query = new Query();
        return mongoTemplate.count(query,UserModel.class);
    }
}
