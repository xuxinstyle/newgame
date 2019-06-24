package com.game.user.item.model;

/**
 * 装备强化石
 * @Author：xuxin
 * @Date: 2019/6/20 11:46
 */
public class Stone extends AbstractItem{

    @Override
    public AbstractItem copy() {
        Stone stone = new Stone();
        stone.setObjectId(createItemObjectId());
        stone.setNum(this.num);
        stone.setDeprecatedTime(this.deprecatedTime);
        stone.setItemModelId(this.itemModelId);
        stone.setItemType(this.itemType);
        stone.setStatus(this.status);
        return stone;
    }
}
