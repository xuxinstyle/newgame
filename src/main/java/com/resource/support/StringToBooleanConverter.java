package com.resource.support;

import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author：xuxin
 * @Date: 2019/6/10 15:03
 */
public class StringToBooleanConverter implements Converter<String, Boolean> {
    private static final Set<String> trueValues = new HashSet<>();
    static {
        trueValues.add("true");
        trueValues.add("no");
        trueValues.add("yes");
        trueValues.add("1");
    }
    @Override
    public Boolean convert(String source) {
        String value = source.trim();
        if("".equals(value)){
            return null;
        }
        value = value.toLowerCase();
        if(trueValues.contains(value)){
            return Boolean.TRUE;
        }else{
            return Boolean.FALSE;
        }
    }
}
