package com.game.scence.fight.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.attributeid.AttributeIdEnum;
import com.game.base.attribute.attributeid.MedicineAttributeId;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.attribute.container.ModelAttribute;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.constant.Job;
import com.game.role.player.model.Player;
import com.game.role.player.resource.PlayerLevelResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 16:18
 */
public class PlayerUnit extends CreatureUnit {
    /**
     * 职业
     */
    private int jobId;
    /**
     * 等级
     */
    private int level;
    /**
     * 玩家账号id
     */
    private String accountId;

    public static PlayerUnit valueOf(Player player){
        PlayerUnit playerUnit = new PlayerUnit();
        playerUnit.setJobId(player.getPlayerJob());
        playerUnit.setLevel(player.getLevel());
        playerUnit.setAttributeContainer(new CreatureAttributeContainer());
        CreatureAttributeContainer attributeContainer = playerUnit.getAttributeContainer();
        Map<String, ModelAttribute> modelAttributes = player.getAttributeContainer().getModelAttributes();
        for(ModelAttribute modelAttribute:modelAttributes.values()){
            Map<AttributeType, Attribute> attributeMap = modelAttribute.getAttributeMap();
            List<Attribute> attrs = new ArrayList<>(attributeMap.values());
            attributeContainer.putAndComputeAttributes(modelAttribute.getAttributeId(), attrs);
        }
        Map<AttributeType, Attribute> finalAttributes = attributeContainer.getFinalAttributes();
        playerUnit.setId(player.getObjectId());
        playerUnit.setPosition(player.getPosition());
        playerUnit.setVisibleName(player.getPlayerName());
        playerUnit.setType(ObjectType.PLAYER);
        playerUnit.setCurrHp(finalAttributes.get(AttributeType.MAX_HP).getValue());
        playerUnit.setCurrMp(finalAttributes.get(AttributeType.MAX_MP).getValue());
        playerUnit.setAccountId(player.getAccountId());
        return playerUnit;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
