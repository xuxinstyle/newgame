package com.game.base.attribute.attributeid;

/**
 * @Author：xuxin
 * @Date: 2019/6/25 16:46
 */
public enum AttributeIdEnum implements AttributeId {
    /**
     * 基础属性
     */
    BASE {
        @Override
        public String getName() {
            return "BASE";
        }
    },
    /**
     * 战斗时使用
     */
    PVP{
        @Override
        public String getName() {
            return "PVP";
        }
    },

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
