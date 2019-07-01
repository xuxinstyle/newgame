package com.game.role.equipstren.service;

import com.game.role.equipstren.resource.EquipStrenResource;
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

    /**
     * 获取装备强化资源
     * @param position
     * @param quality
     * @param level
     * @return
     */
    EquipStrenResource getEquipStrenResource(int position, int quality, int level);
}
