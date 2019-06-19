package com.game.user.equip.service;

import com.db.HibernateDao;
import com.game.user.equip.entity.EquipmentEnt;
import com.game.user.equip.resource.EquipResource;
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
    private HibernateDao hibernateDao;

    EquipResource getEquipResource(int id){
        return storageManager.getResource(id,EquipResource.class);
    }

    public void save(EquipmentEnt equipmentEnt) {
        hibernateDao.saveOrUpdate(EquipmentEnt.class, equipmentEnt);
    }

    public void insert(EquipmentEnt equipmentEnt){
        hibernateDao.save(EquipmentEnt.class,equipmentEnt);
    }

    public EquipmentEnt getEquipmentEnt(long playerId){
        return hibernateDao.find(EquipmentEnt.class, playerId);
    }
}
