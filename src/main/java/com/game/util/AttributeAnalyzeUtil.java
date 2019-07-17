package com.game.util;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/11 19:57
 */
public class AttributeAnalyzeUtil {
    /**
     * 解析属性字符串
     *
     * @param attrsStr
     * @return
     */
    public static List<Attribute> analyzeAttr(String attrsStr) {
        List<Attribute> attributes = new ArrayList<>();
        String[] split = attrsStr.split(StringUtil.FEN_HAO);
        for (String attr : split) {
            String[] str = attr.split(StringUtil.DOU_HAO);
            AttributeType type = AttributeType.valueOf(str[0]);
            long value = Long.parseLong(str[1]);
            attributes.add(Attribute.valueOf(type, value));
        }
        return attributes;
    }
}
