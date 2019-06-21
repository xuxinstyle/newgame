package com.game.user.item.service;

import com.db.HibernateDao;
import com.game.SpringContext;
import com.game.base.gameObject.constant.ObjectType;
import com.game.user.equip.resource.EquipResource;
import com.game.user.item.constant.ItemType;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;
import com.game.user.item.model.ItemStorageInfo;
import com.game.user.item.packet.SM_AwardToPack;
import com.game.user.item.packet.SM_RemoveItemFormPack;
import com.game.user.item.packet.SM_ShowPackItem;
import com.game.user.item.packet.bean.ItemVO;
import com.game.user.item.resource.ItemResource;
import com.resource.core.StorageManager;
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

    /**
     * 背包最大格子数
     */
    public static final int MAX_PACK_SIZE = 150;
    /**
     * 背包初始已使用的格子数
     */
    public static final int INIT_PACK_USED_SIZE = 0;
    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Autowired
    private ItemManager itemManager;

    @Autowired
    private StorageManager storageManager;

    @Autowired
    private HibernateDao hibernateDao;

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

    @Override
    public void addItemToPackAndSave(String accountId, AbstractItem item) {
        ItemStorageEnt itemStorageEnt = getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        pack.addItem(item);
        save(itemStorageEnt);

    }

    @Override
    public void removeItemFromPack(String accountId, AbstractItem item) {
        ItemStorageEnt itemStorageEnt = getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        pack.removeByObject(item.getObjectId(), item.getNum());
        save(itemStorageEnt);
    }

    @Override
    public ItemStorageEnt getItemStorageEnt(String accountId) {
        return hibernateDao.find(ItemStorageEnt.class, accountId);
    }

    @Override
    public void save(ItemStorageEnt itemStorageEnt) {
        hibernateDao.saveOrUpdate(ItemStorageEnt.class, itemStorageEnt);
    }

    @Override
    public void sort(String accountId) {
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        sort(itemStorageEnt);
        save(itemStorageEnt);
    }

    /**
     * 这两个sort需要注意持久化的时间
     *
     * @param itemStorageEnt
     */
    @Override
    public void sort(ItemStorageEnt itemStorageEnt) {
        ItemStorageInfo pack = itemStorageEnt.getPack();
        pack.sort();
    }

    @Override
    public void createStorage(String accountId) {
        ItemStorageEnt itemStorageEnt = new ItemStorageEnt();
        itemStorageEnt.setAccountId(accountId);
        ItemStorageInfo itemStorageInfo = ItemStorageInfo.valueOf(MAX_PACK_SIZE, INIT_PACK_USED_SIZE);
        itemStorageEnt.setPack(itemStorageInfo);
        save(itemStorageEnt);
    }

    @Override
    public void AwardToPack(TSession session, String accountId, int itemModelId, int num) {
        ItemStorageEnt itemStorageEnt = getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        if (!checkResource(itemModelId)) {
            SM_AwardToPack sm = new SM_AwardToPack();
            sm.setStatus(2);
            session.sendPacket(sm);
            return;
        }
        AbstractItem item = createItem(itemModelId, num);

        if (!pack.checkPackEnough(item)) {
            logger.warn("玩家{}背包空间不足发奖失败", accountId);
            SM_AwardToPack sm = new SM_AwardToPack();
            sm.setStatus(3);
            session.sendPacket(sm);
            return;
        }
        ItemResource itemResource = SpringContext.getItemService().getItemResource(item.getItemModelId());
        if (itemResource.isAutoUse()) {

            item.use(accountId, num);
        } else {
            addItemToPackAndSave(accountId, item);
        }
        SM_AwardToPack sm = new SM_AwardToPack();
        sm.setStatus(1);
        session.sendPacket(sm);
    }

    private boolean checkResource(int itemModelId) {
        ItemResource itemResource = getItemResource(itemModelId);
        if (itemResource == null) {
            return false;
        }
        return true;
    }

    @Override
    public void removeItem(TSession session, String accountId, long object, int num) {
        ItemStorageEnt itemStorageEnt = SpringContext.getItemService().getItemStorageEnt(accountId);
        ItemStorageInfo pack = itemStorageEnt.getPack();
        if (!pack.removeByObject(object, num)) {
            SM_RemoveItemFormPack sm = new SM_RemoveItemFormPack();
            sm.setStatus(0);
            session.sendPacket(sm);
            logger.error("玩家{}移除装备失败，背包中的道具{}数量不足{}", accountId, object, num);
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
}
