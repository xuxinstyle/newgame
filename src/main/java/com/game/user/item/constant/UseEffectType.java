package com.game.user.item.constant;

import com.game.user.item.model.*;

/**
 * 这里的名字要和道具类型中的名字相同
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
    private Class<? extends UseEffect> useClazz;
    UseEffectType(int id, Class<? extends UseEffect> useClazz){
        this.id = id;
        this.useClazz = useClazz;
    }
    public UseEffect create(){
        try{
            return useClazz.newInstance();
        }catch (Exception e){
            e.printStackTrace();
            throw new IllegalArgumentException("生成使用效果实例["+useClazz.getName()+"]错误");
        }
    }

}
