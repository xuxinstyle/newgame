package com.game.scence.fight.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.executor.ICommand;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.skill.model.SkillInfo;
import com.game.role.skill.model.SkillSlot;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.scence.visible.model.Position;
import com.game.util.StringUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 12:16
 */
public abstract class CreatureUnit extends BaseUnit{

    /**
     * buff属性容器
     */
    /**
     * 账号id
     */
    private String accountId;
    /**
     * 属性容器
     */
    private CreatureAttributeContainer attributeContainer;
    /**
     * 战斗中可使用的技能
     */
    private SkillInfo skillInfo;
    /**
     * 当前血量
     */
    private long currHp;
    /**
     * 当前蓝量
     */
    private long currMp;

    /**
     * 是否死亡
     */
    private boolean isDead;
    /**
     * 战斗单元身上的复活command
     */
    private Map<Class<? extends ICommand>, ICommand> commandMap = new HashMap<>();
    /**
     * 技能cd command  《技能id,技能cd 的command》
     */
    private Map<Integer,ICommand> cdCommandMap = new HashMap<>();

    /**
     * 技能是否再cd状态
     */
    private boolean isSkillCd;

    public void useSkill(int skillId){
        ICommand command = cdCommandMap.get(skillId);
        SkillSlot skillSlot = skillInfo.getSkillSlotMap().get(skillId);
        if(skillSlot==null){
            return;
        }
        int level = skillSlot.getLevel();
        SkillLevelResource skillLevelResource = SpringContext.getSkillService().getSkillLevelResource(skillId + StringUtil.XIA_HUA_XIAN + level);

        setSkillCd(true);
    }
    public void putCommand(ICommand command){
        ICommand iCommand = commandMap.get(command.getClass());
        if(iCommand!=null){
            iCommand.cancel();
        }
        commandMap.put(command.getClass(),command);
    }
    public void clearAllCommand(){
        for(ICommand command:commandMap.values()){
            command.cancel();
        }
        commandMap.clear();
        for(ICommand command:cdCommandMap.values()){
            command.cancel();
        }
        cdCommandMap.clear();
    }

    public Map<Class<? extends ICommand>, ICommand> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<Class<? extends ICommand>, ICommand> commandMap) {
        this.commandMap = commandMap;
    }

    public long getReviveCd(){
        return 0L;
    }
    public SkillInfo getSkillInfo() {
        return skillInfo;
    }

    public void setSkillInfo(SkillInfo skillInfo) {
        this.skillInfo = skillInfo;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public boolean isDead() {
        return isDead;
    }

    public void setDead(boolean dead) {
        isDead = dead;
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

    public CreatureAttributeContainer getAttributeContainer() {
        return attributeContainer;
    }

    public void setAttributeContainer(CreatureAttributeContainer attributeContainer) {
        this.attributeContainer = attributeContainer;
    }
    public long getMaxHp(){
        Attribute attribute = attributeContainer.getFinalAttributes().get(AttributeType.MAX_HP);
        if(attribute==null){
            return 0L;
        }
        return attribute.getValue();
    }
    public long getMaxMp(){
        Attribute attribute = attributeContainer.getFinalAttributes().get(AttributeType.MAX_MP);
        if(attribute==null){
            return 0L;
        }
        return attribute.getValue();
    }



    public boolean isSkillCd() {

        return isSkillCd;
    }

    public void setSkillCd(boolean skillCd) {
        isSkillCd = skillCd;
    }

    public Map<Integer, ICommand> getCdCommandMap() {
        return cdCommandMap;
    }

    public void setCdCommandMap(Map<Integer, ICommand> cdCommandMap) {
        this.cdCommandMap = cdCommandMap;
    }
    public void putCdCommand(int skillId,ICommand command){
        ICommand cdCommand = cdCommandMap.get(skillId);
        if(cdCommand!=null){
            cdCommand.cancel();
        }
        cdCommandMap.put(skillId,command);
    }
}
