package com.game.scence.fight.model;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.gameobject.constant.ObjectType;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.visible.model.Position;

import java.util.List;
import java.util.Map;

/**
 * 战斗基础
 * @Author：xuxin
 * @Date: 2019/7/4 12:09
 */
public abstract class BaseUnit {
    /**
     * 唯一id
     */
    private long id;
    /**
     * 位置
     */
    private Position position;
    /**
     * 可视物名字
     */
    private String visibleName;

    /**
     * 可视物类型
     */
    private ObjectType type;

    /**
     * 所在的场景id
     */
    private int mapId;

    /**
     * 所在的场景
     */
    private AbstractScene scene;

    public AbstractScene getScene() {
        return scene;
    }

    public void setScene(AbstractScene scene) {
        this.scene = scene;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    public ObjectType getType() {
        return type;
    }

    public void setType(ObjectType type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
