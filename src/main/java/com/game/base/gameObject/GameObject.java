package com.game.base.gameObject;

/**
 * @Author：xuxin
 * @Date: 2019/4/28 17:28
 */
public abstract class GameObject {

    protected long objectId;
    public abstract String gatName();
    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
