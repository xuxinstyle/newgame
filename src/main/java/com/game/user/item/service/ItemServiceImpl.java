package com.game.user.item.service;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.common.exception.RequestException;
import com.game.role.equip.resource.EquipResource;
import com.game.user.item.constant.ItemType;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;
import com.game.user.item.model.Equipment;
import com.game.user.item.model.ItemStorageInfo;
import com.game.user.itemeffect.model.AbstractUseEffect;
import com.game.user.item.packet.*;
import com.game.user.item.packet.bean.ItemVO;
import com.game.user.item.resource.ItemResource;
import com.game.util.I18nId;
import com.game.util.PlayerUtil;
import com.game.util.SendPacketUtil;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 11:36
 */
@Component
public class ItemServiceImpl implements ItemService {

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    private ItemManager itemManager;

    /**
     * fixme: 创建道具时统一使用这个方法
     *
     * @param itemModelId
     * @param num
     * @return
     */
    @Override
    public AbstractItem createItem(int itemModelId, int num) {
        ItemResource resource = getItemResource(itemModelId);
        AbstractItem item = ItemType.valueOf(resource.getItemType()).create();
        item.init(resource);
        item.setNum(num);
        item.setObjectId(createObject(ObjectType.ITEM));
        return item;
    }

    @Override
    public ItemResource getItemResource(int itemModelId) {
        return itemManager.getItemResource(itemModelId);
    }

    @Override
    public EquipResource getEquipResource(int itemModelId) {
        return itemManager.getEquipResource(itemModelId);
    }

    public long createObject(ObjectType type) {
        return SpringContext.getIdentifyService().getNextIdentify(type);
    }

    /**
     * FIXME:添加道具 调用该方法前要判断背包是否充足
     * @param accountId
     * @param item
     */
    @Override
    public void addItemToPackAndSave(String accountId, AbstractItem item) {
        ItemStorageEnt itemStorageEnt = getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        /**
         * FIXME:换一个方法试一下
         */
        pack.addItemQuick(item);
        save(itemStorageEnt);
        SendPacketUtil.send(accountId, SM_AwardToPack.valueOf(1));

    }


    @Override
    public ItemStorageEnt getItemStorageEnt(String accountId) {
        return itemManager.getItemStorageEnt(accountId);
    }

    @Override
    public void save(ItemStorageEnt itemStorageEnt) {
        itemManager.save(itemStorageEnt);

    }

    /**
     * TODO:这个方法暂时没有用到,等找到效率比较高的整理背包的算法再用
     * @param accountId
     */
    @Override
    public void sort(String accountId) {
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        sort(itemStorageEnt);

    }

    /**
     * TODO: 这里还没用 等加gm命令 这两个sort需要注意持久化的时间
     *
     * @param itemStorageEnt
     */
    @Override
    public void sort(ItemStorageEnt itemStorageEnt) {
        ItemStorageInfo pack = itemStorageEnt.getPack();
        pack.sort();
        save(itemStorageEnt);
    }

    @Override
    public void createStorage(String accountId) {
        ItemStorageEnt itemStorageEnt = new ItemStorageEnt();
        itemStorageEnt.setAccountId(accountId);
        ItemStorageInfo itemStorageInfo = ItemStorageInfo.valueOf(PlayerUtil.PACK_MAX_SIZE, PlayerUtil.INIT_PACK_USE_SIZE);
        itemStorageEnt.setPack(itemStorageInfo);
        save(itemStorageEnt);
    }

    /**
     * 这里发奖先一个个加进去 背包满了就消失了
     *
     * @param accountId
     * @param items
     */
    @Override
    public void awardToPack(String accountId, List<AbstractItem> items) {

        ItemStorageEnt itemStorageEnt = getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        for (AbstractItem item : items) {
            ItemResource itemResource = SpringContext.getItemService().getItemResource(item.getItemModelId());
            if (itemResource.isAutoUse()) {
                item.use(accountId, item.getNum());
                SendPacketUtil.send(accountId, SM_AwardToPack.valueOf(4));
                if (logger.isDebugEnabled()) {
                    logger.debug("道具[{}][{}]自动使用数量[{}]", item.getName(), item.getObjectId(), item.getNum());
                }
                continue;
            }
            if (!pack.checkPackEnough(item)) {
                logger.warn("玩家{}背包空间不足发奖{}失败", accountId, item);
                SM_AwardToPack sm = SM_AwardToPack.valueOf(3);
                SendPacketUtil.send(accountId, sm);
                continue;
            }

            addItemToPackAndSave(accountId, item);
        }
    }

