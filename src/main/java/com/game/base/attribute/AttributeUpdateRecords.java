package com.game.base.attribute;

import com.game.base.attribute.constant.AttributeType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 17:13
 */
public class AttributeUpdateRecords {
    /**
     * 改变的模块
     */
    private final AttributeId changeId;
    /**
     * 本次改变设计的属性类型
     */
    private final Set<AttributeType> types = new HashSet<>();
    /**
     * 本次改变被移除的属性
     */
    private Collection<Attribute> removedAttribute = null;

    public AttributeUpdateRecords(AttributeId changeId){
        this.changeId = changeId;
    }

    public void addAttrs(Collection<Attribute> attrs){
        if(!attrs.isEmpty()){
            for(Attribute attribute : attrs){
                addType(attribute.getAttributeType());
            }
        }
    }

    private void addType(AttributeType type) {
        if(type == null){
            return ;
        }
        types.add(type);
        AttributeType[] effectAttributes = type.getEffectAttributes();
        if(effectAttributes!=null){
            for (AttributeType t : effectAttributes){
                addType(t);
            }
        }
    }

    public AttributeId getChangeId() {
        return changeId;
    }

    public Set<AttributeType> getTypes() {
        return types;
    }

    public Collection<Attribute> getRemovedAttribute() {
        return removedAttribute;
    }

    public void setRemovedAttribute(Collection<Attribute> removedAttribute) {
        this.removedAttribute = removedAttribute;
    }
}
