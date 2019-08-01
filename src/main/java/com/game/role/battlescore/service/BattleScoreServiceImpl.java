package com.game.role.battlescore.service;

import com.game.SpringContext;
import com.game.role.battlescore.util.BattleScoreCompute;
import com.game.role.player.model.Player;
import com.game.world.rank.event.BattleScoreChangeEvent;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 16:00
 */
@Component
public class BattleScoreServiceImpl implements BattleScoreService {

    @Override
    public void freshPlayerAttribute(Player player) {
        long battleScore = BattleScoreCompute.computePlayerBattleScore(player);
        player.setBattleScore(battleScore);
        BattleScoreChangeEvent event = BattleScoreChangeEvent.valueOf(player);
        SpringContext.getEvenManager().syncSubmit(event);
    }
}
