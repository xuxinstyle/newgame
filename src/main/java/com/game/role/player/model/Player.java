package com.game.role.player.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.AttributeIdEnum;
import com.game.base.attribute.CreatureAttributeContainer;
import com.game.base.gameobject.constant.ObjectType;
import com.game.base.gameobject.model.Creature;
import com.game.role.player.resource.PlayerLevelResource;
import com.game.scence.constant.SceneType;
import com.game.scence.model.PlayerPosition;
import org.springframework.stereotype.Component;

import java.util.List;

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
    private PlayerPosition position;
    /**
     * 玩家当前等级拥有的经验
     */
    private long exp;
    /**
     * 上次登出时所在场景地图 默认新手村
     */
    private int lastLogoutMapId;
    /**
     * 玩家当前所在的地图
     */
    private int currMapId;

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
        player.setCurrentMapType(SceneType.NoviceVillage.getMapId());
        player.setLastLogoutMapType(SceneType.NoviceVillage.getMapId());
        // TODO: BUFF容器
        player.setAttributeContainer(new PlayerAttributeContainer());
        /** 生成玩家基础属性*/
        PlayerLevelResource resource = SpringContext.getPlayerSerivce().getPlayerLevelResource(player.getLevel());
        List<Attribute> baseAttributeList = resource.getBaseAttributeList();
        player.getAttributeContainer().putAndComputeAttributes(AttributeIdEnum.LEVEL,baseAttributeList);
        return player;
    }

    public int getLastLogoutMapType() {
        return lastLogoutMapId;
    }

    public void setLastLogoutMapType(int lastLogoutMapId) {
        this.lastLogoutMapId = lastLogoutMapId;
    }

    public int getCurrentMapType() {
        return currMapId;
    }

    public void setCurrentMapType(int mapId) {
        this.currMapId = mapId;
    }

    public long getExp() {
        return exp;
    }

    public void setExp(long exp) {
        this.exp = exp;
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

    @Override
    public CreatureAttributeContainer getAttributeContainer() {
        return  attributeContainer;
    }

    @Override
    public void setAttributeContainer(CreatureAttributeContainer attributeContainer) {
        this.attributeContainer = (PlayerAttributeContainer)attributeContainer;
    }

    public PlayerPosition getPosition() {
        return position;
    }

    public void setPosition(PlayerPosition position) {
        this.position = position;
    }

    public int getLastLogoutMapId() {
        return lastLogoutMapId;
    }

    public void setLastLogoutMapId(int lastLogoutMapId) {
        this.lastLogoutMapId = lastLogoutMapId;
    }

    public int getCurrMapId() {
        return currMapId;
    }

    public void setCurrMapId(int currMapId) {
        this.currMapId = currMapId;
    }
}
