package com.game.user.task.model;

import com.game.role.player.model.Player;
import com.game.user.task.constant.TaskConditionType;
import com.game.user.task.resource.TaskResource;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 23:01
 */
@Component
public class TalkNpcProcessor extends AbstractProcessor {
    @Override
    protected TaskConditionType getTaskConditionType() {
        return TaskConditionType.NPC_TALK;
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
        String[] values = conDef.getValues();
        if (values[1].equals(key) && type == conDef.getType()) {
            return true;
        }
        return false;
    }
}
