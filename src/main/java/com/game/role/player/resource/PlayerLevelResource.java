package com.game.role.player.resource;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/13 15:32
 */
@LoadResource
@Component
public class PlayerLevelResource {
    /**
     * 等级
     */
    private int level;
    /**
     * p升级所需经验
     */
    private long upLevelExp;
    /**
     * 基础属性
     */
    private String baseAttribute;

    @Analyze("analyze")
    private Map<AttributeType, Attribute> baseAttributeMap;

    public void analyze(){
        String[] split = baseAttribute.split(";");
        Map<AttributeType, Attribute> baseAttributeMap = new HashMap<>();
        for(String str:split){
            String[] attr = str.split(",");
            AttributeType attributeType = AttributeType.valueOf(attr[0]);

            long value = Long.parseLong(attr[1]);
            Attribute attribute = Attribute.valueOf(attributeType, value);
            baseAttributeMap.put(attributeType,attribute);
        }
        this.baseAttributeMap = baseAttributeMap;
    }

    public Map<AttributeType, Attribute> getBaseAttributeMap() {
        return baseAttributeMap;
    }

    public void setBaseAttributeMap(Map<AttributeType, Attribute> baseAttributeMap) {
        this.baseAttributeMap = baseAttributeMap;
    }

    public long getUpLevelExp() {
        return upLevelExp;
    }

    public void setUpLevelExp(long upLevelExp) {
        this.upLevelExp = upLevelExp;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public String getBaseAttribute() {
        return baseAttribute;
    }

    public void setBaseAttribute(String baseAttribute) {
        this.baseAttribute = baseAttribute;
    }
}
