package com.game.role.equipstren.service;

import com.game.role.equipstren.resource.EquipStrenResource;
import com.resource.anno.Init;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/1 21:43
 */
@Component
public class EquipStrenManager {
    @Autowired
    private StorageManager storageManager;

    /**
     * <装备位置,<装备品质，<强化等级, resource>>>
     */
    @Init("init")
    private Map<Integer,Map<Integer,Map<Integer,EquipStrenResource>>> positionQualityLevelMap;

    public void init(){
        Collection<EquipStrenResource> resourceAll =
                (Collection<EquipStrenResource>)storageManager.getResourceAll(EquipStrenResource.class);
        Map<Integer,Map<Integer,Map<Integer,EquipStrenResource>>>  positionQualityLevelMap= new HashMap<>();
        for(EquipStrenResource resource:resourceAll){
            Map<Integer, Map<Integer, EquipStrenResource>> qualityLevelMap = positionQualityLevelMap.get(resource.getPosition());

            if(qualityLevelMap==null){
                qualityLevelMap = new HashMap<>();
                positionQualityLevelMap.put(resource.getPosition(),qualityLevelMap);
                Map<Integer, EquipStrenResource> levelMap = new HashMap<>();
                qualityLevelMap.put(resource.getQuality(),levelMap);
                levelMap.put(resource.getLevel(),resource);
            }

            Map<Integer, EquipStrenResource> levelMap = qualityLevelMap.get(resource.getQuality());
            if(levelMap==null){
                levelMap = new HashMap<>();
                qualityLevelMap.put(resource.getQuality(),levelMap);
            }
            levelMap.put(resource.getLevel(),resource);
        }
        this.positionQualityLevelMap = positionQualityLevelMap;
    }

    public EquipStrenResource getEquipStrenResource(int position,int quality,int level){
        Map<Integer, Map<Integer, EquipStrenResource>> qualityLevelMap = positionQualityLevelMap.get(position);
        if(qualityLevelMap==null){
            return null;
        }
        Map<Integer, EquipStrenResource> levelMap = qualityLevelMap.get(quality);
        if(levelMap==null){
            return null;
        }
        return levelMap.get(level);
    }
}
