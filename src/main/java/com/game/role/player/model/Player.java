package com.game.role.player.model;

import com.game.base.gameObject.constant.ObjectType;
import com.game.base.gameObject.model.Creature;
import com.game.role.constant.Job;
import org.springframework.stereotype.Component;

@Component
public class Player extends Creature<Player> {

    /**
     * 账号Id
     */
    private String accountId;
    /**
     * 角色姓名
     */
    private String playerName;
    /**
     *  玩家职业
     */
    private int playerJob;
    /**
     * 玩家等级
     */
    private int level;
    /**
     *  玩家的生存状态 生存，死亡 1 生存，2 死亡
     */
    private int surviveStatus;
    /**
     * 角色当前位置x坐标
     */
    private int x;
    /**
     * 角色当前位置y坐标
     */
    private int y;
    /**
     * 玩家当前等级拥有的经验
     */
    private long exp;

    /**
     *
     * @param playerId
     * @param accountId
     * @param type
     * @return
     */
    public static Player valueOf(long playerId, String accountId, int type, String nickName){
        Player player = new Player();
        player.setLevel(1);
        player.setPlayerJob(type);
        player.setObjectId(playerId);
        player.setAccountId(accountId);
        player.setPlayerName(nickName);
        player.setSurviveStatus(1);
        player.setX(0);
        player.setY(0);
        return player;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
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

    public int getPlayerJob() {
        return playerJob;
    }

    public void setPlayerJob(int playerJob) {
        this.playerJob = playerJob;
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

    @Override
    public ObjectType getObjectType() {
        return ObjectType.PLAYER;
    }

    @Override
    public String getName() {
        return playerName;
    }


}
