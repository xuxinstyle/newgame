package com.game.base.attribute.util;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 22:10
 */
public class ComputerUtil {
    private static final Logger logger = LoggerFactory.getLogger(ComputerUtil.class);

    /**
     * 计算二级属性真实值
     *
     * @param accumulateAttributes
     * @param attr                 需要计算的属性，如果不是二级属性直接返回accumulateAttributes中的值
     */
    public static long computerReal(Map<AttributeType, Attribute> accumulateAttributes, Attribute attr) {
        long value = 0L;
        if (attr == null) {
            logger.error("传来的属性不能为空");
            return value;
        }
        AttributeType[] firstAttributes = attr.getAttributeType().getFirstAttributes();
        if (firstAttributes == null || firstAttributes.length == 0) {
            Attribute attribute = accumulateAttributes.get(attr.getAttributeType());
            if (attribute != null) {
                value += attribute.getValue();
            }
            return value;
        }
        for (AttributeType type : firstAttributes) {
            if (type == null) {
                continue;
            }
            Attribute attribute = accumulateAttributes.get(type);
            Map<AttributeType, Attribute> attributeMap = type.computeChangeAttribute(attribute.getValue());
            if (attributeMap == null) {
                continue;
            }
            Attribute changeAttr = attributeMap.get(attr.getAttributeType());
            if (changeAttr == null) {
                continue;
            }
            value += changeAttr.getValue();
        }
        Attribute attribute = accumulateAttributes.get(attr.getAttributeType());
        if (attribute != null) {
            value += attribute.getValue();
        }
        return value;
    }

}
