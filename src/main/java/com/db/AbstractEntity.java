package com.db;

import java.io.Serializable;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 15:42
 */
public abstract class AbstractEntity<T extends Comparable<T> & Serializable> implements IEntity<T>{
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
