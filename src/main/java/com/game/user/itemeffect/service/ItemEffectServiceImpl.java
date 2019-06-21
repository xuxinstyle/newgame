package com.game.user.itemeffect.service;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.user.item.command.ItemDeprecatedCommand;
import com.game.user.item.command.ItemDeprecatedDelayCommand;
import com.game.user.itemeffect.entity.ItemEffectEnt;
import com.game.user.itemeffect.model.ItemEffectdetaiInfo;
import com.game.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 11:59
 */
@Component
public class ItemEffectServiceImpl implements ItemEffectService {
    @Autowired
    private ItemEffectManager itemEffectManager;

    @Override
    public void save(ItemEffectEnt itemEffectEnt) {
        itemEffectManager.saveItemEffect(itemEffectEnt);
    }

    @Override
    public ItemEffectEnt getItemEffectEnt(long playerId) {
        return itemEffectManager.getItemEffectOrCreate(playerId);
    }

    @Override
    public void doLoginAfter(String accountId) {

        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        /**
         * 可能还没创角
         */
        if(player==null){
            return;
        }
        /**
         * 这里如果玩家没有延期的信息，也会在表中存一条信息
         */
        ItemEffectEnt itemEffectEnt = getItemEffectEnt(player.getObjectId());
        Map<Integer, ItemEffectdetaiInfo> itemEffectdetaiInfoMap = itemEffectEnt.getItemEffectInfo().getItemEffectdetaiInfoMap();
        for(ItemEffectdetaiInfo itemEffectdetaiInfo:itemEffectdetaiInfoMap.values()){
            if(itemEffectdetaiInfo.isEffective()){
                if(itemEffectdetaiInfo.getInvalidTime()>=TimeUtil.now()) {
                    Map<Integer, ItemDeprecatedDelayCommand> itemDeprecateDelayCommandMap =
                            SpringContext.getItemService().getItemDeprecateDelayCommandMap(player.getObjectId());
                    /**
                     * 如果itemManager中没有则重新定时，并将命令放到itemManager中
                     */
                    if (itemDeprecateDelayCommandMap == null || itemDeprecateDelayCommandMap.get(itemEffectdetaiInfo) == null) {
                        ItemDeprecatedDelayCommand itemDeprecatedDelayCommand = new ItemDeprecatedDelayCommand(
                                itemEffectdetaiInfo.getInvalidTime() - TimeUtil.now(), accountId,
                                itemEffectdetaiInfo.getItemModelId(), player.getObjectId());
                        SpringContext.getCommonExecutorService().submit(itemDeprecatedDelayCommand);
                        SpringContext.getItemService().putCommand(itemDeprecatedDelayCommand);
                        continue;
                    }
                }else{
                    ItemDeprecatedCommand command = new ItemDeprecatedCommand(accountId,itemEffectdetaiInfo.getItemModelId(), player.getObjectId());
                    SpringContext.getAccountExecutorService().submit(command);
                }
            }
        }
    }

}
