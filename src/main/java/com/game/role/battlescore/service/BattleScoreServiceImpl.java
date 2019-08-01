package com.game.role.battlescore.service;

import com.game.SpringContext;
import com.game.role.battlescore.util.BattleScoreCompute;
import com.game.role.player.model.Player;
import com.game.world.rank.event.BattleScoreChangeAsynEvent;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 16:00
 */
@Component
public class BattleScoreServiceImpl implements BattleScoreService {

    @Override
    public void freshPlayerBattleScore(Player player) {
        long battleScore = BattleScoreCompute.computePlayerBattleScore(player);
        player.setBattleScore(battleScore);
        SpringContext.getPlayerSerivce().save(player);
        BattleScoreChangeAsynEvent event = BattleScoreChangeAsynEvent.valueOf(player);
        SpringContext.getEvenManager().asyncSubmit(event);
    }
}
