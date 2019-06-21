package com.game.user.itemeffect.service;

import com.game.user.itemeffect.entity.ItemEffectEnt;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 11:59
 */
public interface ItemEffectService {
    /**
     * 持久化到数据库中
     * @param itemEffectEnt
     */
    void save(ItemEffectEnt itemEffectEnt);
    /**
     * 获取玩家的道具效果信息
     */
    ItemEffectEnt getItemEffectEnt(long playerId);

    /**
     * 做登录后的操作，判断玩家使用的药是否过期
     * @param accountId
     */
    void doLoginAfter(String accountId);
}
