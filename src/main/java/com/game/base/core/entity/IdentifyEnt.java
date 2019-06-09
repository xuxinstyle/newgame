package com.game.base.core.entity;

import com.db.AbstractEntity;
import org.hibernate.annotations.Table;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


/**
 * @Author：xuxin
 * @Date: 2019/6/5 18:33
 */
/*@Entity(name="identify")
@Table(appliesTo = "identify", comment = "唯一标识id")*/
public class IdentifyEnt{
    /** 实体类型id */
    /*@Id
    @Column(columnDefinition = "INT default 0 comment '类型Id', nullable = false")*/
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
