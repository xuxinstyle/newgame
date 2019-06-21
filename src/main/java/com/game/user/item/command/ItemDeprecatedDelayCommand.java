package com.game.user.item.command;

import com.game.SpringContext;
import com.game.base.executor.common.Impl.AbstractCommonDelayCommand;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 21:45
 */
public class ItemDeprecatedDelayCommand extends AbstractCommonDelayCommand {

    /**
     * 账号id
     */
    private String accountId;

    /**
     * 道具id
     */
    private int itemModelId;
    /**
     * 角色Id
     */
    private long  playerId;

    public ItemDeprecatedDelayCommand(long delay, String accountId, int itemModelId, long playerId) {
        super(delay);
        this.accountId = accountId;
        this.itemModelId = itemModelId;
        this.playerId = playerId;
    }

    @Override
    public String getName() {
        return "ItemDeprecatedDelayCommand";
    }

    @Override
    public void active() {
        ItemDeprecatedCommand command = new ItemDeprecatedCommand(accountId,itemModelId, playerId);
        SpringContext.getAccountExecutorService().submit(command);
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
