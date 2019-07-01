package com.game.base.attribute.util;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.ImmutableAttribute;
import com.game.base.attribute.container.ModelAttribute;
import com.game.base.attribute.constant.AttributeType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
    public static void collectAttribute(Map<String, ModelAttribute> target,
                                        Map<AttributeType, Attribute> result, List<AttributeId> excludeId) {
        /**
         * 把所有同类型的属性加起来
         */
        for(Map.Entry<String, ModelAttribute> entry:target.entrySet()){
            if(excludeId!=null && excludeId.contains(entry.getKey())){
                continue;
            }
            ModelAttribute modelAttribute = entry.getValue();
            if(modelAttribute !=null) {
                AttributeUtil.accumulateToMap(modelAttribute.getAttributeMap(), result);
            }
        }
    }

    /**
     * 强克隆map
     * @param cloneTarget
     * @param result
     */
    private void cloneAttributeMap(Map<AttributeType, Attribute> cloneTarget, Map<AttributeType, Attribute> result) {
        for(Attribute attribute:result.values()){
            attribute.setValue(0);
        }
        for (Attribute attr:cloneTarget.values()){
            Attribute attribute = result.get(attr.getAttributeType());
            if(attribute != null){
                attribute.setValue(attr.getValue());
            }else{
                result.put(attr.getAttributeType(), Attribute.valueOf(attr.getAttributeType(), attr.getValue()));
            }
        }
    }
    public static List<Attribute> computeImmutableAttribute(List<ImmutableAttribute> immutableAttributeList){
        List<Attribute> attributeList = new ArrayList<>();
        for(ImmutableAttribute immutableAttribute:immutableAttributeList){
            Attribute attribute = Attribute.valueOf(immutableAttribute.getAttributeType(), immutableAttribute.getValue());
            attributeList.add(attribute);
        }
        return attributeList;
    }
}
