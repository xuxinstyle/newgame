package com.game.world.union.facade;

import com.game.SpringContext;
import com.game.common.exception.RequestException;
import com.game.util.I18nId;
import com.game.util.SendPacketUtil;
import com.game.world.union.packet.*;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/29 10:34
 */
@Component
public class UnionFacade {

    private Logger logger = LoggerFactory.getLogger(UnionFacade.class);

    @HandlerAnno
    public void createUnion(TSession session, CM_CreateUnion cm) {
        try {
            SpringContext.getUnionService().createUnion(session.getAccountId(), cm.getUnionName());
        } catch (RequestException e) {
            logger.error("玩家[{}]创建行会错误,原因[{}]", session.getAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]创建行会错误,原因[{}]", session.getAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void applyJoinUnion(TSession session, CM_ApplyJoinUnion cm) {
        try {
            SpringContext.getUnionService().applyJoinUnion(session.getAccountId(), cm.getUnionId());
        } catch (RequestException e) {
            logger.error("玩家[{}]申请加如行会错误,原因[{}]", session.getAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]申请加如行会错误,原因[{}]", session.getAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void exitUnion(TSession session, CM_ExitUnion cm) {
        try {
            SpringContext.getUnionService().exitUnion(session.getAccountId());
        } catch (RequestException e) {
            logger.error("玩家[{}]退出工会错误,原因[{}]", session.getAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]退出工会错误,原因[{}]", session.getAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void showApplyList(TSession session, CM_ShowApplyList cm) {
        try {
            SpringContext.getUnionService().showApplyList(session.getAccountId());
        } catch (RequestException e) {
            logger.error("玩家[{}]查看申请列表错误,原因[{}]", session.getAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]查看申请列表错误,原因[{}]", session.getAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void agreeApply(TSession session, CM_AgreeApply cm) {
        try {
            SpringContext.getUnionService().agreeApply(session.getAccountId(), cm.getTargetAccountId());
        } catch (RequestException e) {
            logger.error("玩家[{}]同意申请加入工会错误,原因[{}]", session.getAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]同意申请加入工会错误,原因[{}]", session.getAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void refuseApply(TSession session, CM_RefuseApply cm) {
        try {
            SpringContext.getUnionService().refuse(session.getAccountId(), cm.getTargetAccountId());
        } catch (RequestException e) {
            logger.error("玩家[{}]拒绝[{}]加入工会错误,原因[{}]", session.getAccountId(), cm.getTargetAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]拒绝[{}]加入工会错误,原因[{}]", session.getAccountId(), cm.getTargetAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void showUnionMembers(TSession session, CM_ShowUnionMembers cm) {
        try {
            SpringContext.getUnionService().showUnionMembers(session.getAccountId(), cm.getUnionId());
        } catch (RequestException e) {
            logger.error("玩家[{}]查看工会[{}]信息错误,原因[{}]", session.getAccountId(), cm.getUnionId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]查看工会[{}]信息错误,原因[{}]", session.getAccountId(), cm.getUnionId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void showUnionList(TSession session, CM_ShowUnionList cm) {
        try {
            SpringContext.getUnionService().showUnionList(session.getAccountId());
        } catch (RequestException e) {
            logger.error("玩家[{}]查看工会列表错误,原因[{}]", session.getAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]查看工会列表错误,原因[{}]", session.getAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void kickOhter(TSession session, CM_KickOther cm) {
        try {
            SpringContext.getUnionService().kickOther(session.getAccountId(), cm.getTargetAccountId());
        } catch (RequestException e) {
            logger.error("玩家[{}]移除玩家[{}]失败,原因[{}]", session.getAccountId(), cm.getTargetAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("玩家[{}]移除玩家[{}]失败,原因[{}]", session.getAccountId(), cm.getTargetAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void updatePermission(TSession session, CM_UpdatePermission cm) {
        try {
            SpringContext.getUnionService().updatePermission(session.getAccountId(), cm.getTargetAccountId(), cm.getPermission());
        } catch (RequestException e) {
            logger.warn("玩家[{}]修改玩家[{}]权限至[{}]失败,原因[{}]", session.getAccountId(), cm.getTargetAccountId(), cm.getPermission(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.warn("玩家[{}]修改玩家[{}]权限至[{}]失败,原因[{}]", session.getAccountId(), cm.getTargetAccountId(), cm.getPermission(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void showMySelfUnion(TSession session, CM_ShowMyselfUnion cm) {
        try {
            SpringContext.getUnionService().showMySelfUnion(session.getAccountId());
        } catch (RequestException e) {
            logger.warn("玩家[{}]查看自己工会信息失败,原因[{}]", session.getAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.warn("玩家[{}]查看自己工会信息失败,原因[{}]", session.getAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void appointCaptain(TSession session, CM_AppointCaptain cm) {
        try {
            SpringContext.getUnionService().appoinCaptain(session.getAccountId(), cm.getTargetAccountId());
        } catch (RequestException e) {
            logger.warn("玩家[{}]委任会长失败,原因[{}]", session.getAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.warn("玩家[{}]委任会长失败,原因[{}]", session.getAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void disbandUnion(TSession session, CM_DisbandUnion cm) {
        try {
            SpringContext.getUnionService().disband(session.getAccountId());
        } catch (RequestException e) {
            logger.warn("玩家[{}]解散工会失败,原因[{}]", session.getAccountId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.warn("玩家[{}]解散工会失败,原因[{}]", session.getAccountId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }


}
