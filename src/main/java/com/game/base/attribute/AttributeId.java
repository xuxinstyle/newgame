package com.game.base.attribute;

import org.codehaus.jackson.annotate.JsonTypeInfo;

import java.io.Serializable;


/**
 * @Author：xuxin
 * @Date: 2019/6/25 16:18
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public interface AttributeId extends Serializable {
    /**
     * 获取属性节点的名称
     */
    String getName();
}
