package com.game.role.player.service;

import com.game.role.player.resource.PlayerLevelResource;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author：xuxin
 * @Date: 2019/6/5 9:56
 */
public class PlayerManager {
    @Autowired
    private StorageManager storageManager;

    public PlayerLevelResource getResource(int level){
        return storageManager.getResource(level,PlayerLevelResource.class);
    }

}
