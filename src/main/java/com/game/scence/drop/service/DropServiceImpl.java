package com.game.scence.drop.service;

import com.game.SpringContext;
import com.game.scence.drop.model.RandDrop;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.monster.resource.MonsterResource;
import com.game.user.item.model.AbstractItem;
import com.game.util.ItemUtil;
import com.game.util.RandomUtil;
import com.game.util.StringUtil;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @Author：xuxin
 * @Date: 2019/7/15 15:59
 */
@Component
public class DropServiceImpl implements DropService {
    /**
     * 获取怪物的掉落物
     *
     * @param
     * @param killerJobType
     * @return
     */
    @Override
    public List<AbstractItem> getRandDropItems(MonsterResource monsterResource, int killerJobType) {
        RandDrop randDrop = monsterResource.getRandDrop();
        List<AbstractItem> items = randDrop.getRandDropItem(killerJobType);
        /**
         * 加经验
         */
        AbstractItem item = SpringContext.getItemService().createItem(ItemUtil.EXP_ITEM_ID,
                monsterResource.getExp());
        items.add(item);
        return items;
    }

}
