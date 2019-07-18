package com.game.role.equipstren.resource;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.util.JsonUtils;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO:由于没加缓存所以没有使用不可变属性
 * @Author：xuxin
 * @Date: 2019/7/1 20:52
 */
@LoadResource
public class EquipStrenResource {
    /**
     * 唯一id
     */
    private String id;
    /**
     * 装备位置
     */
    private int position;
    /**
     * 装备品质
     */
    private int quality;
    /**
     * 强化等级
     */
    private int level;
    /**
     * 该等级的属性
     */
    private String attrs;

    @Analyze("analyzeAttr")
    private List<Attribute> attributeList;

    /**
     * 这个方法有点鸡肋，只能解析一个Attribute
     */
    public void analyzeAttr(){
        if("".equals(attrs)||attrs==null){
            return;
        }
        String substring = attrs.substring(2, attrs.length() - 2);
        String[] split = substring.split(",");
        String[] type = split[0].split(":");
        AttributeType attributeType = AttributeType.valueOf(type[1].substring(1, type[1].length() - 1));
        String[] value = split[1].split(":");
        long attrValue = Long.parseLong(value[1].substring(1, value[1].length() - 1));
        Attribute attribute = Attribute.valueOf(attributeType, attrValue);
        if (attributeList == null) {
            attributeList = new ArrayList<>();
        }
        attributeList.add(attribute);

    }
    /**
     * 强化成功率
     */
    private int succRate;

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getSuccRate() {
        return succRate;
    }

    public void setSuccRate(int succRate) {
        this.succRate = succRate;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getAttrs() {
        return attrs;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
}
