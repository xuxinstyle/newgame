package com.game.util;

/**
 * @Author：xuxin
 * @Date: 2019/6/4 22:24
 */
public class TimeUtil {

    /** 默认的字符串时间格式*/
    public static final String TIME_FORMAT_TEMPLATE = "yyyy-MM-dd HH:mm:ss";
    /** 带毫秒的字符串时间格式*/
    public static final String MILL_TIME_FORMAT_TEMPLATE = "yyyy-MM-dd HH:mm:ss.SS";
    /** 五分钟的毫秒数*/
    public static final long FIVE_MINUTES = 5 * 60 * 1000;
    public static long now(){
        return System.currentTimeMillis();
    }
}
