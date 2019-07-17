package com.game.scence.drop.model;

import com.game.SpringContext;
import com.game.user.item.model.AbstractItem;
import com.game.util.StringUtil;

import java.util.*;

/**
 * @Author：xuxin
 * @Date: 2019/7/16 16:23
 */
public class RandDrop {
    /**
     * 《职业，道具列表》
     */
    private Map<Integer, List<Integer>> dropMap;

    public static RandDrop analyzeDrop(String dropStr) {
        RandDrop randDrop = new RandDrop();
        Map<Integer, List<Integer>> dropMap = new HashMap<>();
        String[] split = dropStr.split(StringUtil.FEN_HAO);
        for (String jobStr : split) {
            List<Integer> itemIds = new ArrayList<>();
            String[] job = jobStr.split(StringUtil.XIA_HUA_XIAN);
            String[] itemIdStr = job[1].split(StringUtil.MAOHAO);
            int minItemId = Integer.parseInt(itemIdStr[0]);
            int maxItemId = Integer.parseInt(itemIdStr[1]);
            for (int i = minItemId; i <= maxItemId; i++) {
                itemIds.add(i);
            }
            if (dropMap.get(Integer.parseInt(job[0])) != null) {
                List<Integer> itemIdMaps = dropMap.get(Integer.parseInt(job[0]));
                itemIds.addAll(itemIdMaps);
            }
            dropMap.put(Integer.parseInt(job[0]), itemIds);
        }
        randDrop.setDropMap(dropMap);
        return randDrop;
    }

    public List<AbstractItem> getRandDropItem(int killerJobType) {
        List<Integer> itemIds = dropMap.get(killerJobType);
        if (itemIds == null) {
            itemIds = new ArrayList<>();
        }
        if (dropMap.get(0) != null) {
            itemIds.addAll(dropMap.get(0));
        }
        Random random = new Random();
        int itemModelId = itemIds.get(random.nextInt(itemIds.size()));
        AbstractItem item = SpringContext.getItemService().createItem(itemModelId, 1);
        List<AbstractItem> items = new ArrayList<>();
        items.add(item);
        return items;
    }

    public Map<Integer, List<Integer>> getDropMap() {
        return dropMap;
    }

    public void setDropMap(Map<Integer, List<Integer>> dropMap) {
        this.dropMap = dropMap;
    }
}
