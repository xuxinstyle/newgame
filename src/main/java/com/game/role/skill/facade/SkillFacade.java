package com.game.role.skill.facade;

import com.event.anno.EventAnn;
import com.game.SpringContext;
import com.game.role.skill.packet.*;
import com.game.scence.monster.event.CreatureDeadEvent;
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
    public void showSkill(TSession session, CM_ShowSkillInfo cm) {
        try {
            SpringContext.getSkillService().showSkillInfo(cm.getPlayerId());
        } catch (Exception e) {
            logger.error("玩家[{}]查看技能栏失败", session.getAccountId());
        }
    }
    @HandlerAnno
    public void showSkillBar(TSession session, CM_ShowSkillBar cm){
        try{
            SpringContext.getSkillService().showSkillBar(cm.getPlayerId());
        }catch (Exception e){
            logger.error("玩家[{}]查看快捷技能栏失败", session.getAccountId());
        }
    }
    @HandlerAnno
    public void setSkillBar(TSession session, CM_SetSkillBar cm){
        try{
            SpringContext.getSkillService().setSkillBar(session,cm.getPlayerId(),cm.getSetStr());
        }catch (Exception e){
            logger.error("玩家[{}]设置技能栏失败",session.getAccountId());
        }
    }
    @HandlerAnno
    public void useSingleSkill(TSession session,CM_UseSkillToMonster cm){
        try {
            SpringContext.getSkillService().useSkillToMonster(session,cm.getMapId(),cm.getSceneId(),cm.getSkillTargetId(),cm.getUseId(),cm.getSkillBarId());
        }catch (Exception e){
            logger.error("角色[{}]使用技能[{}]出错！",cm.getUseId(),cm.getSkillBarId());
        }
    }

    @EventAnn
    public void doMonsterDeadAfter(CreatureDeadEvent event){
        SpringContext.getSkillService().doCreatureDeadAfter(event.getMapId(),event.getMonsterUnit());
    }

}
