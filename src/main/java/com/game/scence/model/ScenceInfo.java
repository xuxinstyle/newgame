package com.game.scence.model;

import com.game.base.gameObject.constant.EntityType;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:43
 */
public class ScenceInfo {
    /**
     * 当前场景中所有的实体<实体类型， 实体>
     */
    private Map<EntityType, Object> entityMap;

    public Object getScenceEntity(EntityType entityType){
        return entityMap.get(entityType);
    }

    public void putEntity(EntityType type, Object entity){
        entityMap.put(type, entity);
    }
    public Map<EntityType, Object> getEntityMap() {
        return entityMap;
    }

    public void setEntityMap(Map<EntityType, Object> entityMap) {
        this.entityMap = entityMap;
    }
}
