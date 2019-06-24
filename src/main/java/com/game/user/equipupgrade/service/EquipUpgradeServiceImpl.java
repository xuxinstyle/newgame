package com.game.user.equipupgrade.service;

import com.game.SpringContext;
import com.game.user.condition.model.UpgradeCondition;
import com.game.user.equip.resource.EquipResource;
import com.game.user.equipupgrade.packet.SM_EquipUpgrade;
import com.game.user.item.constant.ItemType;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;
import com.game.user.item.model.Equipment;
import com.game.user.item.model.ItemStorageInfo;
import com.game.user.item.resource.ItemResource;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 14:50
 */
@Component
public class EquipUpgradeServiceImpl implements EquipUpgradeService {
    private static final Logger logger = LoggerFactory.getLogger(EquipUpgradeServiceImpl.class);
    @Override
    public void equipUpgrade(TSession session, String accountId, long itemObjectId) {
        /**
         * 判断是否能升级
         */
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        AbstractItem item = pack.getItem(itemObjectId);
        if(item==null){
            SM_EquipUpgrade sm = new SM_EquipUpgrade();
            sm.setStatus(2);
            logger.warn("玩家{}背包中没有道具{}",accountId,itemObjectId);
            session.sendPacket(sm);
            return;
        }
        if(item.getItemType()!=ItemType.EQUIPMENT.getId()){
            SM_EquipUpgrade sm = new SM_EquipUpgrade();
            sm.setStatus(3);
            logger.warn("道具{}不能升级",itemObjectId);
            session.sendPacket(sm);
            return;
        }
        Equipment equipment = (Equipment)item;
        EquipResource equipResource = SpringContext.getItemService().getEquipResource(equipment.getItemModelId());
        UpgradeCondition upgradeCondition = equipResource.getUpgradeCondition();
        Map<String, Object> param = new HashMap<>();
        param.put("equipLevel", equipment.getStrenNum());
        if(!upgradeCondition.checkCondition(accountId,param)){
            SM_EquipUpgrade sm = new SM_EquipUpgrade();
            sm.setStatus(4);
            session.sendPacket(sm);
            logger.warn("升级道具不足");
            return;
        }
        /**
         * TODO:配置一下装备升级后对应的道具id
         */
        ItemResource newItemResource = SpringContext.getItemService().getItemResource(equipResource.getUpgradeId());
        if(newItemResource==null){
            SM_EquipUpgrade sm = new SM_EquipUpgrade();
            sm.setStatus(5);
            logger.warn("达到升级上限");
            session.sendPacket(sm);
            return;
        }
        Equipment newEquipment = new Equipment();
        newEquipment.init(newItemResource);
        newEquipment.setObjectId(itemObjectId);
        newEquipment.setNum(1);
        /**
         * 消耗道具 移除原来的装备 添加新装备到背包
         */
        pack.removeItem(upgradeCondition.getItemModelId(),upgradeCondition.getNum());
        pack.removeByObject(itemObjectId,1);
        pack.addItem(newEquipment);
        SpringContext.getItemService().save(itemStorageEnt);
        SM_EquipUpgrade sm = new SM_EquipUpgrade();
        sm.setStatus(1);
        session.sendPacket(sm);
    }
}
