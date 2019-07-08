package com.game.base.attribute.container;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.constant.AttributeType;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Author：xuxin
 * @Date: 2019/6/25 15:31
 */
public class ModelAttribute {
    private AttributeId attributeId;

    /**
     * 模块属性
     */
    private Map<AttributeType, Attribute> attributeMap = new HashMap<>();

    /**
     * 获取属性
     * @param type
     *          目标类型
     * @return  属性值，默认返回0
     *
     */
    public long getAttribute(AttributeType type){
        Attribute attribute = attributeMap.get(type);
        return attribute == null ? 0L : attribute.getValue();
    }

    public AttributeId getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(AttributeId attributeId) {
        this.attributeId = attributeId;
    }

    public Map<AttributeType, Attribute> getAttributeMap() {
        return attributeMap;
    }


    public void setAttributeMap(Map<AttributeType, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
    }
}
