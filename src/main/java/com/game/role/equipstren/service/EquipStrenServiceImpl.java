package com.game.role.equipstren.service;

import com.game.SpringContext;
import com.game.role.equipstren.resource.EquipStrenResource;
import com.game.role.player.model.Player;
import com.game.user.condition.model.StrenCondition;
import com.game.role.equip.resource.EquipResource;
import com.game.user.item.constant.ItemType;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;
import com.game.user.item.model.Equipment;
import com.game.user.item.model.ItemStorageInfo;
import com.game.role.equipstren.packet.SM_StrenEquip;
import com.socket.core.session.TSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 9:08
 */
@Component
public class EquipStrenServiceImpl implements EquipStrenService {
    @Autowired
    private EquipStrenManager equipStrenManager;
    /**
     * 强化装备
     * @param session
     * @param itemObjectId
     * @param accountId
     */
    @Override
    public void strenEquip(TSession session, long itemObjectId, String accountId) {
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        AbstractItem item = pack.getItem(itemObjectId);
        if(item==null){
            SM_StrenEquip sm = new SM_StrenEquip();
            sm.setStatus(2);
            session.sendPacket(sm);
            return;
        }
        if(item.getItemType()!=ItemType.EQUIPMENT.getId()){
            SM_StrenEquip sm = new SM_StrenEquip();
            sm.setStatus(4);
            sm.setItemName(item.getName());
            session.sendPacket(sm);
            return;
        }
        Equipment equipment = (Equipment) item;
        int itemModelId = item.getItemModelId();
        EquipResource equipResource = SpringContext.getItemService().getEquipResource(itemModelId);
        StrenCondition streCondition = equipResource.getStreCondition();
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        if(!streCondition.checkCondition(player,null)){
            SM_StrenEquip sm = new SM_StrenEquip();
            sm.setStatus(3);
            session.sendPacket(sm);
            return;
        }
        if(!equipment.doStrenEquip()){
            SM_StrenEquip sm = new SM_StrenEquip();
            sm.setStatus(5);
            session.sendPacket(sm);
            return;
        }
        /**
         * 消耗强化道具
         */
        pack.removeItem(streCondition.getItemModelId(), streCondition.getNum());
        SpringContext.getItemService().save(itemStorageEnt);

        SM_StrenEquip sm = new SM_StrenEquip();
        sm.setItemName(equipment.getName());
        sm.setStatus(1);
        session.sendPacket(sm);
        return;
    }
    @Override
    public EquipStrenResource getEquipStrenResource(int position,int quality, int level){
        return equipStrenManager.getEquipStrenResource(position,quality,level);
    }
}
