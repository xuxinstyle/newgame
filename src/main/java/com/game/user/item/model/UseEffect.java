package com.game.user.item.model;

import com.game.base.attribute.Attribute;
import com.game.base.attribute.constant.AttributeType;
import com.game.role.player.model.Player;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/17 12:30
 */
public abstract class UseEffect {
    /**
     * 初始化
     * @param effect
     */
    public abstract void init(String effect);

    /**
     * 使用
     * @param
     */
    public abstract void use(String accountId,int num);
}
