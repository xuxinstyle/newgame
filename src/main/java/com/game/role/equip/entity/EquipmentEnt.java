package com.game.role.equip.entity;

import com.db.AbstractEntity;
import com.game.role.equip.model.EquipmentInfo;
import com.game.util.JsonUtils;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @Author：xuxin
 * @Date: 2019/6/16 18:30
 */
@Entity(name = "equipment")
@Table(appliesTo = "equipment", comment = "装备栏信息")
public class EquipmentEnt extends AbstractEntity<Long> {
    /**
     * 角色唯一id
     */
    @Id
    @Column(columnDefinition = "bigint default 10000 comment '角色id'", nullable = false)
    private long playerId;

    @Lob
    @Column(columnDefinition = "blob comment '装备栏数据'")
    private byte[] equipmentData;

    @Transient
    private EquipmentInfo equipmentInfo;

    @Override
    public void doSerialize() {
        this.equipmentData =JsonUtils.object2Bytes(equipmentInfo);
    }

    @Override
    public void doDeserialize() {
        this.equipmentInfo = JsonUtils.bytes2Object(this.equipmentData, EquipmentInfo.class);
    }

    @Override
    public Long getId() {
        return playerId;
    }

    public EquipmentInfo getEquipmentInfo() {
        return equipmentInfo;
    }

    public void setEquipmentInfo(EquipmentInfo equipmentInfo) {
        this.equipmentInfo = equipmentInfo;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

}
