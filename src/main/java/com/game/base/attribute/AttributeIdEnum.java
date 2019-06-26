package com.game.base.attribute;

import java.io.Serializable;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 16:46
 */
public enum AttributeIdEnum implements AttributeId {
    /**
     * 等级成长属性
     */
    LEVEL{
        @Override
        public String getName() {
            return "LEVEL";
        }
    },
    /**
     * 装备属性
     */
    EQUIP{
        @Override
        public String getName() {
            return "EQUIP";
        }
    }
    ;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}
