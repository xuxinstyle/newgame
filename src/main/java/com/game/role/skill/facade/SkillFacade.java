package com.game.role.skill.facade;

import com.game.SpringContext;
import com.game.common.exception.RequestException;
import com.game.role.skill.packet.*;
import com.game.util.I18nId;
import com.game.util.SendPacketUtil;
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
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]学习技能[{}]失败", session.getAccountId(), cm.getSkillId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void upgradeSkill(TSession session, CM_UpgradeSkill cm) {
        try {
            SpringContext.getSkillService().upgradeSkill(cm.getPlayerId(), cm.getSkillId());
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]升级技能[{}]失败", session.getAccountId(), cm.getSkillId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void showSkill(TSession session, CM_ShowSkillInfo cm) {
        try {
            SpringContext.getSkillService().showSkillInfo(cm.getPlayerId());
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]查看技能栏失败", session.getAccountId());
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }
    @HandlerAnno
    public void showSkillBar(TSession session, CM_ShowSkillBar cm){
        try{
            SpringContext.getSkillService().showSkillBar(cm.getPlayerId());
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]查看快捷技能栏失败", session.getAccountId());
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }
    @HandlerAnno
    public void setSkillBar(TSession session, CM_SetSkillBar cm){
        try{
            SpringContext.getSkillService().setSkillBar(session,cm.getPlayerId(),cm.getSetStr());
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]设置技能栏失败",session.getAccountId());
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }
    @HandlerAnno
    public void useSingleSkill(TSession session, CM_UseSkill cm) {
        try {
            SpringContext.getSkillService().useSkill(session, cm.getMapId(), cm.getSceneId(), cm.getSkillTargetId(), cm.getUseId(), cm.getUseType(), cm.getSkillBarId(), cm.getObjectType());
        } catch (RequestException e) {
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("角色[{}]使用技能[{}]出错！",cm.getUseId(),cm.getSkillBarId());
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }


}
