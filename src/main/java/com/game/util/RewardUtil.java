package com.game.util;

import com.game.user.reward.model.RewardDef;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/23 10:09
 */
public class RewardUtil {

    public static List<RewardDef> analyzeReward(String[] rewards) {
        List<RewardDef> rewardDefList = new ArrayList<>();
        for (String item : rewards) {
            String[] split = item.split(StringUtil.XIA_HUA_XIAN);
            RewardDef rewardDef = RewardDef.valueOf(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
            rewardDefList.add(rewardDef);
        }
        return rewardDefList;
    }
}
