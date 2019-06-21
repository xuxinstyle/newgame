package com.game.user.item.service;

import com.game.user.equip.resource.EquipResource;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;
import com.game.user.item.resource.ItemResource;
import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 11:36
 */
public interface ItemService {
    /**
     * 创建道具
     *
     * @param itemModelId
     * @param num
     * @return
     */
    AbstractItem createItem(int itemModelId, int num);

    /**
     * 获取道具资源
     */
    ItemResource getItemResource(int itemModelId);

    /**
     * 添加道具到玩家背包中
     *
     * @param accountId
     * @param item
     */
    void addItemToPackAndSave(String accountId, AbstractItem item);

    /**
     * 移除玩家背包中指定的道具
     *
     * @param accountId
     * @param item
     */
    void removeItemFromPack(String accountId, AbstractItem item);

    /**
     * 从数据库中查询玩家背包数据
     *
     * @param accountId
     * @return
     */
    ItemStorageEnt getItemStorageEnt(String accountId);

    /**
     * 保存玩家背包信息
     *
     * @param itemStorageEnt
     */
    void save(ItemStorageEnt itemStorageEnt);

    /**
     * 整理玩家背包
     *
     * @param accountId
     */
    void sort(String accountId);

    /**
     * 整理玩家背包
     *
     * @param itemStorageEnt
     */
    void sort(ItemStorageEnt itemStorageEnt);

    /**
     * 创建玩家背包
     *
     * @param accountId
     */
    void createStorage(String accountId);

    /**
     * 响应添加道具到背包的请求
     */
    void AwardToPack(TSession session, String accountId, int itemModelId, int num);
    /**
     * 移除道具
     */
    void removeItem(TSession session, String accountId, long Object, int num);

    /**
     * 查看玩家的背包信息
     * @param session
     * @param accountId
     */
    void showItem(TSession session, String accountId);

    /**
     * 获取装备资源
     * @param itemModelId
     * @return
     */
    EquipResource getEquipResource(int itemModelId);

    /**
     * 使用道具
     * @param session
     * @param itemObjectId
     */
    void useItem(TSession session, long itemObjectId,int num);
}
