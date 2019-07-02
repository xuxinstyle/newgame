package com.game.user.itemeffect.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/21 11:19
 */
public class ItemEffectInfo {
    /**
     * <道表id, 道具失效详细信息>
     */
    Map<Integer, ItemEffectdetaiInfo> itemEffectdetaiInfoMap;

    public static ItemEffectInfo valueOf(){
        ItemEffectInfo itemEffectInfo = new ItemEffectInfo();
        itemEffectInfo.setItemEffectdetaiInfoMap(new HashMap<>());
        return itemEffectInfo;
    }
    public Map<Integer, ItemEffectdetaiInfo> getItemEffectdetaiInfoMap() {
        return itemEffectdetaiInfoMap;
    }

    public void setItemEffectdetaiInfoMap(Map<Integer, ItemEffectdetaiInfo> itemEffectdetaiInfoMap) {
        this.itemEffectdetaiInfoMap = itemEffectdetaiInfoMap;
    }
}
