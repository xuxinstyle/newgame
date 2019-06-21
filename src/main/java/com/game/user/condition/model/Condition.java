package com.game.user.condition.model;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.user.item.entity.ItemStorageEnt;
import com.game.user.item.model.AbstractItem;

/**
 * 这里只用来强化装备和升阶
 * @Author：xuxin
 * @Date: 2019/6/16 17:57
 *
 */
public abstract class Condition {


    public boolean checkCondition(String accountId,int equipLevel){

        return true;
    }

}
