package com.resource.support;

import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author：xuxin
 * @Date: 2019/6/10 15:03
 */
public class StringToDateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String source) {
        SimpleDateFormat df  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            return df.parse(source);
        }catch (ParseException e){
            e.printStackTrace();
            throw new IllegalArgumentException("字符串["+source+"]不符合格式要求[yyyy-MM-dd HH:mm:ss]");
        }

    }
}
