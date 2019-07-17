package com.game.role.skilleffect.packet;

import com.game.base.gameobject.constant.ObjectType;
import com.game.scence.fight.model.CreatureUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/15 18:02
 */
public class SM_CreatureHurt {
    /**
     * 受伤害的Object类型
     */
    private ObjectType objectType;
    /**
     * 唯一id
     */
    private long objectId;
    /**
     * 名称
     */
    private String name;
    /**
     * 伤害值
     */
    private long hurtValue;

    public static SM_CreatureHurt valueOf(CreatureUnit unit, long hurtValue) {
        SM_CreatureHurt sm = new SM_CreatureHurt();
        sm.setObjectType(unit.getType());
        sm.setObjectId(unit.getId());
        sm.setName(unit.getVisibleName());
        sm.setHurtValue(hurtValue);
        return sm;
    }

    public long getHurtValue() {
        return hurtValue;
    }

    public void setHurtValue(long hurtValue) {
        this.hurtValue = hurtValue;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
