package com.game.user.item.resource;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.user.item.constant.UseEffectType;
import com.game.user.item.model.UseEffect;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 18:13
 */
@LoadResource
@Component
public class ItemResource {
    /**道具唯一标识id*/
    private int id;
    /**道具名*/
    private String name;
    /**道具类型*/
    private String itemType;
    /** 道具品质*/
    private int quality;
    /**装备类型*/
    private String equipmentType;
    /**是否需要使用*/
    private boolean needToUse;
    /** 使用等级*/
    private int useLevel;
    /** 能否出售*/
    private int isCanSell;
    /**售价*/
    private int sellPrice;
    /** 使用条件*/
    private String conditions;
    /** 是否需要指定角色*/
    private boolean isUseToPlayer;

    /**最大堆叠数*/
    private int overLimit;
    /** 使用职业要求*/
    private int jobLimit;
    /**
     * 使用效果 配置表中配置格式：使用时限\增加的属性;增加的属性\增加的经验
     */
    @Analyze("analyzeEffect")
    private String effect;

    private UseEffect useEffect;
    private boolean autoUse;

    /**
     * 配置表中一定要配置对应的符号，要么就
     */
    public void analyzeEffect(){
        if(effect==null){
            return;
        }
        String[] split = effect.split(":");
        if(split==null){
            return;
        }
        UseEffect useEffect = UseEffectType.valueOf(split[0]).create();
        useEffect.init(split[1]);
        this.useEffect = useEffect;
    }

    public boolean isAutoUse() {
        return autoUse;
    }

    public void setAutoUse(boolean autoUse) {
        this.autoUse = autoUse;
    }

    public UseEffect getUseEffect() {
        return useEffect;
    }

    public void setUseEffect(UseEffect useEffect) {
        this.useEffect = useEffect;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public int getJobLimit() {
        return jobLimit;
    }

    public void setJobLimit(int jobLimit) {
        this.jobLimit = jobLimit;
    }

    public int getUseLevel() {
        return useLevel;
    }

    public void setUseLevel(int useLevel) {
        this.useLevel = useLevel;
    }

    public int getIsCanSell() {
        return isCanSell;
    }

    public void setIsCanSell(int isCanSell) {
        this.isCanSell = isCanSell;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public boolean isUseToPlayer() {
        return isUseToPlayer;
    }

    public void setUseToPlayer(boolean useToPlayer) {
        isUseToPlayer = useToPlayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public String getEquipmentType() {
        return equipmentType;
    }

    public void setEquipmentType(String equipmentType) {
        this.equipmentType = equipmentType;
    }

    public boolean isNeedToUse() {
        return needToUse;
    }

    public void setNeedToUse(boolean needToUse) {
        this.needToUse = needToUse;
    }

    public int getOverLimit() {
        return overLimit;
    }

    public void setOverLimit(int overLimit) {
        this.overLimit = overLimit;
    }
}
