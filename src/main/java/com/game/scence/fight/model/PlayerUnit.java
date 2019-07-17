package com.game.scence.fight.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.attribute.container.ModelAttribute;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.role.skill.entity.SkillEnt;
import com.game.role.skill.model.SkillInfo;
import com.game.scence.visible.resource.MapResource;

import java.util.ArrayList;
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


    public static PlayerUnit valueOf(Player player){
        PlayerUnit playerUnit = new PlayerUnit();
        playerUnit.setJobId(player.getPlayerJob());
        playerUnit.setLevel(player.getLevel());
        /**
         * 属性容器
         */
        playerUnit.setAttributeContainer(new CreatureAttributeContainer());
        CreatureAttributeContainer attributeContainer = playerUnit.getAttributeContainer();
        Map<String, ModelAttribute> modelAttributes = player.getAttributeContainer().getModelAttributes();
        for(ModelAttribute modelAttribute:modelAttributes.values()){
            Map<AttributeType, Attribute> attributeMap = modelAttribute.getAttributeMap();
            List<Attribute> attrs = new ArrayList<>(attributeMap.values());
            attributeContainer.putAndComputeAttributes(modelAttribute.getAttributeId(), attrs);
        }
        Map<AttributeType, Attribute> finalAttributes = attributeContainer.getFinalAttributes();
        /**
         * 技能
         */
        SkillEnt skillEnt = SpringContext.getSkillService().getSkillEnt(player.getObjectId());
        SkillInfo skillInfo = skillEnt.getSkillInfo();
        SkillInfo unitSkillInfo = skillInfo.deepCopy();
        /**
         * FIXME:这里直接放进去 如果玩家在账号线程池修改技能信息，则场景中的信息也跟着改
         */
        playerUnit.setSkillInfo(unitSkillInfo);
        playerUnit.setId(player.getObjectId());
        playerUnit.setPosition(player.getPosition());
        playerUnit.setVisibleName(player.getPlayerName());
        playerUnit.setType(ObjectType.PLAYER);
        playerUnit.setCurrHp(finalAttributes.get(AttributeType.MAX_HP).getValue());
        playerUnit.setCurrMp(finalAttributes.get(AttributeType.MAX_MP).getValue());
        playerUnit.setAccountId(player.getAccountId());
        playerUnit.setMapId(player.getCurrMapId());
        return playerUnit;
    }

    /**
     * TODO:未来将使用技能的方法封装在这里
     * @param skillId
     * @param targetUnit
     */
    public void useSkill(int skillId, CreatureUnit targetUnit){

    }

    @Override
    protected void doAttackAfter(CreatureUnit attacker) {

    }

    @Override
    public long getReviveCd() {
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(getMapId());
        return mapResource.getReviveCd();
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
