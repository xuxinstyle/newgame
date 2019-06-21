package com.game.user.item.model;

/**
 * 升阶石
 * @Author：xuxin
 * @Date: 2019/6/20 11:49
 */
public class UpStone extends AbstractItem{
    @Override
    public AbstractItem copy() {
        UpStone upStone = new UpStone();
        upStone.setObjectId(createItemObjectId());
        upStone.setNum(this.num);
        upStone.setDeprecatedTime(this.deprecatedTime);
        upStone.setItemModelId(this.itemModelId);
        upStone.setItemType(this.itemType);
        upStone.setStatus(this.status);
        return upStone;
    }
}
