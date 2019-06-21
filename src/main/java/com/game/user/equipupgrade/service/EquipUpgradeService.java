package com.game.user.equipupgrade.service;

import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 14:50
 */
public interface EquipUpgradeService {
    /**
     * 装备升级品质
     * @param session
     * @param accountId
     * @param itemObjectId
     */
    void equipUpgrade(TSession session, String accountId,long itemObjectId);
}
