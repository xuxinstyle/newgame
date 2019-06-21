package com.game.user.item.model;

/**
 * 装备强化石
 * @Author：xuxin
 * @Date: 2019/6/20 11:46
 */
public class StrenStone extends AbstractItem{

    @Override
    public AbstractItem copy() {
        StrenStone strenStone = new StrenStone();
        strenStone.setObjectId(createItemObjectId());
        strenStone.setNum(this.num);
        strenStone.setDeprecatedTime(this.deprecatedTime);
        strenStone.setItemModelId(this.itemModelId);
        strenStone.setItemType(this.itemType);
        strenStone.setStatus(this.status);
        return strenStone;
    }
}
