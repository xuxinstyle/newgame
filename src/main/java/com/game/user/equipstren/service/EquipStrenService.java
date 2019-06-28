package com.game.user.equipstren.service;

import com.socket.core.session.TSession;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 9:07
 */
public interface EquipStrenService {
    /**
     * 强化装备
     * @param session
     * @param itemObjectId
     * @param accountId
     */
    void strenEquip(TSession session, long itemObjectId,String accountId);
}
