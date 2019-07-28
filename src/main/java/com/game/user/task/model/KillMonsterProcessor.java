package com.game.user.task.model;

import com.game.role.player.model.Player;
import com.game.user.task.constant.TaskConditionType;
import org.springframework.stereotype.Component;

/**
 * 杀死怪物事件处理器
 *
 * @Author：xuxin
 * @Date: 2019/7/26 16:34
 */
@Component
public class KillMonsterProcessor extends AbstractProcessor {
    @Override
    protected TaskConditionType getTaskConditionType() {
        return TaskConditionType.KILL_MONSTER;
    }

    @Override
    public boolean initProgress(Task task, Player player, int index, TaskConDef def) {
        return false;
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
        if (conDef.getType() != type) {
            return false;
        }
        if (!conDef.getValues()[1].equals(key)) {
            return false;
        }
        return true;
    }
}
