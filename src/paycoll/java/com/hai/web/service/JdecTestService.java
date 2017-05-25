package com.hai.web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by cloud on 2017/2/23.
 */
@Service
public class JdecTestService {
    @Autowired
    private JdbcTemplate template;

    public JdecTestService(){
        String sql = "select count(0) from kp_users";

    }
}
