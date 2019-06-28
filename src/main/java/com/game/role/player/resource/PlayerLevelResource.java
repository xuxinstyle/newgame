package com.game.role.player.resource;

import com.game.common.attribute.Attribute;
import com.game.common.attribute.constant.AttributeType;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
    private List<Attribute> baseAttributeList;

    public void analyze(){
        String[] split = baseAttribute.split(";");
        List<Attribute> baseAttributeList = new ArrayList<>();
        for(String str:split){
            String[] attr = str.split(",");
            AttributeType attributeType = AttributeType.valueOf(attr[0]);
            long value = Long.parseLong(attr[1]);
            Attribute attribute = Attribute.valueOf(attributeType, value);
            baseAttributeList.add(attribute);
        }
        this.baseAttributeList = baseAttributeList;
    }

    public List<Attribute> getBaseAttributeList() {
        return baseAttributeList;
    }

    public void setBaseAttributeList(List<Attribute> baseAttributeList) {
        this.baseAttributeList = baseAttributeList;
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
