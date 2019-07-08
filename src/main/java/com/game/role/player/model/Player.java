package com.game.role.player.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeIdEnum;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.gameobject.constant.ObjectType;
import com.game.base.gameobject.model.Creature;
import com.game.role.player.resource.PlayerLevelResource;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.model.Position;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.stereotype.Component;

import java.beans.Transient;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

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
    private Position position;
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
     * 玩家场景id
     */
    private int currSceneId;

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
        player.setCurrMapId(MapType.NoviceVillage.getMapId());
        player.setLastLogoutMapId(MapType.NoviceVillage.getMapId());
        // TODO: BUFF容器

        player.setAttributeContainer(new PlayerAttributeContainer());
        /** 生成玩家基础属性*/
        PlayerLevelResource resource = SpringContext.getPlayerSerivce().getPlayerLevelResource(player.getLevel());
        List<Attribute> baseAttributeList = resource.getBaseAttributeList();
        player.getAttributeContainer().putAndComputeAttributes(AttributeIdEnum.BASE,baseAttributeList);
        return player;
    }

    public int getCurrSceneId() {
        return currSceneId;
    }

    public void setCurrSceneId(int currSceneId) {
        this.currSceneId = currSceneId;
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
    public AccountInfo getAccount(String accountId){
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        return accountEnt.getAccountInfo();
    }
    public boolean isChangeMap(){
        return getAccount(this.getAccountId()).getIsChangeMap().get();
    }
    public void setChangeMapId(boolean changeMapId){
        getAccount(this.getAccountId()).getIsChangeMap().getAndSet(changeMapId);
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
        this.attributeContainer = attributeContainer;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
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
