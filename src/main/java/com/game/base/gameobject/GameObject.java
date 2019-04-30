package com.game.base.gameobject;

/**
 * @Author：xuxin
 * @Date: 2019/4/28 17:28
 */
public abstract class GameObject {

    protected Long objectId;
    public abstract String gatName();
    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
