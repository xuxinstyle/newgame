package com.game.user.item.model;

import com.game.user.item.resource.ItemResource;

/**
 * 装备强化石
 * @Author：xuxin
 * @Date: 2019/6/20 11:46
 */
public class Stone extends AbstractItem{

    @Override
    public void init(ItemResource itemResource) {
        super.init(itemResource);

    }

    @Override
    public AbstractItem copy() {
        Stone stone = new Stone();
        stone.setObjectId(createItemObjectId());
        stone.setNum(this.num);
        stone.setItemModelId(this.itemModelId);
        stone.setItemType(this.itemType);
        return stone;
    }
}
