package com.game.user.condition.model;

import com.game.SpringContext;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/6/18 12:16
 */
public class EquipCondition extends Condition {
    /**
     * 职业 如果为0则不限条件
     */
    private int career;
    /**
     * 等级
     */
    private int level;

    @Override
    public boolean checkCondition(String accountId) {

        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        if(player.getLevel()< this.level){
            return false;
        }
        if(player.getPlayerJob()!=this.career){
            return false;
        }
        return true;
    }

    public int getCareer() {
        return career;
    }

    public void setCareer(int career) {
        this.career = career;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
