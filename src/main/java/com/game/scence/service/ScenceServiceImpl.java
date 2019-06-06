package com.game.scence.service;

import com.game.role.account.model.AccountInfo;
import com.game.role.account.packet.SM_EnterCreatePlayer;
import com.game.SpringContext;
import com.game.role.account.entity.AccountEnt;
import com.game.role.constant.Job;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.scence.constant.SceneType;
import com.game.scence.packet.SM_EnterInitScence;
import com.game.scence.packet.SM_EnterMap;
import com.game.scence.packet.SM_ShowAccountInfo;
import com.game.scence.packet.SM_ShowAllAccountInfo;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 10:42
 */
@Component
public class ScenceServiceImpl implements ScenceService {
    private static final Logger logger = LoggerFactory.getLogger(ScenceServiceImpl.class);

    @Autowired
    private ScenceManger scenceManger;

    @Override
    public void enterInitMap(TSession session, String accountId) {
        /**
         * 如果没有角色信息，则进入创角页面
         */
        if(!checkAccountInfo(accountId)){
            SM_EnterCreatePlayer sm = new SM_EnterCreatePlayer();
            sm.setAccountId(accountId);

            session.sendPacket(sm);
            logger.info("没有角色信息");
        }else {
            /**
             * 如果有角色信息，则进入玩家上次在的地图
             */
            logger.info("有角色信息！");
            doEnterMap(session, accountId);
        }
    }

    @Override
    public void enterMap(TSession session, int mapId) {
        String accountId = session.getAccountId();
        if(accountId==null){
            logger.warn("Session中的accountId为空");
            return;
        }
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        SceneType sceneType = SceneType.valueOf(mapId);
        if(sceneType==null){
            logger.warn("没有目标地图信息");
            return;
        }
        SceneType currentMapType = accountInfo.getCurrentMapType();
        if(currentMapType!=null){
            scenceManger.removeAccountId(accountInfo.getCurrentMapType().getMapid(), accountId);
        }

        accountInfo.setCurrentMapType(sceneType);
        SpringContext.getAccountService().save(accountEnt);
        scenceManger.setScenceAccountId(mapId, accountId);
        if(logger.isDebugEnabled()){
            logger.debug("切换地图成功");
        }
        SM_EnterMap sm = new SM_EnterMap();
        sm.setStatus(1);
        sm.setMapId(mapId);
        session.sendPacket(sm);
    }

    private void doEnterMap(TSession session, String accountId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        if(logger.isDebugEnabled()){
            logger.debug("进入地图："+accountEnt.toString(),accountInfo);
        }

        scenceManger.setScenceAccountId(accountInfo.getLastLogoutMapType().getMapid(), accountId);
        SM_EnterInitScence sm = new SM_EnterInitScence();
        sm.setAccountId(accountId);
        sm.setType(accountInfo.getLastLogoutMapType().getMapid());
        session.sendPacket(sm);
    }

    /**
     * 判断玩家是否创角色 如果玩家没有角色 则进入创角
     * @param accountId
     * @return
     */
    private boolean checkAccountInfo(String accountId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        /**
         * 如玩家没有昵称，则说明没有创角色信息
         */
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        logger.info(accountInfo.toString());

        if(accountInfo.getAccountName()==null||accountInfo.getAccountName().equals("")){
            return false;
        }
        return true;
    }

    @Override
    public void removeScenceAccountId(int mapdId, String accountId) {
        scenceManger.removeAccountId(mapdId, accountId);
    }

    /**
     * 以后显示的信息会增加，先暂时传这些数据
     * @param session
     * @param mapId
     */
    @Override
    public void showAllAccountInfo(TSession session, int mapId) {
        List<String> scenceAccountIds = scenceManger.getScenceAccountIds(mapId);
        SM_ShowAllAccountInfo sm = new SM_ShowAllAccountInfo();
        Map<String ,String> accountInfos = new HashMap<>(scenceAccountIds.size());
        for (String accountId :scenceAccountIds) {
            AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
            AccountInfo accountInfo = accountEnt.getAccountInfo();
            accountInfos.put(accountId, accountInfo.getAccountName());
        }
        String context = parse(accountInfos);
        sm.setContext(context);
        if(logger.isDebugEnabled()){
            logger.debug(sm.toString());
            logger.debug(session.getAccountId());
        }
        session.sendPacket(sm);
    }

    private String parse(Map<String,String> accountInfos) {
        StringBuffer context = new StringBuffer("");
        for(Map.Entry<String,String> entry:accountInfos.entrySet()){
            context.append("accountId:"+entry.getKey()+"  昵称："+entry.getValue());
        }
        return context.toString();
    }

    @Override
    public void showAccountInfo(TSession session, String accountId, int mapId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        if(accountInfo==null){
            logger.warn("没有 ["+accountId+"]的账号信息");
            return;
        }
        List<Long> playerIds = accountInfo.getPlayerIds();
        if(playerIds.size()<1){
            logger.warn("玩家["+accountId+"]没有创建角色");
            return;
        }
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayer(playerIds.get(0));
        Player player = playerEnt.getPlayer();
        SM_ShowAccountInfo sm = new SM_ShowAccountInfo();
        sm.setAccountId(accountId);
        sm.setNickName(accountInfo.getAccountName());
        sm.setCareer(Job.valueOf(player.getCareer()).getName());
        sm.setLevel(player.getLevel());
        sm.setPlayerName(player.getPlayerName());
        session.sendPacket(sm);

    }

    @Override
    public void setScenceAccountId(int mapId, String accountId) {
        scenceManger.setScenceAccountId(mapId, accountId);
    }

    @Override
    public void removeAccountId(int mapId, String accountId) {
        scenceManger.removeAccountId(mapId,accountId);
    }
}
