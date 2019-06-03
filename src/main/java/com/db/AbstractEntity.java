package com.db;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 15:42
 */
public abstract class AbstractEntity implements IEntity{
    @Override
    public void serialize() {
        doSerialize();
    }

    @Override
    public void unserialize() {
        doDeserialize();
    }
    /**
     * 序列化
     */
    public abstract void doSerialize();

    /**
     * 反序列化
     */
    public abstract void doDeserialize();
}
