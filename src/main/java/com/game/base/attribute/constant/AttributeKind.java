package com.game.base.attribute.constant;

/**
 * @Author：xuxin
 * @Date: 2019/6/26 14:38
 */
public enum AttributeKind {
    /**
     * 一级属性
     */
    FIRST_ATTRIBUTE(1),
    /**
     * 二级属性
     */
    SECOND_ATTRIBUTE(2),
    /**
     * 其他属性
     */
    OTHER_ATTRIBUTE(3),
    ;
    private int kind;
    AttributeKind(int kind){
        this.kind = kind;
    }

    public int getKind() {
        return kind;
    }


}