    @Override
    public void gmAwardToPack(String accountId, int itemModelId, int num) {
        ItemStorageEnt itemStorageEnt = getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        if (!checkResource(itemModelId)) {
            SendPacketUtil.send(accountId, SM_AwardToPack.valueOf(2));
            return;
        }
        AbstractItem item = createItem(itemModelId, num);
        ItemResource itemResource = SpringContext.getItemService().getItemResource(item.getItemModelId());
        if (itemResource.isAutoUse()) {
            item.use(accountId, num);
            SendPacketUtil.send(accountId, SM_AwardToPack.valueOf(1));
            return;
        }
        if (!pack.checkPackEnough(item)) {
            logger.warn("玩家{}背包空间不足发奖失败", accountId);
            SendPacketUtil.send(accountId, SM_AwardToPack.valueOf(3));
            return;
        }
        addItemToPackAndSave(accountId, item);
    }

    private boolean checkResource(int itemModelId) {
        ItemResource itemResource = getItemResource(itemModelId);
        if (itemResource == null) {
            return false;
        }
        return true;
    }

    @Override
    public void removeItem(TSession session, String accountId, long objectId, int num) {
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        if (!pack.removeAndThrow(objectId, num)) {
            SM_RemoveItemFormPack sm = new SM_RemoveItemFormPack();
            sm.setStatus(0);
            session.sendPacket(sm);
            logger.error("玩家{}移除装备失败，背包中的道具{}数量不足{}", accountId, objectId, num);
            return;
        }
        save(itemStorageEnt);
        SM_RemoveItemFormPack sm = new SM_RemoveItemFormPack();
        sm.setStatus(1);
        session.sendPacket(sm);
    }

    @Override
    public void showItem(TSession session, String accountId) {
        ItemStorageEnt itemStorageEnt = getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        AbstractItem[] items = pack.getItems();
        List<ItemVO> list = new ArrayList<>();

        for (int i = 0; i < items.length; i++) {
            AbstractItem item = items[i];
            if (item == null) {
                continue;

            } else {
                ItemVO itemVO = new ItemVO();
                itemVO.setObjectId(item.getObjectId());
                itemVO.setItemModelId(item.getItemModelId());
                itemVO.setItemName(item.getName());
                itemVO.setNum(item.getNum());
                list.add(itemVO);
            }
        }
        SM_ShowPackItem sm = new SM_ShowPackItem();
        sm.setSize(pack.getMaxSize());
        sm.setUseSize(pack.getUseSize());
        sm.setItemList(list);
        session.sendPacket(sm);
    }

    @Override
    public void useItem(TSession session, long itemObjectId,int num) {
        String accountId = session.getAccountId();
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        AbstractItem item = pack.getItem(itemObjectId);
        if(item==null){
            logger.warn("玩家背包没有道具[{}]",itemObjectId);
            SM_UseItem sm = new SM_UseItem();
            sm.setStatus(2);
            session.sendPacket(sm);
            return;
        }
        ItemResource resource = item.getResource();
        AbstractUseEffect useEffect = resource.getUseEffect();
        if(useEffect==null){
            logger.warn("道具[{}]不能使用",itemObjectId);
            SM_UseItem sm = new SM_UseItem();
            sm.setStatus(3);
            session.sendPacket(sm);
            return;
        }
        /**
         * fixme:这里看调用什么地方的use比较好?  这里调用item的use扩展性比较好，如果以后需要加其他可使用的道具，只要直接调用
         */
        if (!pack.removeAndThrow(itemObjectId, num)) {
            RequestException.throwException(I18nId.PACK_ITEM_NUM_INSUFFICIENT);
        }

        item.use(accountId,num);
        save(itemStorageEnt);
        SM_UseItem sm = new SM_UseItem();
        sm.setEffectiveTime(num*useEffect.getEffectiveTime());
        sm.setStatus(1);
        session.sendPacket(sm);
    }

    @Override
    public void showItemInfo(TSession session, long itemObjectId) {
        String accountId = session.getAccountId();
        if(accountId==null){
            return;
        }
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        AbstractItem item = pack.getItem(itemObjectId);
        if(item==null){
            SM_ShowItemInfo sm = new SM_ShowItemInfo();
            sm.setStatus(2);
            return;
        }
        ItemResource resource = item.getResource();

        SM_ShowItemInfo sm = new SM_ShowItemInfo();
        sm.setItemObjectId(itemObjectId);
        sm.setItemName(resource.getName());
        sm.setNum(item.getNum());
        sm.setItemType(item.getItemType());
        if(item.getItemType()!=ItemType.EQUIPMENT.getId()){
            session.sendPacket(sm);
            return;
        }
        Equipment equipment = (Equipment)item;
        sm.setEquipQuality(equipment.getQuality());
        sm.setEquipType(equipment.getEquipType().getPosition());
        sm.setStrenNum(equipment.getStrenNum());
        sm.setJobType(equipment.getJobLimit());
        sm.setLimitLevel(resource.getUseLevel());
        sm.setBaseAttributeList(equipment.getBaseAttributeList());
        sm.setStrenAttributeList(new ArrayList<>(equipment.getStrenAttributeList()));
        sm.setStatus(1);
        session.sendPacket(sm);
    }

}
