package com.game.common.attribute.util;

import com.game.common.attribute.Attribute;
import com.game.common.attribute.AttributeId;
import com.game.common.attribute.AttributeSet;
import com.game.common.attribute.constant.AttributeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 21:45
 */
public class AttributeUtil {
    private static final Logger logger = LoggerFactory.getLogger(AttributeUtil.class);

    public static void accumulateToMap(List<Attribute> attrs, Map<AttributeType, Attribute> resultMap) {
        if(attrs == null){
            return;
        }
        for(Attribute attribute : attrs){
            if(attribute == null|| attribute.getAttributeType() == null){
                logger.error("属性数据错误[{}]",attribute);
                return;
            }
            Attribute attr = resultMap.get(attribute.getAttributeType());
            if(attr == null){
                resultMap.put(attribute.getAttributeType(), Attribute.valueOf(attribute.getAttributeType(),attribute.getValue()));
            }else{
                /**
                 * TODO:这里直接修改了 没有根据类型设置属性值
                 */
                attr.setValue(attr.getValue()+attribute.getValue());
            }
        }
    }

    public static void accumulateToMap(Map<AttributeType, Attribute> targetMap,Map<AttributeType, Attribute> resultMap){
        for(Attribute add :targetMap.values()){
            if(add==null){
                continue;
            }
            Attribute attribute = resultMap.get(add.getAttributeType());
            if(attribute != null){
                attribute.setValue(attribute.getValue()+add.getValue());
            }else {
                resultMap.put(add.getAttributeType(), Attribute.valueOf(add.getAttributeType(),add.getValue()));
            }
        }
    }

    /**
     * 将属性模块按类型累加起来
     * @param target
     * @param result
     * @param excludeId
     */
    public static void collectAttribute(Map<String, AttributeSet> target,
                                        Map<AttributeType, Attribute> result, List<AttributeId> excludeId) {
        /**
         * 把所有同类型的属性加起来
         */
        for(Map.Entry<String, AttributeSet> entry:target.entrySet()){
            if(excludeId!=null && excludeId.contains(entry.getKey())){
                continue;
            }
            AttributeSet attributeSet = entry.getValue();
            if(attributeSet!=null) {
                AttributeUtil.accumulateToMap(attributeSet.getAttributeMap(), result);
            }
        }
    }
}
