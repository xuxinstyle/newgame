package com.game.user.task.model;

import com.game.role.player.model.Player;
import com.game.user.task.constant.TaskConditionType;
import org.springframework.stereotype.Component;

/**
 * 玩家登录的任务有点奇葩
 *
 * @Author：xuxin
 * @Date: 2019/7/25 16:54
 */
@Component
public class AccountLoginProcessor extends AbstractProcessor {

    @Override
    protected TaskConditionType getTaskConditionType() {
        return TaskConditionType.ACCOUNT_LOGIN;
    }

    @Override
    public boolean initProgress(Task task, Player player, int index, TaskConDef def) {
        return false;
    }

    @Override
    protected boolean isReplace() {
        return true;
    }

    @Override
    protected int getValue(TaskConditionType type, TaskConDef conDef) {
        return 1;
    }

    @Override
    protected boolean checkTaskCon(TaskConditionType type, TaskConDef conDef, String key) {
        if (type == conDef.getType()) {
            return true;
        }
        return false;
    }
}
