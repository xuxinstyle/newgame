package com.game.base.gameobject.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 用来定义游戏世界中所有的对象类型
 * @Author：xuxin
 * @Date: 2019/6/3 11:27
 */
public enum ObjectType {
    /**
     * 玩家角色
     */
    PLAYER(1,"玩家"),
    /**
     * NPC
     */
    NPC(2,"NPC"),
    /**
     * 怪物
     */
    MONSTER(3,"怪物"),
    /**
     * 道具
     */
    ITEM(4,"道具"),
    ;

    private int typeId;

    private String objectName;

    static Map<Integer ,ObjectType> objectMap = new HashMap<>();
    static {
        for(ObjectType type:ObjectType.values()){
            objectMap.put(type.getTypeId(),type);
        }
    }
    ObjectType(int objectId,String objectName){
        this.typeId = objectId;
        this.objectName = objectName;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getTypeId() {
        return typeId;
    }

    public static ObjectType getObjectType(int objectId){
        return objectMap.get(objectId);
    }
    public static Map<Integer, ObjectType> getObjectMap() {
        return objectMap;
    }

    public static void setObjectMap(Map<Integer, ObjectType> objectMap) {
        ObjectType.objectMap = objectMap;
    }
}
