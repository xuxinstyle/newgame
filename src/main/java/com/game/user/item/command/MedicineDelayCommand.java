package com.game.user.item.command;

import com.game.SpringContext;
import com.game.base.executor.ICommand;
import com.game.base.executor.common.Impl.AbstractCommonDelayCommand;

/**
 * @Author：xuxin
 * @Date: 2019/6/20 21:45
 */
public class MedicineDelayCommand extends AbstractCommonDelayCommand {

    /**
     * 账号id
     */
    private String accountId;

    /**
     * 道具id
     * @param delay
     * @param accountId
     */
    private int itemModelId;

    public MedicineDelayCommand(long delay,String accountId, int itemModelId) {
        super(delay);
        this.accountId = accountId;
        this.itemModelId = itemModelId;
    }

    @Override
    public String getName() {
        return "MedicineDelayCommand";
    }

    @Override
    public void active() {
        MedicineCommand command = new MedicineCommand(accountId,itemModelId);
        SpringContext.getAccountExecutorService().submit(command);
    }
}
