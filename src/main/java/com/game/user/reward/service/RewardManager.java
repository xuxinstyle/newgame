package com.game.user.reward.service;

import com.game.user.reward.resource.RewardResource;
import com.resource.anno.Analyze;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/23 10:15
 */
@Component
public class RewardManager {
    @Autowired
    private StorageManager storageManager;

    public RewardResource getRewardResource(int rewardId) {
        return storageManager.getResource(rewardId, RewardResource.class);
    }
}
