package com.game.role.skill.resource;


import com.game.base.skill.model.PassiveEffect;
import com.game.user.condition.model.UpgradeSkillCondition;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 14:11
 */
@LoadResource
public class SkillLevelResource {
    /**
     * 唯一标识id 技能表id_level
     */
    private String id;
    /**
     * 技能等级
     */
    private int level;
    /**
     * 伤害类型 1 物理 2魔法
     */
    private String hurtType;
    /**
     * 技能目标数量
     */
    private int targetNum;
    /**
     * 使用距离
     */
    private int useDis;
    /**
     * 使用半径范围
     */
    private long useRangeRadius;
    /**
     * 技能基础伤害
     */
    private long hurt;
    /**
     * 技能属性加成伤害百分比
     */
    private long prop;
    /**
     * 技能释放cd 普攻的技能cd为攻速
     */
    private long cd;
    /**
     * 前置技能id
     */
    private String preSkillId;

    private String upgradeSkillConditionStr;
    /**
     * 升级技能条件
     */
    @Analyze("analyzeUpgradeSkillCondition")
    private UpgradeSkillCondition upgradeSkillCondition;
    /**
     * 被动效果
     */
    private String passiveEffectStr;
    /**
     * 被动技能持续时间
     */
    private long duration;
    /**
     * 技能消耗MP
     */
    private long consume;
    /**
     * 描述
     */
    private String describe;
    /**
     * 被动效果
     */
    @Analyze("analyzePassiveEffect")
    private PassiveEffect passiveEffect;


    public void analyzePassiveEffect(){
        if(passiveEffectStr==null){
            return;
        }
        this.passiveEffect = PassiveEffect.valueOf(passiveEffectStr);
    }

    public void analyzeUpgradeSkillCondition(){
        if(upgradeSkillConditionStr==null){
            return;
        }
        int level = Integer.parseInt(upgradeSkillConditionStr);
        upgradeSkillCondition = UpgradeSkillCondition.valueOf(level);
    }

    public long getConsume() {
        return consume;
    }

    public void setConsume(long consume) {
        this.consume = consume;
    }

    public String getPassiveEffectStr() {
        return passiveEffectStr;
    }

    public void setPassiveEffectStr(String passiveEffectStr) {
        this.passiveEffectStr = passiveEffectStr;
    }

    public PassiveEffect getPassiveEffect() {
        return passiveEffect;
    }

    public void setPassiveEffect(PassiveEffect passiveEffect) {
        this.passiveEffect = passiveEffect;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPreSkillId() {
        return preSkillId;
    }

    public void setPreSkillId(String preSkillId) {
        this.preSkillId = preSkillId;
    }

    public String getUpgradeSkillConditionStr() {
        return upgradeSkillConditionStr;
    }

    public void setUpgradeSkillConditionStr(String upgradeSkillConditionStr) {
        this.upgradeSkillConditionStr = upgradeSkillConditionStr;
    }

    public UpgradeSkillCondition getUpgradeSkillCondition() {
        return upgradeSkillCondition;
    }

    public void setUpgradeSkillCondition(UpgradeSkillCondition upgradeSkillCondition) {
        this.upgradeSkillCondition = upgradeSkillCondition;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getHurtType() {
        return hurtType;
    }

    public void setHurtType(String hurtType) {
        this.hurtType = hurtType;
    }

    public int getTargetNum() {
        return targetNum;
    }

    public void setTargetNum(int targetNum) {
        this.targetNum = targetNum;
    }

    public int getUseDis() {
        return useDis;
    }

    public void setUseDis(int useDis) {
        this.useDis = useDis;
    }

    public long getUseRangeRadius() {
        return useRangeRadius;
    }

    public void setUseRangeRadius(long useRangeRadius) {
        this.useRangeRadius = useRangeRadius;
    }

    public long getHurt() {
        return hurt;
    }

    public void setHurt(long hurt) {
        this.hurt = hurt;
    }

    public long getProp() {
        return prop;
    }

    public void setProp(long prop) {
        this.prop = prop;
    }

    public long getCd() {
        return cd;
    }

    public void setCd(long cd) {
        this.cd = cd;
    }
}
