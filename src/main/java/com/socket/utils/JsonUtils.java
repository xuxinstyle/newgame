package com.socket.utils;


import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * @Author：xuxin
 * @Date: 2019/4/29 14:43
 * JSON工具类
 */

public class JsonUtils {
    /*private static ObjectMapper mapper;
    static Logger  logger = LoggerFactory.getLogger(JsonUtils.class);*/

    /*static {
        mapper = new ObjectMapper();
        // 转换为格式化的json
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
//        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }*/

    /**
     * 对象转二进制
     * */
    /*public static byte[] object2Bytes(Object object) {
        try {
            return mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            logger.error("对象转二进制:JsonProcessingException",e);
        }
        return null;
    }

    *//**
     * 二进制转对象
     * *//*
    public static<T> T bytes2Object(byte[] bytes,Class<T> clz) {
        try {
            return mapper.readValue(bytes,clz);
        } catch (IOException e) {
            logger.error("二进制转对象:JsonProcessingException",e);
        }
        return null;
    }
*/



    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_NULL_MAP_VALUES, false);
        mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
        mapper.configure(org.codehaus.jackson.map.SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.setSerializationConfig(mapper.getSerializationConfig().withDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
    }
    public static byte[] object2Bytes(Object object){
        try {
            if(object==null){
                return new byte[0];
            }
            return mapper.writeValueAsBytes(object);
        }catch (Exception e){
            logger.error("{}转换为数字时发生错误",object);
            e.printStackTrace();
            return null;
        }
    }
    public static <T> T bytes2Object(byte[] content, Class<T> clz){
        try {
            if(content==null){
                try {
                    clz.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            return mapper.readValue(content, clz);
        } catch (IOException e) {
            logger.error("数组转换为对象["+clz+"]时出错");
            e.printStackTrace();
        }
        return null;
    }

}