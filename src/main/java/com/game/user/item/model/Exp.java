package com.game.user.item.model;

import com.game.user.item.resource.ItemResource;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/12 18:00
 */
public class Exp extends AbstractItem{

    /**
     * 可加的经验值
     */
    private long expCount;

    /** 根据itemResource表初始化*/
    @Override
    public void init(ItemResource itemResource, Map<String, Object> params) {
        super.init(itemResource, params);
    }
}
