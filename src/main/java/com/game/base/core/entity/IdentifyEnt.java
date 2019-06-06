package com.game.base.core.entity;

import com.db.AbstractEntity;

/**
 * @Author：xuxin
 * @Date: 2019/6/5 18:33
 */
public class IdentifyEnt{
    /** 实体类型id */
    private int typeId;
    /** 从这个数字开始增加*/
    private long now;

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }
}
