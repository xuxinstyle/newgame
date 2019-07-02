package com.game.user.itemeffect.service;

import com.game.role.player.event.LogoutEvent;
import com.game.user.itemeffect.command.ItemExpireDelayCommand;
import com.game.user.itemeffect.entity.ItemEffectEnt;

import java.util.Map;

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
     * 获取玩家的道具效果信息 数据库中没有则创建一条信息
     * @param playerId
     * @return
     */
    ItemEffectEnt getItemEffectEntOrCreate(long playerId);
    /**
     * 做登录后的操作，判断玩家使用的药是否过期
     * @param accountId
     */
    void doLoginAfter(String accountId);
    /**
     * 获取延迟道具command
     * @param playerId
     * @return
     */
    Map<Integer, ItemExpireDelayCommand> getItemExpireDelayCommandMap(long playerId);

    /**
     * 添加言辞道具command
     */
    void putCommand(ItemExpireDelayCommand command);

    /**
     * 移除延迟命令
     * @param itemModelId
     */
    void removeDelayCommand(long playerId ,int itemModelId);

    /**
     * 做玩家登出后的操作
     * @param event
     */
    void doLogoutAfter(LogoutEvent event);

    /**
     * 做药效过期处理
     * @param playerId
     * @param itemModelId
     */
    void doItemExpire(String accountId, int itemModelId);
}
