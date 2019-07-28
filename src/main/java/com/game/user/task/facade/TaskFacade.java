package com.game.user.task.facade;

import com.event.anno.EventAnn;
import com.game.SpringContext;
import com.game.common.exception.RequestException;
import com.game.login.event.LoginEvent;
import com.game.role.player.event.PlayerUpLevelEvent;
import com.game.user.task.event.EnterMapEvent;
import com.game.user.task.event.PassMapEvent;
import com.game.user.task.event.TalkEvent;
import com.game.user.task.packet.*;
import com.game.util.I18nId;
import com.game.util.SendPacketUtil;
import com.game.world.hopetower.event.MonsterDeadEvent;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/25 12:25
 */
@Component
public class TaskFacade {

    private static final Logger logger = LoggerFactory.getLogger(TaskFacade.class);

    @EventAnn
    public void doAfterLogin(LoginEvent event) {
        SpringContext.getTaskService().doAfterLogin(event.getPlayer());
    }

    @EventAnn
    public void dotalkWithNpc(TalkEvent event) {
        SpringContext.getTaskService().doTalkWithNpc(event.getPlayer(), event.getNpcId());
    }

    @EventAnn
    public void doPassMapTask(PassMapEvent event) {
        SpringContext.getAccountExecutorService().addTask(event.getAccountId(), "doPassMapTask", () -> {
            SpringContext.getTaskService().doPassMapTask(event.getAccountId(), event.getMapId());
        });
    }

    @EventAnn
    public void doEnterMapTask(EnterMapEvent event) {
        SpringContext.getAccountExecutorService().addTask(event.getAccountId(), "doPassMapTask", () -> {
            SpringContext.getTaskService().doEnterMapTask(event.getAccountId(), event.getMapId());
        });
    }

    @EventAnn
    public void doPlayerLevelUp(PlayerUpLevelEvent event) {
        SpringContext.getAccountExecutorService().addTask(event.getPlayer().getAccountId(), "doPlayerLevelUp", () -> {
            SpringContext.getTaskService().doPlayerLevelUp(event.getPlayer());
        });
    }

    @EventAnn
    public void doMonsterkillDead(MonsterDeadEvent event) {
        // 丢到账号线程做杀怪后的处理
        SpringContext.getAccountExecutorService().addTask(event.getAccountId(), "doMonsterkillDead", () -> {
            SpringContext.getTaskService().doKillMonster(event.getAccountId(), event.getMapId(), event.getMonsterId());
        });

    }

    @HandlerAnno
    public void talkWithNpc(TSession session, CM_TalkWithNpc cm) {
        try {
            SpringContext.getTaskService().talkWithNpc(session.getAccountId(), cm.getMapId(), cm.getNpcId());
        } catch (RequestException e) {
            logger.error("与[{}]npc聊天失败失败,原因[{}]", cm.getNpcId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("与[{}]npc聊天失败失败", cm.getNpcId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void showFinishTask(TSession session, CM_ShowFinishTask cm) {
        try {
            SpringContext.getTaskService().showFinishTask(session.getAccountId());
        } catch (RequestException e) {
            logger.error("查看已完成的任务失败,原因[{}]", e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("查看已完成的任务失败,原因[{}]", e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void receivceReward(TSession session, CM_ReceiveTaskAward cm) {
        try {
            SpringContext.getTaskService().receiveTaskAward(session.getAccountId(), cm.getTaskId());
        } catch (RequestException e) {
            logger.error("领取任务[{}]奖励失败,原因[{}]", cm.getTaskId(), e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("领取任务[{}]奖励失败,原因[{}]", cm.getTaskId(), e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void showCanReceive(TSession session, CM_ShowCanReceiveTask cm) {
        try {
            SpringContext.getTaskService().showCanReceiveTask(session.getAccountId());
        } catch (RequestException e) {
            logger.error("查看可领取的任务失败,原因[{}]", e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("查看可领取的任务失败,原因[{}]", e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void acceptTask(TSession session, CM_AcceptTask cm) {
        try {
            SpringContext.getTaskService().acceptTask(session.getAccountId(), cm.getTaskId());
        } catch (RequestException e) {
            logger.error("接受任务失败,原因[{}]", e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("接受任务失败,原因[{}]", e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

    @HandlerAnno
    public void showExecuteTasks(TSession session, CM_ShowExecuteTask cm) {
        try {
            SpringContext.getTaskService().showExecuteTasks(session.getAccountId());
        } catch (RequestException e) {
            logger.error("查看正在进行的任务失败,原因[{}]", e.getErrorCode());
            SendPacketUtil.send(session.getAccountId(), e.getErrorCode());
        } catch (Exception e) {
            logger.error("查看正在进行的任务失败,原因[{}]", e);
            SendPacketUtil.send(session.getAccountId(), I18nId.ERROR);
        }
    }

}
