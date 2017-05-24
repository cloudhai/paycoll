package com.hai.web.base;

import com.alibaba.fastjson.JSON;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Created by cloud on 2017/2/24.
 */
public class GlobalExceptionResolver implements HandlerExceptionResolver{

    public ModelAndView resolveException(HttpServletRequest req, HttpServletResponse res, Object handler, Exception ex) {
        PrintWriter out = null;
        try{
            out = res.getWriter();
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json,charset='UTF-8'");
            res.setHeader("Cache-Control", "no-store");//or no-cache
            res.setHeader("Pragrma", "no-cache");
            res.setDateHeader("Expires", 0);
            out.write(JSON.toJSONString(new WebResult("100",ex.getMessage())));
            ex.printStackTrace();
        }catch (Exception e){

        }
        finally {
            if(out != null){
                out.flush();
                out.close();
            }
        }
        return null;
    }
}
