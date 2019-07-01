package com.game.util;


import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.type.ArrayType;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
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

    private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    private static ObjectMapper mapper = new ObjectMapper();
    private static TypeFactory typeFactory = TypeFactory.defaultInstance();

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
    public static <T> T string2Object(String context, Class<T> clz){
        JavaType javaType = typeFactory.constructType(clz);
        try{
            return (T) mapper.readValue(context,javaType);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
    public static <T> T[] string2Array(String context, Class<T> clz){
        ArrayType type = ArrayType.construct(typeFactory.constructType(clz));
        try{
            return (T[]) mapper.readValue(context,type);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
