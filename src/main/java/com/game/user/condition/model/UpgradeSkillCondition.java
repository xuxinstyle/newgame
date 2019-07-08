package com.game.user.condition.model;

import com.game.SpringContext;
import com.game.role.player.model.Player;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 20:11
 */
public class UpgradeSkillCondition extends AbstractCondition {
    /**
     * 角色等级
     */
    private int level;

    public static UpgradeSkillCondition valueOf(int level){
        UpgradeSkillCondition condition = new UpgradeSkillCondition();
        condition.setLevel(level);
        return condition;
    }
    @Override
    public boolean checkCondition(Player player, Map<String, Object> param) {
        if(player.getLevel()>=level){
            return true;
        }
        return false;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
