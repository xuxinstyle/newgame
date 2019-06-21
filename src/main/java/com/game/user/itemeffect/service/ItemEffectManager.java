package com.game.user.itemeffect.service;

import com.db.HibernateDao;
import com.game.user.itemeffect.entity.ItemEffectEnt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 12:02
 */
@Component
public class ItemEffectManager {
    @Autowired
    private HibernateDao hibernateDao;

    public void saveItemEffect(ItemEffectEnt itemEffectEnt){
        hibernateDao.saveOrUpdate(ItemEffectEnt.class, itemEffectEnt);
    }

    public ItemEffectEnt getItemEffectOrCreate(long playerId){
        ItemEffectEnt itemEffectEnt = hibernateDao.find(ItemEffectEnt.class, playerId);
        if(itemEffectEnt==null){
            ItemEffectEnt nowItemEffectEnt = ItemEffectEnt.valueOf(playerId);
            saveItemEffect(nowItemEffectEnt);
            return nowItemEffectEnt;
        }
        return itemEffectEnt;
    }
}
