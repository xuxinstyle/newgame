package com.game.user.equip.service;

import com.game.user.equip.entity.EquipmentEnt;
import com.game.user.equip.event.EquipEvent;
import com.game.user.equip.resource.EquipResource;
import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/16 19:05
 */
public interface EquipService {
    /**
     * 穿装备
     */
    void equip(TSession session, String accountId, long equipObjectId);
    /**
     * 卸下装备 1 卸装备成功
     */

    int unEquip(TSession session, String accountId, int position);
    /**
     * 保存装备数据
     */
    void save(EquipmentEnt equipmentEnt);

    /**
     * 插入装备数据
     * @param equipmentEnt
     */
    void insert(EquipmentEnt equipmentEnt);
    /**
     * 获取装备资源数据
     * @param itemModelId
     * @return
     */
    EquipResource getEquipResource(int itemModelId);

    /**
     * 获取数据库中装备信息
     * @param playerId
     * @return
     */
    EquipmentEnt getEquipmentEnt(long playerId);

    /**
     * 查看玩家装备栏信息
     * @param session
     * @param accountId
     */
    void showEquip(TSession session, String accountId);

    ;
}
