package com.game.base.player.model;

import com.game.base.gameObject.GameObject;
import com.game.role.constant.Job;
import com.game.scence.constant.SceneType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;

@Component
public class Player extends GameObject {

    // 账号Id
    private String accountId;
    // 角色姓名
    private String playerName;
    /**
     *  玩家职业
     */
    private int career;
    /**
     * 玩家等级
     */
    private int level;
    /**
     *  玩家的生存状态 生存，死亡 1 生存，2 死亡
     */
    private int surviveStatus;
    public static Player valueOf(long playerId, String accountId, Job type){
        Player player = new Player();
        player.setLevel(1);
        player.setCareer(type.getJobType());
        player.setObjectId(playerId);
        player.setAccountId(accountId);
        player.setPlayerName(type.name());
        player.setSurviveStatus(1);
        return player;
    }

    @Override
    public String gatName() {
        return playerName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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

    public int getSurviveStatus() {
        return surviveStatus;
    }

    public void setSurviveStatus(int surviveStatus) {
        this.surviveStatus = surviveStatus;
    }
}
