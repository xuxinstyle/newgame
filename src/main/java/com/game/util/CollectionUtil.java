package com.game.util;

import java.util.Collection;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 17:28
 */
public class CollectionUtil {
    public static boolean isBlank(Collection c){
        return c == null || c.size() ==0;
    }

    public static boolean isBlank(Map c){
        return c == null || c.size() ==0;
    }
}
