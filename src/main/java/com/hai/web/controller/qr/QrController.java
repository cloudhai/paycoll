package com.hai.web.controller.qr;

import com.hai.web.base.WebResult;
import com.hai.util.LogUtils;
import com.hai.util.SpringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by cloud on 2017/2/28.
 */
@Controller
public class QrController {
    @RequestMapping(value="/qr/scan",method = RequestMethod.GET)
    public void qrCall(HttpServletRequest request, HttpServletResponse response, @RequestParam("userId")String userId){
        String agent = request.getHeader("User-Agent").toLowerCase();
        if(agent.indexOf("micromessenger") >0){
            System.out.println("微信");
        }else if(agent.indexOf("alipayclient")>0){
            System.out.println("支付宝");
        }
        System.out.println(userId);
    }

    @RequestMapping(value="/spring",method = RequestMethod.GET)
    @ResponseBody
    public WebResult utilTest() throws SQLException {
        DataSource dataSource = SpringUtils.getBean("dataSource");
        Connection conn = dataSource.getConnection();
        PreparedStatement pst = conn.prepareStatement("select * from kp_users limit 5");
        ResultSet rst = pst.executeQuery();
        while(rst.next()){
            LogUtils.log.info(rst.getLong("user_id"));
        }
        return new WebResult("1");
    }
}
