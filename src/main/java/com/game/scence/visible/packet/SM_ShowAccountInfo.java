package com.game.scence.visible.packet;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.visible.model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/6 10:11
 */
public class SM_ShowAccountInfo {

    /**
     * 账号Id
     */
    private String accountId;
    /**
     * 账号昵称
     */
    private String nickName;
    /**
     * 职业
     */
    private int career;
    /**
     * 等级
     */
    private int level;

    /**
     * 角色名
     */
    private String playerName;
    /*
     * 位置
     *
     */
    private Position position;

    /**
     * 属性
     */
    private List<Attribute> attributeList;

    /**
     * 当前血量
     */
    private long currHp;

    /**
     * 当前mp
     */
    private long currMp;

    public static SM_ShowAccountInfo valueOf(Player player){
        SM_ShowAccountInfo sm = new SM_ShowAccountInfo();
        sm.setAccountId(player.getAccountId());
        sm.setCareer(player.getPlayerJob());
        sm.setLevel(player.getLevel());
        sm.setPlayerName(player.getPlayerName());
        sm.setPosition(player.getPosition());
        CreatureAttributeContainer attributeContainer = player.getAttributeContainer();
        Map<AttributeType, Attribute> finalAttributes = attributeContainer.getFinalAttributes();
        List<Attribute> attrs = new ArrayList<>(finalAttributes.values());
        sm.setAttributeList(attrs);
        sm.setCurrHp(finalAttributes.get(AttributeType.MAX_HP).getValue());
        sm.setCurrMp(finalAttributes.get(AttributeType.MAX_MP).getValue());
        return sm;
    }
    public static SM_ShowAccountInfo valueOf(PlayerUnit unit){
        SM_ShowAccountInfo sm = new SM_ShowAccountInfo();
        sm.setCurrMp(unit.getCurrMp());
        sm.setCurrHp(unit.getCurrHp());
        Map<AttributeType, Attribute> finalAttributes = unit.getAttributeContainer().getFinalAttributes();
        List<Attribute> attrs = new ArrayList<>(finalAttributes.values());
        sm.setAttributeList(attrs);
        sm.setPosition(unit.getPosition());
        sm.setPlayerName(unit.getVisibleName());
        sm.setLevel(unit.getLevel());
        sm.setCareer(unit.getJobId());
        sm.setAccountId(unit.getAccountId());
        return sm;
    }
    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }

    public long getCurrHp() {
        return currHp;
    }

    public void setCurrHp(long currHp) {
        this.currHp = currHp;
    }

    public long getCurrMp() {
        return currMp;
    }

    public void setCurrMp(long currMp) {
        this.currMp = currMp;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
