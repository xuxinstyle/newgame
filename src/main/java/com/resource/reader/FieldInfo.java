package com.resource.reader;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @Author：xuxin
 * @Date: 2019/6/10 12:12
 */
public class FieldInfo {
    /** 第几列*/
    public final int index;
    /** 资源类属性*/
    public final Field field;
    public FieldInfo(int index, Field field){
        /** 将字段设为可读*/
        ReflectionUtils.makeAccessible(field);
        this.index = index;
        this.field = field;
    }
}
