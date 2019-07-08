package com.game.user.condition.model;

import com.game.SpringContext;
import com.game.role.player.model.Player;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/18 12:16
 */
public class EquipCondition extends AbstractCondition {
    /**
     * 职业 如果为0则不限条件
     */
    private int career;
    /**
     * 等级
     */
    private int playerNeedLevel;

    @Override
    public boolean checkCondition(Player player,Map<String, Object> param) {
        if(player.getLevel()< this.playerNeedLevel){
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

    public int getPlayerNeedLevel() {
        return playerNeedLevel;
    }

    public void setPlayerNeedLevel(int playerNeedLevel) {
        this.playerNeedLevel = playerNeedLevel;
    }
}
