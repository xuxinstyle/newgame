package com.game.role.skill.packet;

import com.game.base.gameobject.constant.ObjectType;
import com.game.base.gameobject.model.Creature;
import com.game.scence.fight.model.CreatureUnit;

/**
 * 生物死亡
 * @Author：xuxin
 * @Date: 2019/7/10 21:54
 */
public class SM_CreatureDead {
    /**
     * mapId
     */
    private int mapId;
    /**
     * 类型
     */
    private ObjectType objectType;
    /**
     * 唯一标识id
     */
    private long objectId;
    /**
     * 生物名字
     */
    private String creatureUnitname;

    public static SM_CreatureDead valueOf(int mapId, CreatureUnit creatureUnit){
        SM_CreatureDead sm = new SM_CreatureDead();
        sm.setMapId(mapId);
        sm.setCreatureUnitname(creatureUnit.getVisibleName());
        sm.setObjectId(creatureUnit.getId());
        sm.setObjectType(creatureUnit.getType());
        return sm;
    }
    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
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

    public String getCreatureUnitname() {
        return creatureUnitname;
    }

    public void setCreatureUnitname(String creatureUnitname) {
        this.creatureUnitname = creatureUnitname;
    }
}
