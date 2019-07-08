package com.game.base.attribute.container;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.attributeid.AttributeId;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.attribute.util.AttributeUtil;
import org.codehaus.jackson.annotate.JsonTypeInfo;
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
@JsonTypeInfo(use = JsonTypeInfo.Id.MINIMAL_CLASS)
public abstract class AbstractAttributeContainer {

    private static final Logger logger = LoggerFactory.getLogger(AbstractAttributeContainer.class);

    public AbstractAttributeContainer(){

    }

    /**
     * 当前属性   计算了一级属性和百分比属性的属性集合
     */
    protected Map<AttributeType, Attribute> finalAttributes = new ConcurrentHashMap<>();
    /**
     * 存放模块属性
     */
    protected Map<String, ModelAttribute> modelAttributes = new ConcurrentHashMap<>();
    /**
     * 各属性模块累加在一起的属性集合
     */
    protected Map<AttributeType, Attribute> collectAttributes = new ConcurrentHashMap<>();
    /**
     * 设置该模块的属性 并且重新计算
     * @param id
     * @param attrs
     */
    public void putAndComputeAttributes(AttributeId id, List<Attribute> attrs){
        if(attrs == null){
            logger.error("设置属性不能为空！"+id);
            return;
        }else if(modelAttributes.containsKey(id.toString())||attrs.size()>0){
            AttributeUpdateSet updateSet = new AttributeUpdateSet(id);
            setAttributeModel(id,attrs,updateSet);
            recompute(updateSet);
        }
    }

    /**
     * 重新计算属性
     * @param updateSet
     */
    protected abstract void recompute(AttributeUpdateSet updateSet);
    /**
     * 把传进来的属性放进模块modelAttributeSet中,并设置改变的属性类型，并清除以前该模块的属性
     * @param id
     * @param attrs
     * @param records
     */
    public void setAttributeModel(AttributeId id, List<Attribute> attrs, AttributeUpdateSet records) {
        if(records!=null){
            ModelAttribute oldAttrs = modelAttributes.get(id.toString());
            records.addAttrs(attrs);
            if(oldAttrs != null){
                records.addAttrs(oldAttrs.getAttributeMap().values());
            }
        }
        if(attrs.size() == 0){
            modelAttributes.remove(id.toString());
        }else{
            setAttributeModel(id,attrs);
        }
    }

    /**
     * 将属性放到对应的属性模块中
     * @param id
     * @param attrs
     */
    private void setAttributeModel(AttributeId id, List<Attribute> attrs) {
        ModelAttribute modelAttribute = modelAttributes.get(id.toString());
        if(modelAttribute == null){
            modelAttribute = new ModelAttribute();
            modelAttribute.setAttributeId(id);
            modelAttributes.put(id.toString(), modelAttribute);
        }
        //将原来的属性清空覆盖掉
        modelAttribute.getAttributeMap().clear();
        if(attrs !=null){
            AttributeUtil.accumulateToMap(attrs, modelAttribute.getAttributeMap());
        }
    }

    /**
     * 场景中需要加同步
     * @param id
     */
    public void removeAndCompteAttribtues(AttributeId id){
        ModelAttribute oldAttrs = modelAttributes.get(id.toString());
        if(removeAttributes(id)){
            AttributeUpdateSet updateSet = new AttributeUpdateSet(id);
            if(oldAttrs!=null){
                updateSet.addAttrs(oldAttrs.getAttributeMap().values());
            }
            recompute(updateSet);
        }
    }

    public boolean removeAttributes(AttributeId id){
        return modelAttributes.remove(id.toString())!=null;
    }

    public Map<AttributeType, Attribute> getFinalAttributes() {
        return finalAttributes;
    }

    public void setFinalAttributes(Map<AttributeType, Attribute> finalAttributes) {
        this.finalAttributes = finalAttributes;
    }

    public Map<String, ModelAttribute> getModelAttributes() {
        return modelAttributes;
    }

    public void setModelAttributes(Map<String, ModelAttribute> modelAttributes) {
        this.modelAttributes = modelAttributes;
    }

    public Map<AttributeType, Attribute> getCollectAttributes() {
        return collectAttributes;
    }

    public void setCollectAttributes(Map<AttributeType, Attribute> collectAttributes) {
        this.collectAttributes = collectAttributes;
    }
}
