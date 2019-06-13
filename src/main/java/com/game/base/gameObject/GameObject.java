package com.game.base.gameObject;

/**
 * 整个游戏中的唯一id
 * @Author：xuxin
 * @Date: 2019/4/28 17:28
 */
public abstract class GameObject {
    /**
     * 这个id是整个游戏
     */
    protected long objectId;

    public abstract String getName();

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }
}
