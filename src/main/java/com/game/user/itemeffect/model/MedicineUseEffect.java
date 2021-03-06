package com.game.user.itemeffect.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.MedicineAttributeId;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.AbstractAttributeContainer;
import com.game.role.battlescore.event.ChangeAttrbuteEvent;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.scence.fight.command.AddAttributeBuffSynCommand;
import com.game.user.itemeffect.command.ItemExpireDelayCommand;
import com.game.user.itemeffect.entity.ItemEffectEnt;
import com.game.util.ParamUtil;
import com.game.util.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 14:19
 */
public class MedicineUseEffect extends AbstractUseEffect {
    private static final Logger logger = LoggerFactory.getLogger(MedicineUseEffect.class);
    /**
     * 增加的属性
     */
    private List<Attribute> addAttributeList = new ArrayList<>();
    /**
     * 道具表id
     */
    private int itemModelId;

    @Override
    public void init(String effect, Map<String, Object> param) {
        String[] split = effect.split(":");
        for (String str : split[0].split(";")) {
            if (str == null || "".equals(str)) {
                continue;
            }
            String[] attr = str.split(",");
            AttributeType attributeType = AttributeType.valueOf(attr[0]);
            Attribute attribute = Attribute.valueOf(attributeType, Long.parseLong(attr[1]));
            this.addAttributeList.add(attribute);
        }
        /**
         * 这个地方配置表中配置的是
         */
        this.effectiveTime = Long.parseLong(split[1]) * 1000 * 60;
        Object itemModelId = param.get(ParamUtil.ITEM_MODELID);
        this.itemModelId = (int) itemModelId;
    }

    /**
     *
     */
    @Override
    public void active(String acountId, int num) {
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(acountId);
        Player player = playerEnt.getPlayer();

        ItemEffectEnt itemEffectEnt = SpringContext.getItemEffectService().getItemEffectEntOrCreate(player.getObjectId());
        ItemEffectInfo itemEffectInfo = itemEffectEnt.getItemEffectInfo();
        Map<Integer, ItemEffectdetaiInfo> itemEffectdetaiInfoMap = itemEffectInfo.getItemEffectdetaiInfoMap();
        ItemEffectdetaiInfo itemEffectdetaiInfo = itemEffectdetaiInfoMap.get(itemModelId);
        /**
         * 第一次使用
         */
        if (itemEffectdetaiInfo == null || !itemEffectdetaiInfo.isEffectiveStatus() || itemEffectdetaiInfo.getInvalidTime() < TimeUtil.now()) {
            /**
             * 如果没有道具的使用信息则直接加入到数据库中，并做定时操作
             */
            AbstractAttributeContainer attributeContainer = player.getAttributeContainer();
            attributeContainer.putAndComputeAttributes(MedicineAttributeId.getMedicineAttributeId(itemModelId), addAttributeList);
            SpringContext.getPlayerSerivce().save(playerEnt);
            ChangeAttrbuteEvent event = ChangeAttrbuteEvent.valueOf(player);
            SpringContext.getEvenManager().syncSubmit(event);
            /**
             *  TODO 做战斗属性同步
             */

            AddAttributeBuffSynCommand synCommand = AddAttributeBuffSynCommand.valueOf(player,
                    MedicineAttributeId.getMedicineAttributeId(itemModelId), addAttributeList);
            SpringContext.getSceneExecutorService().submit(synCommand);
            /**
             * 抛出定时command
             */
            itemEffectdetaiInfoMap.put(itemModelId, ItemEffectdetaiInfo.valueOf(itemModelId, true,
                    TimeUtil.now() + effectiveTime * num));
            ItemExpireDelayCommand command = new ItemExpireDelayCommand(effectiveTime * num, acountId, itemModelId, player.getObjectId());
            SpringContext.getAccountExecutorService().submit(command);
            SpringContext.getItemEffectService().putCommand(command);
        } else {
            /**
             * 如果有相关的信息更新过期时间，则将之前的command取消再并移除 重新抛一个command
             */
            itemEffectdetaiInfo.setInvalidTime(itemEffectdetaiInfo.getInvalidTime() + effectiveTime * num);
            long delay = itemEffectdetaiInfo.getInvalidTime() - TimeUtil.now();
            ItemExpireDelayCommand command = new ItemExpireDelayCommand(delay, acountId, itemModelId, player.getObjectId());
            SpringContext.getAccountExecutorService().submit(command);
            Map<Integer, ItemExpireDelayCommand> itemExpireDelayCommandMap = SpringContext.getItemEffectService().getItemExpireDelayCommandMap(player.getObjectId());
            ItemExpireDelayCommand oldCommand = itemExpireDelayCommandMap.get(itemModelId);
            if (oldCommand == null) {
                return;
            }
            oldCommand.cancel();
            SpringContext.getItemEffectService().removeDelayCommand(player.getObjectId(), itemModelId);
            SpringContext.getItemEffectService().putCommand(command);
        }
        SpringContext.getItemEffectService().save(itemEffectEnt);
        if (logger.isDebugEnabled()) {
            logger.debug("玩家{}使用药品道具成功", acountId);
        }
    }

    public List<Attribute> getAddAttributeList() {
        return addAttributeList;
    }

    public void setAddAttributeList(List<Attribute> addAttributeList) {
        this.addAttributeList = addAttributeList;
    }


    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }
}
