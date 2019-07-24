package com.game.scence.drop.command;

import com.game.SpringContext;
import com.game.base.executor.account.impl.AbstractAccountCommand;
import com.game.scence.monster.resource.MonsterResource;
import com.game.user.item.model.AbstractItem;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/16 21:54
 */
public class DropItemAddCommand extends AbstractAccountCommand {
    /**
     * 怪物资源
     */
    private MonsterResource monsterResource;

    private int job;

    public static DropItemAddCommand valueOf(String accountId, MonsterResource monsterResource, int job) {
        DropItemAddCommand command = new DropItemAddCommand(accountId);
        command.setMonsterResource(monsterResource);
        command.setJob(job);
        return command;
    }

    public DropItemAddCommand(String accountId) {
        super(accountId);
    }

    @Override
    public String getName() {
        return "DropItemAddCommand";
    }

    @Override
    public void active() {
        MonsterResource monsterResource = getMonsterResource();
        List<AbstractItem> dropItems = SpringContext.getDropService().getRandDropItems(monsterResource, job);
        if (dropItems == null) {
            return;
        }
        SpringContext.getItemService().awardToPack(getAccountId(), dropItems);
    }

    public MonsterResource getMonsterResource() {
        return monsterResource;
    }

    public void setMonsterResource(MonsterResource monsterResource) {
        this.monsterResource = monsterResource;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }
}
