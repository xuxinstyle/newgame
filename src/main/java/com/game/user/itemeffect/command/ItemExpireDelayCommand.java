package com.game.user.itemeffect.command;

import com.game.SpringContext;
import com.game.base.executor.account.impl.AbstractAccountDelayCommand;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 21:45
 */
public class ItemExpireDelayCommand extends AbstractAccountDelayCommand {
    /**
     * 道具id
     */
    private int itemModelId;
    /**
     * 角色Id
     */
    private long  playerId;

    public ItemExpireDelayCommand(long delay, String accountId, int itemModelId, long playerId) {
        super(delay,accountId);
        this.itemModelId = itemModelId;
        this.playerId = playerId;
    }

    @Override
    public String getName() {
        return "ItemExpireDelayCommand";
    }

    @Override
    public void active() {
        SpringContext.getItemEffectService().doItemExpire(getAccountId(),itemModelId);
    }

    public int getItemModelId() {
        return itemModelId;
    }

    public void setItemModelId(int itemModelId) {
        this.itemModelId = itemModelId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }
}
