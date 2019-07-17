package com.game.scence.visible.packet.bean;

import com.game.base.gameobject.constant.ObjectType;
import com.game.scence.visible.model.Position;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 19:52
 */
public class VisibleVO {
    /**
     * 可视物唯一id
     */
    private long objectId;
    /**
     * 可视物类型
     */
    private ObjectType type;
    /**
     * 可视物的名字
     */
    private String visibleName;
    /**
     * 位置坐标
     */
    private Position position;
    /**
     * 当前血量
     */
    private long currHp;
    /**
     * 最大血量
     */
    private long maxHp;
    /**
     * 当前蓝量
     */
    private long currMp;

    /**
     * 最大蓝量
     */
    private long maxMp;

    public long getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(long maxHp) {
        this.maxHp = maxHp;
    }

    public long getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(long maxMp) {
        this.maxMp = maxMp;
    }

    public long getCurrHp() {
        return currHp;
    }

    public void setCurrHp(long currHp) {
        this.currHp = currHp;
    }

    public long getCurrMp() {
        return currMp;
    }

    public void setCurrMp(long currMp) {
        this.currMp = currMp;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public long getObjectId() {
        return objectId;
    }

    public void setObjectId(long objectId) {
        this.objectId = objectId;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
