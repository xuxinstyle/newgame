package com.game.user.item.constant;

import com.game.user.item.model.*;

/**
 * 使用效果的道具类型
 * @Author：xuxin
 * @Date: 2019/6/17 14:28
 */
public enum UseEffectType {
    /**
     * 药品使用效果
     */
    MEDICINE(1,MedicineEffect.class),
    /**
     * 经验丹使用效果
     */
    EXP(1, ExpUseEffect.class),
    ;
    private int id;

    private Class<? extends AbstractUseEffect> useClazz;

    UseEffectType(int id, Class<? extends AbstractUseEffect> useClazz){
        this.id = id;
        this.useClazz = useClazz;
    }
    public AbstractUseEffect create(){
        try{
            return useClazz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("生成使用效果实例["+useClazz.getName()+"]错误");
        }
    }

}
