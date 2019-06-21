package com.game.user.item.service;

import com.game.user.equip.resource.EquipResource;
import com.game.user.item.command.ItemDeprecatedDelayCommand;
import com.game.user.item.resource.ItemResource;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 11:48
 */
@Component
public class ItemManager {
    @Autowired
    private StorageManager storageManager;
    /**
     * <角色id，<道具表id，道具延迟命令 >>
     */
    private Map<Long ,Map<Integer, ItemDeprecatedDelayCommand>> playerItemDeprecateCommand = new HashMap<>();

    public ItemResource getItemResource(int id) {
        return storageManager.getResource(id, ItemResource.class);
    }

    public EquipResource getEquipResource(int modelId){
        return storageManager.getResource(modelId, EquipResource.class);
    }
    public Map<Integer, ItemDeprecatedDelayCommand> getItemDeprecateDelayCommandMap(long playerId){
        return playerItemDeprecateCommand.get(playerId);
    }
    public void putCommand(ItemDeprecatedDelayCommand command){
        if(playerItemDeprecateCommand.get(command.getPlayerId())==null){
            Map<Integer, ItemDeprecatedDelayCommand> commandMap = new HashMap<>();
            commandMap.put(command.getItemModelId(),command);
            playerItemDeprecateCommand.put(command.getPlayerId(),commandMap);
            return;
        }
        Map<Integer, ItemDeprecatedDelayCommand> commandMap = playerItemDeprecateCommand.get(command.getPlayerId());
        /*if(commandMap.get(command.getItemModelId())==null){*/
        commandMap.put(command.getItemModelId(),command);
        /*}*/
        /*ItemDeprecatedDelayCommand itemDeprecatedDelayCommand = commandMap.get(command.getItemModelId());
        itemDeprecatedDelayCommand.setDelay(itemDeprecatedDelayCommand.getDelay()+);*/
    }
    public void removeItemDelayCommand(long playerId,int itemModelId){
        Map<Integer, ItemDeprecatedDelayCommand> itemDelayCommandMap = getItemDeprecateDelayCommandMap(playerId);
        if(itemDelayCommandMap==null){
            return;
        }
        itemDelayCommandMap.remove(itemModelId);

    }
}
