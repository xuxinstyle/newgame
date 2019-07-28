package com.game.user.reward.service;

import com.game.user.reward.model.RewardDef;
import com.game.user.reward.resource.RewardResource;

/**
 * @Author：xuxin
 * @Date: 2019/7/23 10:17
 */
public interface RewardService {
    /**
     * 获取奖励资源
     *
     * @param rewardId
     * @return
     */
    RewardResource getRewardResource(int rewardId);

    /**
     * 通关奖励配置发奖
     *
     * @param accountId
     * @param rewardDefs
     */
    void doReward(String accountId, RewardDef[] rewardDefs);

    /**
     * 通过奖励id发奖
     *
     * @param accountId
     * @param rewardId
     */
    void doReward(String accountId, int rewardId);

}
