package com.game.user.itemeffect.service;

import com.game.SpringContext;
import com.game.base.attribute.container.AbstractAttributeContainer;
import com.game.base.attribute.attributeid.MedicineAttributeId;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.event.LogoutEvent;
import com.game.role.player.model.Player;
import com.game.user.item.packet.SM_EffectEnd;
import com.game.user.item.resource.ItemResource;
import com.game.user.itemeffect.command.ItemExpireDelayCommand;
import com.game.user.itemeffect.entity.ItemEffectEnt;
import com.game.user.itemeffect.model.ItemEffectInfo;
import com.game.user.itemeffect.model.ItemEffectdetaiInfo;
import com.game.util.TimeUtil;
import com.socket.core.session.TSession;
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
        return itemEffectManager.getItemEffect(playerId);
    }
    @Override
    public ItemEffectEnt getItemEffectEntOrCreate(long playerId){
        return itemEffectManager.getItemEffectOrCreate(playerId);
    }

    @Override
    public Map<Integer, ItemExpireDelayCommand> getItemExpireDelayCommandMap(long playerId){
        return itemEffectManager.getItemDeprecateDelayCommandMap(playerId);
    }

    @Override
    public void putCommand(ItemExpireDelayCommand command){
        itemEffectManager.putCommand(command);
    }
    @Override
    public void removeDelayCommand(long playerId,int itemModelId){
        Map<Integer, ItemExpireDelayCommand> itemDelayCommandMap = getItemExpireDelayCommandMap(playerId);
        if(itemDelayCommandMap==null){
            return;
        }
        itemDelayCommandMap.remove(itemModelId);

    }

    @Override
    public void doLogoutAfter(LogoutEvent event) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(event.getAccountId());
        /**
         * 玩家没创角时
         */
        if(player==null){
            return;
        }
        /**
         * 取消命令
         */
        cancelCommand(player.getObjectId());
        /**
         * 移除map中的command
         */
        removeDelayCommandByPlayerId(player.getObjectId());
    }

    /**
     * TODO:等完善战斗之后需要抛战斗同步策略
     * @param accountId
     * @param itemModelId
     */
    @Override
    public void doItemExpire(String accountId, int itemModelId) {
        /**
         * 先判断是否达到失效时间 如果没有达到继续定时
         */
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(accountId);
        Player player = playerEnt.getPlayer();
        /**
         * 移除map中的command  并删除库中的信息
         */
        removeDelayCommand(player.getObjectId(),itemModelId);


        ItemEffectEnt itemEffectEnt = SpringContext.getItemEffectService().getItemEffectEnt(player.getObjectId());
        if(itemEffectEnt==null){
            return;
        }
        ItemEffectInfo itemEffectInfo = itemEffectEnt.getItemEffectInfo();
        if(itemEffectInfo==null){
            return;
        }
        Map<Integer, ItemEffectdetaiInfo> itemEffectdetaiInfoMap = itemEffectInfo.getItemEffectdetaiInfoMap();
        ItemEffectdetaiInfo itemEffectdetaiInfo = itemEffectdetaiInfoMap.get(itemModelId);

        /**
         * 达到失效时间，还原玩家属性
         */
        AbstractAttributeContainer attributeContainer = player.getAttributeContainer();
        attributeContainer.removeAndCompteAttribtues(MedicineAttributeId.getMedicineAttributeId(itemModelId));
        SpringContext.getPlayerSerivce().save(playerEnt);

        /**
         * 改变玩家身上效果的状态 FIXME: 这里还是不要删数据库信息，如果玩家频繁使用这个道具就会降低效率。
         */
        itemEffectdetaiInfo.setEffectiveStatus(false);
        SpringContext.getItemEffectService().save(itemEffectEnt);
        /**
         * 通知客户端
         */
        TSession tSession = SpringContext.getSessionManager().getAccountSessionMap().get(accountId);
        ItemResource itemResource = SpringContext.getItemService().getItemResource(itemModelId);
        String itemName = itemResource.getName();
        SM_EffectEnd sm = new SM_EffectEnd();
        sm.setItemName(itemName);
        tSession.sendPacket(sm);
    }

    public void removeDelayCommandByPlayerId(long playerId){
        itemEffectManager.removeDelayCommand(playerId);
    }

    /**
     * TODO:这里不确定,问一下
     * @param playerId
     */
    public void cancelCommand(long playerId){
        Map<Integer, ItemExpireDelayCommand> commandMap = itemEffectManager.getItemDeprecateDelayCommandMap(playerId);
        if(commandMap==null){
            return;
        }
        for(ItemExpireDelayCommand command:commandMap.values()){

            command.getFuture().cancel(false);
            command.cancel();
        }
    }

    /**
     * 玩家登录后更新道具过期命令
     * @param accountId
     */
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
        if(itemEffectEnt==null){
            return;
        }
        ItemEffectInfo itemEffectInfo = itemEffectEnt.getItemEffectInfo();
        if(itemEffectInfo==null){
            return;
        }
        Map<Integer, ItemEffectdetaiInfo> itemEffectdetaiInfoMap = itemEffectInfo.getItemEffectdetaiInfoMap();

        for(ItemEffectdetaiInfo itemEffectdetaiInfo:itemEffectdetaiInfoMap.values()){

            if(!itemEffectdetaiInfo.isEffectiveStatus()){
               return;
            }
            Map<Integer, ItemExpireDelayCommand> itemDeprecateDelayCommandMap =
                    getItemExpireDelayCommandMap(player.getObjectId());
            /**
             * 移除旧的command
             */
            if(itemDeprecateDelayCommandMap!=null){
                ItemExpireDelayCommand command = itemDeprecateDelayCommandMap.get(itemEffectdetaiInfo.getItemModelId());
                command.cancel();
                itemDeprecateDelayCommandMap.remove(itemEffectdetaiInfo.getItemModelId());
            }
            /**
             * 抛出新的command
             */
            long delay = Math.max(itemEffectdetaiInfo.getInvalidTime() - TimeUtil.now(), 0);

            ItemExpireDelayCommand itemExpireDelayCommand = new ItemExpireDelayCommand(
                    delay, accountId, itemEffectdetaiInfo.getItemModelId(), player.getObjectId());
            SpringContext.getAccountExecutorService().submit(itemExpireDelayCommand);
            putCommand(itemExpireDelayCommand);
        }
    }

}
