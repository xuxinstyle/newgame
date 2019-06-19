package com.game.user.item.model;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 14:19
 */
public class MedicineEffect extends UseEffect {
    /**
     * 增加的属性
     */
    private Map<AttributeType, Attribute> addAttributeMap = new ConcurrentHashMap<>();

    @Override
    public void init(String effect){
        if(effect==null){
            return;
        }
        String[] split = effect.split(";");
        if(split==null){
            return;
        }
        for(String str:split){
            if(str==null||str.equals("")){
                continue;
            }
            String[] attr = str.split(",");
            AttributeType attributeType = AttributeType.valueOf(attr[0]);
            Attribute attribute = Attribute.valueOf(attributeType, Long.parseLong(attr[1]));
            this.addAttributeMap.put(AttributeType.valueOf(attr[0]),attribute);
        }
    }

    /**
     * TODO:加在玩家身上
     */
    @Override
    public void use(String acountId) {

    }

    public Map<AttributeType, Attribute> getAddAttributeMap() {
        return addAttributeMap;
    }


    public void setAddAttributeMap(Map<AttributeType, Attribute> addAttributeMap) {
        this.addAttributeMap = addAttributeMap;
    }
}
