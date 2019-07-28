package com.game.user.task.model;

import com.game.role.player.model.Player;
import com.game.user.task.constant.TaskConditionType;
import com.game.user.task.resource.TaskResource;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/26 20:21
 */
@Component
public class PlayerLevelUpProcessor extends AbstractProcessor {

    @Override
    protected TaskConditionType getTaskConditionType() {
        return TaskConditionType.PLAYER_LEVEL_UP;
    }

    @Override
    public boolean initProgress(Task task, Player player, int index, TaskConDef def) {
        task.getExcuteProgress()[index] = player.getLevel();
        return true;
    }


    @Override
    protected boolean isReplace() {
        return false;
    }

    @Override
    protected int getValue(TaskConditionType type, TaskConDef conDef) {
        return 1;
    }

    @Override
    protected boolean checkTaskCon(TaskConditionType type, TaskConDef conDef, String key) {
        if (type != conDef.getType()) {
            return false;
        }
        return true;
    }
}
