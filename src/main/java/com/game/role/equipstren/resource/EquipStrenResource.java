package com.game.role.equipstren.resource;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.ImmutableAttribute;
import com.resource.anno.LoadResource;

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
    private int id;
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
    private List<Attribute> attributeList;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<Attribute> getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(List<Attribute> attributeList) {
        this.attributeList = attributeList;
    }
}
