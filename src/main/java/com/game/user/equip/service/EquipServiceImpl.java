package com.game.user.equip.service;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.AttributeContainer;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.user.condition.model.EquipCondition;
import com.game.user.equip.constant.EquipType;
import com.game.user.equip.entity.EquipmentEnt;
import com.game.user.equip.model.EquipHole;
import com.game.user.equip.model.EquipmentInfo;
import com.game.user.equip.packet.SM_Equip;
import com.game.user.equip.packet.SM_ShowEquipInfo;
import com.game.user.equip.packet.SM_UnEquip;
import com.game.user.equip.packet.bean.EquipmentVO;
import com.game.user.equip.resource.EquipResource;
import com.game.user.item.constant.ItemType;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;
import com.game.user.item.model.Equipment;
import com.game.user.item.model.ItemStorageInfo;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/16 19:05
 */
@Component
public class EquipServiceImpl implements EquipService {
    private static final Logger logger = LoggerFactory.getLogger(EquipServiceImpl.class);

    @Autowired
    private EquipManager equipManager;

    /**
     * 注意穿脱装备时需要加上装备的基础属性和强化属性
     *
     * @param session
     * @param accountId
     * @param equipObjectId
     */
    @Override
    public void equip(TSession session, String accountId, long equipObjectId) {
        /**
         * 判断穿戴条件
         */
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        AbstractItem item = pack.getItem(equipObjectId);
        if (item == null) {
            SM_Equip sm = new SM_Equip();
            sm.setStatus(3);
            session.sendPacket(sm);
            return;
        }
        if (!checkWear(item, accountId)) {
            SM_Equip sm = new SM_Equip();
            sm.setStatus(2);
            session.sendPacket(sm);
            if (logger.isDebugEnabled()) {
                logger.debug("玩家{}");
            }
            return;
        }
        /**
         * 卸下原来的装备 并计算卸下后的玩家属性
         */
        Equipment equipment = (Equipment) item;
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(accountId);
        Player player = playerEnt.getPlayer();
        EquipmentEnt equipmentEnt = getEquipmentEnt(player.getObjectId());
        EquipmentInfo equipmentInfo = equipmentEnt.getEquipmentInfo();

        Equipment oldEquipment = equipmentInfo.unEquip(equipment.getEquipType());

        AttributeContainer<Player> attributeContainer = player.getAttributeContainer();
        /**
         * 如果玩家身上有装备 先脱装备
         * FIXME:计算玩家属性时注意加上装备的基础属性和强化属性
         */
        if (oldEquipment != null) {
            List oldBaseAttributeList = new ArrayList<>(oldEquipment.getAttributeList());
            List oldStrenAttributeList = new ArrayList<>(oldEquipment.getStrenAttributeMap().values());
            attributeContainer.removeAndCompute(oldBaseAttributeList);
            attributeContainer.removeAndCompute(oldStrenAttributeList);

            SpringContext.getPlayerSerivce().save(playerEnt);
            SpringContext.getEquipService().save(equipmentEnt);

        }
        /**
         * 从背包扣除新装备 将旧装备加到玩家背包中
         */
        pack.removeByObject(equipObjectId, 1);
        SpringContext.getItemService().save(itemStorageEnt);
        if (oldEquipment != null) {
            SpringContext.getItemService().addItemToPackAndSave(accountId, oldEquipment);
        }
        /**
         * 穿新装备
         */
        EquipResource equipResource = getEquipResource(item.getItemModelId());
        equipmentInfo.wearEquipment(EquipType.valueOf(equipResource.getEquipType()), equipment);
        save(equipmentEnt);

        /**
         * FIXME:计算玩家属性时注意加上装备的基础属性和强化属性
         */
        List<Attribute> newBaseAttributeList = equipment.getAttributeList();
        List<Attribute> newStrenAttributeList = new ArrayList<>(equipment.getStrenAttributeMap().values());
        attributeContainer.addAndComputeMap(newBaseAttributeList);
        attributeContainer.addAndComputeMap(newStrenAttributeList);
        /**
         * 保存 并响应客户端
         */
        SpringContext.getPlayerSerivce().save(playerEnt);
        SM_Equip sm = new SM_Equip();
        sm.setStatus(1);
        session.sendPacket(sm);
    }

    @Override
    public EquipResource getEquipResource(int itemModelId) {
        return equipManager.getEquipResource(itemModelId);
    }

    @Override
    public EquipmentEnt getEquipmentEnt(long playerId) {
        return equipManager.getEquipmentEnt(playerId);
    }


