package com.game.role.equip.service;

import com.db.cache.EntityCacheService;
import com.game.role.equip.entity.EquipmentEnt;
import com.game.role.equip.resource.EquipResource;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/16 19:14
 */
@Component
public class EquipManager {
    @Autowired
    private StorageManager storageManager;
    @Autowired
    private EntityCacheService<Long, EquipmentEnt> entityCacheService;

    EquipResource getEquipResource(int id){
        return storageManager.getResource(id,EquipResource.class);
    }

    public void save(EquipmentEnt equipmentEnt) {
        entityCacheService.saveOrUpdate(equipmentEnt);
    }

    public void insert(EquipmentEnt equipmentEnt){
        entityCacheService.saveOrUpdate(equipmentEnt);
    }

    public EquipmentEnt getEquipmentEnt(long playerId){
        return entityCacheService.load(EquipmentEnt.class,playerId);
    }
}
