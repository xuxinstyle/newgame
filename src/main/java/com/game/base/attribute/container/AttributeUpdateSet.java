package com.game.base.attribute.container;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.constant.AttributeType;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * 属性更新集合
 * @Author：xuxin
 * @Date: 2019/6/25 17:13
 */
public class AttributeUpdateSet {
    /**
     * 改变的模块
     */
    private final AttributeId changeId;
    /**
     * 本次改变设计的属性类型
     */
    private final Set<AttributeType> types = new HashSet<>();


    public AttributeUpdateSet(AttributeId changeId){
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

    public Set<AttributeType> getTypes() {
        return types;
    }

    public AttributeId getChangeId() {
        return changeId;
    }
}
