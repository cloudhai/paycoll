package com.hai.aop;

import com.hai.util.LogUtils;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cloud on 2017/6/26.
 */
@Aspect
@Component
public class LogMonitor {

    private Pattern p = Pattern.compile("execution\\((.*)\\(");

    @Pointcut(value="execution(* com.hai.web.controller.MvcTestController.* (..))")
    public void aspect(){};



    @Around("aspect()")
    public Object printlog(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        String name = pjp.toShortString();
        Matcher matcher = p.matcher(name);
        if(matcher.find()){
            name = matcher.group(1);
        }
        LogUtils.log.info("method:{} params:{}", name, args);
        try {
            Object res = pjp.proceed(args);
            LogUtils.log.info("reutn:{}", res.toString());
            return res;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String str = "execution(MvcTe 123 stController.getlist(..))";
//        String str = "7 218.207.217.233 80 HTTP 福建省 移动 08-11 10:52 0.037 whois ";
        Pattern pattern = Pattern.compile("execution\\((.*)\\(");
//        Pattern pattern = Pattern.compile("(\\d+\\.\\d+\\.\\d+\\.\\d+)\\s+(\\d+)");
        Matcher matcher = pattern.matcher(str);
        if(matcher.find()){
            System.out.println(matcher.group(1));
        }
    }
}
