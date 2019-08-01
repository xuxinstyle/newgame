package com.game.role.battlescore.facade;

import com.event.anno.EventAnn;
import com.game.SpringContext;
import com.game.role.battlescore.event.ChangeAttrbuteEvent;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/31 15:59
 */
@Component
public class BattleScoreFacade {
    @EventAnn
    public void doAttributeChange(ChangeAttrbuteEvent event) {
        SpringContext.getBattleScoreService().freshPlayerAttribute(event.getPlayer());
    }
}
