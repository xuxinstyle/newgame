package com.game.base.attribute;

import com.game.base.attribute.constant.AttributeType;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 15:31
 */
public class AttributeSet {
    /**
     * TODO:模块战力
     */
    private long battleScore;
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

    public long getBattleScore() {
        return battleScore;
    }

    public void setBattleScore(long battleScore) {
        this.battleScore = battleScore;
    }

    public Map<AttributeType, Attribute> getAttributeMap() {
        return attributeMap;
    }

    public void setAttributeMap(Map<AttributeType, Attribute> attributeMap) {
        this.attributeMap = attributeMap;
    }
}
