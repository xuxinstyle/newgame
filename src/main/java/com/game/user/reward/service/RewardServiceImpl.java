package com.game.user.reward.service;


import com.game.SpringContext;
import com.game.user.item.model.AbstractItem;
import com.game.user.reward.model.RewardDef;
import com.game.user.reward.resource.RewardResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 通关奖励id发奖
     *
     * @param accountId
     * @param rewardDefs
     */
    @Override
    public void doReward(String accountId, RewardDef[] rewardDefs) {
        List<AbstractItem> items = new ArrayList<>();
        for (RewardDef def : rewardDefs) {
            AbstractItem item = SpringContext.getItemService().createItem(def.getItemId(), def.getNum());
            items.add(item);
        }
        SpringContext.getItemService().awardToPack(accountId, items);
    }

    /**
     * 通过奖励配置发奖
     *
     * @param accountId
     * @param rewardId
     */
    @Override
    public void doReward(String accountId, int rewardId) {
        RewardResource rewardResource = getRewardResource(rewardId);
        doReward(accountId, rewardResource.getRewardContext());
    }
}