    private boolean checkWear(AbstractItem item, String accountId) {
        if (item.getItemType() != ItemType.EQUIPMENT.getId()) {
            if (logger.isDebugEnabled()) {
                logger.debug("玩家{}穿的不是装备类型的道具{}", accountId, item.getItemType());
            }
            return false;
        }
        EquipResource equipResource = getEquipResource(item.getItemModelId());
        EquipCondition equipCondition = equipResource.getEquipCondition();
        Equipment equipment = (Equipment) item;
        Map<String, Object> param = new HashMap<>();
        param.put("equipLevel", equipment.getStrenNum());
        if (!equipCondition.checkCondition(accountId, param)) {
            if (logger.isDebugEnabled()) {
                logger.debug("玩家{}不符合穿戴条件", accountId);
            }
            return false;
        }
        return true;
    }

    @Override
    public void unEquip(TSession session, String accountId, int position) {
        /**
         * 获取到装备栏的数据
         * 判断背包空间是否充足
         *
         */
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(accountId);
        Player player = playerEnt.getPlayer();
        EquipmentEnt equipmentEnt = getEquipmentEnt(player.getObjectId());
        EquipmentInfo equipmentInfo = equipmentEnt.getEquipmentInfo();
        Equipment equipment = equipmentInfo.getPositionEquip(EquipType.valueOf(position));
        if (equipment == null) {
            SM_UnEquip sm = new SM_UnEquip();
            sm.setStatus(3);
            session.sendPacket(sm);
            return;
        }
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        if (!pack.checkPackEnough(equipment)) {
            SM_UnEquip sm = new SM_UnEquip();
            sm.setStatus(2);
            session.sendPacket(sm);
            return;
        }
        /**
         * 卸下装备
         * 将装备加入背包
         */
        equipmentInfo.unEquip(EquipType.valueOf(position));
        save(equipmentEnt);
        SpringContext.getItemService().save(itemStorageEnt);

        SpringContext.getItemService().addItemToPackAndSave(accountId, equipment);
        /**
         * FIXME:计算玩家属性时注意加上装备的基础属性和强化属性
         */
        List<Attribute> baseAttributeList = equipment.getAttributeList();
        List<Attribute> strenAttributeList = new ArrayList<>(equipment.getStrenAttributeMap().values());
        AttributeContainer<Player> attributeContainer = player.getAttributeContainer();
        attributeContainer.removeAndCompute(baseAttributeList);
        attributeContainer.removeAndCompute(strenAttributeList);

        SpringContext.getPlayerSerivce().save(playerEnt);
        /**
         * 保存玩家数据和装备栏数据和背包数据
         */
        SM_UnEquip sm = new SM_UnEquip();
        sm.setStatus(1);
        session.sendPacket(sm);
        return;
    }

    @Override
    public void save(EquipmentEnt equipmentEnt) {
        equipManager.save(equipmentEnt);
    }

    @Override
    public void insert(EquipmentEnt equipmentEnt) {
        equipManager.insert(equipmentEnt);
    }

    @Override
    public void showEquip(TSession session, String accountId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        long playerId = accountInfo.getPlayerId();
        EquipmentEnt equipmentEnt = SpringContext.getEquipService().getEquipmentEnt(playerId);
        EquipmentInfo equipmentInfo = equipmentEnt.getEquipmentInfo();
        Map<EquipType, EquipHole> equipmentMap = equipmentInfo.getEquipmentMap();
        Map<Integer, EquipmentVO> positionEquipment = new HashMap<>();
        for (Map.Entry<EquipType, EquipHole> entry : equipmentMap.entrySet()) {
            EquipType key = entry.getKey();
            EquipHole equipHole = entry.getValue();
            if (equipHole.getEquipment() == null) {
                positionEquipment.put(key.getPosition(), EquipmentVO.valueOf(key.getPosition()));
            } else {
                Equipment value = equipHole.getEquipment();
                EquipmentVO equipmentVO = new EquipmentVO();
                equipmentVO.setEquipName(value.getName());
                equipmentVO.setJob(value.getJobLimit());
                equipmentVO.setQuality(value.getQuality());
                equipmentVO.setLevel(value.getStrenNum());
                equipmentVO.setPosition(equipHole.getPosition());
                List<Attribute> strenAttributes = new ArrayList<>(value.getStrenAttributeMap().values());
                equipmentVO.setStrenAttributeList(strenAttributes);
                List<Attribute> attributeList = value.getAttributeList();
                equipmentVO.setAttributeList(attributeList);
                positionEquipment.put(key.getPosition(), equipmentVO);
            }
        }
        SM_ShowEquipInfo sm = new SM_ShowEquipInfo();
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        sm.setJob(player.getPlayerJob());
        sm.setPlayerName(player.getPlayerName());
        sm.setPlayerId(playerId);
        sm.setPositionEquipment(positionEquipment);
        session.sendPacket(sm);
    }
}
