package com.game.user.item.service;

import com.game.user.equip.resource.EquipResource;
import com.game.user.item.resource.ItemResource;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 11:48
 */
@Component
public class ItemManager {
    @Autowired
    private StorageManager storageManager;

    public ItemResource getItemResource(int id) {
        return storageManager.getResource(id, ItemResource.class);
    }

    public EquipResource getEquipResource(int modelId){
        return storageManager.getResource(modelId, EquipResource.class);
    }
}
