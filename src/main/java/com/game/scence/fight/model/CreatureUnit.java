package com.game.scence.fight.model;

import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.container.CreatureAttributeContainer;
import com.game.base.executor.ICommand;
import com.game.role.skill.command.ReviveCreatureCommand;
import com.game.role.skill.command.SkillCdCommand;
import com.game.role.skill.model.SkillInfo;
import com.game.role.skill.packet.SM_CreatureDead;
import com.game.role.skill.packet.SM_CreatureRevive;
import com.game.role.skill.packet.SM_SkillStatus;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.game.role.skilleffect.model.AbstractSkillEffect;
import com.game.scence.base.model.AbstractScene;
import com.game.role.skilleffect.packet.SM_CreatureHurt;
import com.game.util.SendPacketUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 12:16
 */
public abstract class CreatureUnit extends BaseUnit{

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
     * 战斗单元身上的复活command 和
     * FIXME: 这个map暂时只放了复活的command
     */
    private Map<Class<? extends ICommand>, ICommand> commandMap = new HashMap<>();
    /**
     * 技能cd command  《技能id,技能cd 的command》
     */
    private Map<Integer,ICommand> cdCommandMap = new HashMap<>();
    /**
     * 玩家的属性buff的command
     */
    private Map<AttributeId, ICommand> buffCommandMap = new HashMap<>();
    /**
     * 技能是否再cd状态
     */
    private Map<Integer, Boolean> skillCdMap = new HashMap<>();

    public void putSkillCd(int skillId, boolean status) {
        skillCdMap.put(skillId, status);
    }

    public Boolean getSkillCdStatus(int skillId) {
        return skillCdMap.get(skillId);
    }

    public boolean consumeMpAndCheck(long consumeMp) {
        if (this.currMp < consumeMp) {
            return false;
        }
        this.currMp = this.currMp - consumeMp;
        return true;
    }

    /**
     * 调用之前需要检查是否充足
     *
     * @param consumeHp
     */
    public void consumeHpAndDoDead(long consumeHp, CreatureUnit attacker) {
        if (this.isDead) {
            return;
        }
        if (this.currHp <= consumeHp) {

            AbstractScene scene = SpringContext.getScenceSerivce().getScene(getMapId());
            List<String> accountIds = scene.getAccountIds();
            // 通知客户端
            for (String accountId : accountIds) {
                SendPacketUtil.send(accountId, SM_CreatureHurt.valueOf(this, this.currHp));
            }
            this.setDead(true);
            this.currHp = 0;
            // 清空生物的状态
            afterDead();
            doDropHandle(attacker);
            doDelayRevive();
            return;
        }
        this.currHp = this.currHp - consumeHp;
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(getMapId());
        List<String> accountIds = scene.getAccountIds();
        for (String accountId : accountIds) {
            SendPacketUtil.send(accountId, SM_CreatureHurt.valueOf(this, consumeHp));
        }
        doAttackAfter(attacker);
    }

    protected abstract void doAttackAfter(CreatureUnit attacker);

    public void doDropHandle(CreatureUnit attacker) {

    }

    public void doDelayRevive() {
        /**
         * 抛一个定时器 定时复活怪物
         */
        ReviveCreatureCommand command = ReviveCreatureCommand.valueOf(getMapId(), getReviveCd(), getAccountId(), this);
        putCommand(command);
        SpringContext.getSceneExecutorService().submit(command);
    }

    public void doRevive() {
        this.setCurrHp(this.getMaxHp());
        this.setCurrMp(this.getMaxMp());
        this.setDead(false);
        /**
         * 通知客户端
         */
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(getMapId());
        List<String> accountIds = scene.getAccountIds();
        SM_CreatureRevive sm = SM_CreatureRevive.valueOf(this);
        for (String accountId : accountIds) {
            SendPacketUtil.send(accountId, sm);
        }
    }

