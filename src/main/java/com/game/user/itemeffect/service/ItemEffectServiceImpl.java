package com.game.user.itemeffect.service;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.AttributeContainer;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.event.LogoutEvent;
import com.game.role.player.model.Player;
import com.game.user.item.model.MedicineEffect;
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

import java.util.List;
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
        ItemEffectInfo itemEffectInfo = itemEffectEnt.getItemEffectInfo();
        Map<Integer, ItemEffectdetaiInfo> itemEffectdetaiInfoMap = itemEffectInfo.getItemEffectdetaiInfoMap();
        ItemEffectdetaiInfo itemEffectdetaiInfo = itemEffectdetaiInfoMap.get(itemModelId);

        /**
         * 达到失效时间，还原玩家属性
         */
        ItemResource itemResource = SpringContext.getItemService().getItemResource(itemModelId);
        MedicineEffect useEffect = (MedicineEffect)itemResource.getUseEffect();
        List<Attribute> addAttributeList = useEffect.getAddAttributeList();

        AttributeContainer<Player> attributeContainer = player.getAttributeContainer();
        attributeContainer.removeAndCompute(addAttributeList);
        SpringContext.getPlayerSerivce().save(playerEnt);

        /**
         * 改变玩家身上效果的状态 FIXME: 这里还是不要删数据库信息，如果玩家频繁使用这个道具就会降低效率。
         */
        itemEffectdetaiInfo.setEffective(false);
        SpringContext.getItemEffectService().save(itemEffectEnt);
        /**
         * 通知客户端
         */
        TSession tSession = SpringContext.getSessionManager().getAccountSessionMap().get(accountId);
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
            /**
             * 这里传false，表示如果这个任务正在执行，则不会取消任务
             * true：表示如果这个任务正在执行，则这个任务将会取消
             */
            /**
             * command.getFuture().cancel(false);
             */
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
        Map<Integer, ItemEffectdetaiInfo> itemEffectdetaiInfoMap = itemEffectEnt.getItemEffectInfo().getItemEffectdetaiInfoMap();

        for(ItemEffectdetaiInfo itemEffectdetaiInfo:itemEffectdetaiInfoMap.values()){

            if(!itemEffectdetaiInfo.isEffective()){
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
