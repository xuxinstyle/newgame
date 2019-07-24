package com.game.user.reward.service;

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
}
