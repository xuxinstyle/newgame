package com.game.user.reward.service;


import com.game.user.reward.resource.RewardResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/23 10:17
 */
@Component
public class RewardServiceImpl implements RewardService {
    @Autowired
    private RewardManager rewardManager;

    @Override
    public RewardResource getRewardResource(int rewardId) {
        return rewardManager.getRewardResource(rewardId);
    }
}
