package com.game.util;

import com.game.SpringContext;
import com.game.user.item.constant.ItemType;
import com.game.user.item.model.AbstractItem;
import com.game.user.item.resource.ItemResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/15 16:49
 */
public class RandomUtil {

    public static AbstractItem randDropItem(int maxItemId, int minItemId) {
        int itemId = (int) (Math.random() * (maxItemId - minItemId + 1) + minItemId);
        AbstractItem item = SpringContext.getItemService().createItem(itemId, 1);
        return item;
    }
}
