package com.game.user.reward.resource;

import com.game.user.reward.model.RewardDef;
import com.game.util.RewardUtil;
import com.resource.anno.Analyze;
import com.resource.anno.LoadResource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/23 10:04
 */
@LoadResource
public class RewardResource {

    private int id;

    private RewardDef[] rewardContext;
    @Analyze("analyzeReward")
    private List<RewardDef> rewards;

    public void analyzeReward() {
        if (rewardContext == null) {
            return;
        }
        List<RewardDef> rewards = new ArrayList<>();
        for (RewardDef def : rewardContext) {
            rewards.add(def);
        }
    }

    public List<RewardDef> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardDef> rewards) {
        this.rewards = rewards;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RewardDef[] getRewardContext() {
        return rewardContext;
    }

    public void setRewardContext(RewardDef[] rewardContext) {
        this.rewardContext = rewardContext;
    }
}
