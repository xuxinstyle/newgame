package com.game.base.core.entity;

import com.db.AbstractEntity;

/**
 * @Author：xuxin
 * @Date: 2019/6/5 18:33
 */
public class IdentifyEnt extends AbstractEntity<Long> {
    /** 实体的唯一标识Id */
    private long identifyId;
    /** 实体类型id */
    private int typeId;

    public long getIdentifyId() {
        return identifyId;
    }

    public void setIdentifyId(long identifyId) {
        this.identifyId = identifyId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    @Override
    public void doSerialize() {

    }

    @Override
    public void doDeserialize() {

    }

    @Override
    public Long getId() {
        return identifyId;
    }
}
