package com.game.base.attribute;

import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.util.AttributeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * FIXME:注意每次计算属性后都要保存
 *
 * @Author：xuxin
 * @Date: 2019/6/12 17:19
 */
public abstract class AttributeContainer {

    private static final Logger logger = LoggerFactory.getLogger(AttributeContainer.class);
    /**
     * 属性容器的拥有者
     */

    public AttributeContainer(){

    }

    /**
     * 当前属性计算了二级属性的属性集合
     */
    protected Map<AttributeType, Attribute> finalAttributes = new ConcurrentHashMap<>();
    /**
     * 模块属性
     */

    protected Map<String, AttributeSet> moduleAttributeSet = new HashMap<>();
    /**
     * 用于累加计算个模块的属性结果的变量
     */

    protected Map<AttributeType, Attribute> accumulateAttributes = new HashMap<>();

    /**
     * 设置该模块属性但不重新计算
     * @param id
     * @param attrs
     */
    public void putAttributes(AttributeId id, List<Attribute> attrs){
        if(attrs == null){
            logger.error("设置属性不能为空！{}",id);
            return;
        }else{
            putAttributes0(id,attrs,null);
        }
    }
    /**
     * 设置该模块的属性 并且重新计算
     * @param id
     * @param attrs
     * @param needSync
     */
    public void putAndCcomputeAttributes(AttributeId id, List<Attribute> attrs, boolean needSync){
        if(attrs == null){
            logger.error("设置属性不能为空！"+id);
            return;
        }else if(moduleAttributeSet.containsKey(id.toString())||attrs.size()>0){
            AttributeUpdate records = new AttributeUpdate(id);
            putAttributes0(id,attrs,records);
            recompute(records,needSync);
        }

    }
    protected abstract void recompute(AttributeUpdate records, boolean needSync);
    /**
     * 把传进来的属性放进模块modelAttributeSet中,并设置改变的属性类型，并清除以前该模块的属性
     * @param id
     * @param attrs
     * @param records
     */
    public void putAttributes0(AttributeId id, List<Attribute> attrs,AttributeUpdate records) {
        if(records!=null){
            AttributeSet oldAttrs = moduleAttributeSet.get(id.toString());
            records.addAttrs(attrs);
            if(oldAttrs != null){
                records.addAttrs(oldAttrs.getAttributeMap().values());
            }
        }
        if(attrs.size() == 0){
            moduleAttributeSet.remove(id.toString());
        }else{
            accumulateAttributeModelAttribute(id,attrs);
        }
    }

    /**
     * 将属性放到对应的属性模块中
     * @param id
     * @param attrs
     */
    private void accumulateAttributeModelAttribute(AttributeId id, List<Attribute> attrs) {
        AttributeSet attributeSet = moduleAttributeSet.get(id.toString());
        if(attributeSet == null){
            attributeSet = new AttributeSet();
            moduleAttributeSet.put(id.toString(),attributeSet);
        }
        //将原来的属性清空覆盖掉
        attributeSet.getAttributeMap().clear();
        if(attrs !=null){
            AttributeUtil.accumulateToMap(attrs, attributeSet.getAttributeMap());
        }
    }

    /**
     * 场景中需要加同步
     * @param id
     * @param needSync
     */
    public void removeAndRecompteAttribtues(AttributeId id, boolean needSync){
        AttributeSet oldAttrs = moduleAttributeSet.get(id.toString());
        if(removeAttributes(id)){
            AttributeUpdate records = new AttributeUpdate(id);
            if(oldAttrs!=null){
                records.addAttrs(oldAttrs.getAttributeMap().values());
            }
            recompute(records,needSync);
        }
    }

    public boolean removeAttributes(AttributeId id){
        return moduleAttributeSet.remove(id.toString())!=null;
    }

    public Map<AttributeType, Attribute> getFinalAttributes() {
        return finalAttributes;
    }

}
