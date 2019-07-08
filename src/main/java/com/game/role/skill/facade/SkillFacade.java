package com.game.role.skill.facade;

import com.game.SpringContext;
import com.game.role.player.model.Player;
import com.game.role.skill.packet.CM_LearnSkill;
import com.game.role.skill.packet.CM_ShowSkillBar;
import com.game.role.skill.packet.CM_UpgradeSkill;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 19:49
 */
@Component
public class SkillFacade {

    private static final Logger logger = LoggerFactory.getLogger(SkillFacade.class);

    @HandlerAnno
    public void learnSkill(TSession session, CM_LearnSkill cm) {
        try {
            SpringContext.getSkillService().learnSkill(cm.getSkillId(), cm.getPlayerId());
        } catch (Exception e) {
            logger.error("玩家[{}]学习技能[{}]失败", session.getAccountId(), cm.getSkillId());
        }
    }

    @HandlerAnno
    public void upgradeSkill(TSession session, CM_UpgradeSkill cm) {
        try {
            SpringContext.getSkillService().upgradeSkill(cm.getPlayerId(), cm.getSkillId());
        } catch (Exception e) {
            logger.error("玩家[{}]升级技能[{}]失败", session.getAccountId(), cm.getSkillId());
        }
    }

    @HandlerAnno
    public void showSkill(TSession session, CM_ShowSkillBar cm) {
        try {
            SpringContext.getSkillService().showSkillBar(cm.getPlayerId());
        } catch (Exception e) {
            logger.error("查看玩家[{}]技能栏失败", session.getAccountId());
        }
    }

}
