package com.game.user.task.model;

import com.game.role.player.model.Player;
import com.game.user.task.constant.TaskConditionType;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/26 17:35
 */
@Component
public class PassMapProcessor extends AbstractProcessor {
    @Override
    protected TaskConditionType getTaskConditionType() {
        return TaskConditionType.PASS_MAP;
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
        if (conDef.getType() != type) {
            return false;
        }
        if (!conDef.getValues()[1].equals(key)) {
            return false;
        }
        return true;
    }
}
