package com.game.user.condition.model;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;

import java.util.Map;

/**
 * 这里只用来强化装备和升阶
 * @Author：xuxin
 * @Date: 2019/6/16 17:57
 *
 */
public abstract class AbstractCondition {


    public boolean checkCondition(Player player, Map<String, Object> param){

        return true;
    }

}
