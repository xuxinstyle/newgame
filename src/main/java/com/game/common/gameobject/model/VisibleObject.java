package com.game.common.gameobject.model;

import com.game.common.gameobject.GameObject;
import com.game.common.gameobject.constant.ObjectType;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 17:12
 */
public abstract class VisibleObject extends GameObject {
    public VisibleObject(Long objectId){
        this.objectId = objectId;
    }
    public VisibleObject(){

    }
    /** 获取可视对象的类型*/
    public abstract ObjectType getObjectType();

    /**
     * 判断是否是统一目标类型
     * @param objectType
     * @return
     */
    public boolean isObjectType(ObjectType objectType){
        return getObjectType().equals(objectType);
    }

}
