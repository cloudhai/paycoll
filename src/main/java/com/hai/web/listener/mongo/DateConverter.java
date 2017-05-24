package com.hai.web.listener.mongo;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cloud on 2017/5/13.
 */
public class DateConverter implements Converter<Date,String> {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss SSS");

    @Override
    public String convert(Date date) {
        return sdf.format(date);
    }

}