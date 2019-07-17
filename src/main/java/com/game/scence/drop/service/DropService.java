package com.game.scence.drop.service;

import com.game.scence.drop.model.RandDrop;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.monster.resource.MonsterResource;
import com.game.user.item.model.AbstractItem;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/15 15:59
 */
public interface DropService {
    /**
     * 随机获取掉落物
     *
     * @param monsterResource
     * @param killerJobType
     * @return
     */
    List<AbstractItem> getRandDropItems(MonsterResource monsterResource, int killerJobType);
}
