package com.game.user.item.command;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.AttributeContainer;
import com.game.base.executor.account.Impl.AbstractAccountCommand;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.user.item.model.MedicineEffect;
import com.game.user.item.packet.SM_EffectEnd;
import com.game.user.item.resource.ItemResource;
import com.game.user.itemeffect.entity.ItemEffectEnt;
import com.game.user.itemeffect.model.ItemEffectInfo;
import com.game.user.itemeffect.model.ItemEffectdetaiInfo;
import com.game.util.TimeUtil;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 21:47
 */
public class ItemDeprecatedCommand extends AbstractAccountCommand {
    /**
     * 药品道具id
     */
    private int itemModelId;

    /**
     * 角色id
     */
    private long playerId;
    private static final Logger logger  = LoggerFactory.getLogger(ItemDeprecatedCommand.class);
    public ItemDeprecatedCommand(String accountId, int itemModelId, long playerId) {
        super(accountId);
        this.itemModelId = itemModelId;
        this.playerId = playerId;
    }

    @Override
    public String getName() {
        return "ItemDeprecatedCommand";
    }

    @Override
    public void active() {
        /**
         * 先判断是否达到失效时间 如果没有达到继续定时
         */
        ItemEffectEnt itemEffectEnt = SpringContext.getItemEffectService().getItemEffectEnt(playerId);
        ItemEffectInfo itemEffectInfo = itemEffectEnt.getItemEffectInfo();
        Map<Integer, ItemEffectdetaiInfo> itemEffectdetaiInfoMap = itemEffectInfo.getItemEffectdetaiInfoMap();
        ItemEffectdetaiInfo itemEffectdetaiInfo = itemEffectdetaiInfoMap.get(itemModelId);
        if(TimeUtil.now()<itemEffectdetaiInfo.getInvalidTime()){
            long delay = itemEffectdetaiInfo.getInvalidTime()-TimeUtil.now();
            ItemDeprecatedDelayCommand command = new ItemDeprecatedDelayCommand(delay,getAccountId(),itemModelId,playerId);
            SpringContext.getCommonExecutorService().submit(command);
            return;
        }
        /**
         * 达到失效时间，还原玩家属性, 移除itemManager中的command
         */
        ItemResource itemResource = SpringContext.getItemService().getItemResource(itemModelId);
        MedicineEffect useEffect = (MedicineEffect)itemResource.getUseEffect();
        List<Attribute> addAttributeList = useEffect.getAddAttributeList();
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(getAccountId());
        AttributeContainer<Player> attributeContainer = playerEnt.getPlayer().getAttributeContainer();
        attributeContainer.removeAndCompute(addAttributeList);
        SpringContext.getPlayerSerivce().save(playerEnt);
        SpringContext.getItemService().removeDelayCommand(playerId,itemModelId);

        /**
         * 改变玩家身上效果的状态
         */
        itemEffectdetaiInfo.setEffective(false);
        SpringContext.getItemEffectService().save(itemEffectEnt);
        logger.info("玩家{}药品{}效果失效",getAccountId(),getItemModelId());
        /**
         * 通知客户端
         */
        TSession tSession = SpringContext.getSessionManager().getAccountSessionMap().get(getAccountId());
        String itemName = itemResource.getName();
        SM_EffectEnd sm = new SM_EffectEnd();
        sm.setItemName(itemName);
        tSession.sendPacket(sm);
        ;
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