    /**
     * 这里的效果暂时做成强制替换成新的buff
     *
     * @param attrId
     * @param command
     */
    public void putBuffCommand(AttributeId attrId, ICommand command) {
        ICommand buffCommand = buffCommandMap.get(attrId);
        if (buffCommand != null) {
            buffCommand.cancel();
        }
        buffCommandMap.put(attrId, command);
    }

    public void removeBuffCommand(AttributeId attrId) {
        ICommand command = buffCommandMap.get(attrId);
        if (command != null) {
            command.cancel();
        }
        buffCommandMap.remove(attrId);
    }

    public Map<AttributeId, ICommand> getBuffCommandMap() {
        return buffCommandMap;
    }

    public void setBuffCommandMap(Map<AttributeId, ICommand> buffCommandMap) {
        this.buffCommandMap = buffCommandMap;
    }

    public Map<Integer, Boolean> getSkillCdMap() {
        return skillCdMap;
    }

    public void setSkillCdMap(Map<Integer, Boolean> skillCdMap) {
        this.skillCdMap = skillCdMap;
    }

    public void putCdCommand(int skillId, ICommand command) {
        ICommand cdCommand = cdCommandMap.get(skillId);
        if (cdCommand != null) {
            cdCommand.cancel();
        }
        cdCommandMap.put(skillId, command);
    }

    /**
     * 清除技能cd
     *
     * @param skillId
     */
    public void clearSkillCd(int skillId) {
        ICommand command = cdCommandMap.get(skillId);
        if (command == null) {
            return;
        }
        command.cancel();
        /**
         * fixme:这里要清除cd的command吗？
         */
        cdCommandMap.remove(skillId);
        skillCdMap.put(skillId, false);
        SendPacketUtil.send(getAccountId(), SM_SkillStatus.valueOf(skillId, 1));
    }

    public void putCommand(ICommand command){
        ICommand iCommand = commandMap.get(command.getClass());
        if(iCommand!=null){
            iCommand.cancel();
        }
        commandMap.put(command.getClass(), command);
    }

    public void afterDead() {
        /**
         * 清空状态
         */
        for (ICommand command : cdCommandMap.values()) {
            command.cancel();
        }
        cdCommandMap.clear();
        for (ICommand command : buffCommandMap.values()) {
            command.cancel();
        }
        buffCommandMap.clear();
        skillCdMap.clear();
        /**
         * 通知客户端
         */
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(getMapId());
        List<String> accountIds = scene.getAccountIds();
        for (String accountId : accountIds) {
            SendPacketUtil.send(accountId, SM_CreatureDead.valueOf(getMapId(), this));
        }
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
        for (ICommand command : buffCommandMap.values()) {
            command.cancel();
        }
        buffCommandMap.clear();
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

    public Map<Integer, ICommand> getCdCommandMap() {
        return cdCommandMap;
    }

    public void setCdCommandMap(Map<Integer, ICommand> cdCommandMap) {
        this.cdCommandMap = cdCommandMap;
    }

    /**
     * @param skillResource
     * @param skillLevelResource
     * @param useUnit
     */
    public void useSkillAfter(SkillResource skillResource, SkillLevelResource skillLevelResource, CreatureUnit useUnit) {

        /**
         * 抛出技能cd的command
         */
        SkillCdCommand command = SkillCdCommand.valueOf(getMapId(), skillResource.getId(), skillLevelResource.getCd(), useUnit.getAccountId(), useUnit);
        SpringContext.getSceneExecutorService().submit(command);
        putCdCommand(skillResource.getId(), command);
        putSkillCd(skillResource.getId(), true);

    }

    public void usSkill(SkillLevelResource skillLevelResource, CreatureUnit targetUnit, SkillResource skillResource) {
        int[] effects = skillLevelResource.getEffects();
        for (int effectId : effects) {
            AbstractSkillEffect skillEffect = SpringContext.getEffectService().getSkillEffect(effectId);
            skillEffect.doActive(getMapId(), this, targetUnit, skillLevelResource, skillResource);
            if (targetUnit.isDead()) {
                break;
            }
        }
    }
}
