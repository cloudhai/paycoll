package com.hai.web.mapper.mysql;

import com.hai.web.model.UserDAO;
import org.apache.ibatis.annotations.Param;

/**
 * Created by cloud on 2017/6/5.
 */
public interface UserKpMapper {

    public UserDAO findUserByUserId(@Param("uid")long uid);
}
