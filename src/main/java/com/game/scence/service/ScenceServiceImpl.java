package com.game.scence.service;

import com.game.SpringContext;
import com.game.role.account.entity.AccountEnt;
import com.game.role.account.model.AccountInfo;
import com.game.role.account.packet.SM_EnterCreatePlayer;
import com.game.role.constant.Job;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.scence.constant.SceneType;
import com.game.scence.packet.*;
import com.game.scence.resource.MapResource;
import com.resource.StorageManager;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 10:42
 */
@Component
public class ScenceServiceImpl implements ScenceService {
    private static final Logger logger = LoggerFactory.getLogger(ScenceServiceImpl.class);

    @Autowired
    private ScenceManger scenceManger;

    /**
     * 创角
     *
     * @param session
     * @param accountId
     */
    @Override
    public void enterMap(TSession session, String accountId, int mapId) {
        /**
         * 如果没有角色信息，则进入创角页面
         */
        if (!checkAccountInfo(accountId)) {
            SM_EnterCreatePlayer sm = new SM_EnterCreatePlayer();
            sm.setAccountId(accountId);

            session.sendPacket(sm);
            logger.info("没有角色信息");
        } else {
            /**
             * 如果有角色信息，则进入玩家上次在的地图
             */
            logger.info("有角色信息！");
            doEnterMap(session, accountId, mapId);
        }
    }

    private Player setPosition(String accountId, MapResource mapResource) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        List<Long> playerIds = accountInfo.getPlayerIds();
        if(playerIds==null||playerIds.size()==0){
            logger.warn("玩家{}没有角色信息",accountId);
            return null;
        }
        Long playerId = playerIds.get(0);
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayer(playerId);
        Player player = playerEnt.getPlayer();
        if(player==null){
            logger.warn("玩家{}没有角色信息",accountId);
            return null;
        }
        /** 初始化位置*/
        player.setX(mapResource.getInitX());
        player.setY(mapResource.getInitY());
        SpringContext.getPlayerSerivce().save(playerEnt);
        return player;
    }

    /**
     * 进入地图
     *
     * @param session
     * @param accountId
     */
    @Override
    public void doEnterMap(TSession session, String accountId,int mapId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        accountInfo.setCurrentMapType(SceneType.valueOf(mapId));
        leaveMap(accountId, mapId);
        if (logger.isDebugEnabled()) {
            logger.debug("进入地图：" + accountEnt.toString(), accountInfo);
        }

        SceneType lastLogoutMapType = accountInfo.getLastLogoutMapType();
        if(lastLogoutMapType==null){
            scenceManger.setScenceAccountId(1, accountId);
            accountInfo.setLastLogoutMapType(SceneType.NoviceVillage);
            accountInfo.setCurrentMapType(SceneType.NoviceVillage);
        }

        MapResource mapResource = getMapResource(mapId);
        if(mapResource ==null){
            logger.warn("{}资源加载错误", MapResource.class);
        }
        if(logger.isDebugEnabled()){
            logger.debug(mapResource.toString());
        }
        Player player = setPosition(accountId, mapResource);
        SpringContext.getAccountService().save(accountEnt);
        SM_EnterMap sm = new SM_EnterMap();
        sm.setX(player.getX());
        sm.setY(player.getY());
        sm.setContext(mapResource.getContext());
        sm.setAccountId(accountId);
        sm.setMapId(mapId);
        sm.setStatus(1);
        session.sendPacket(sm);



    }

    /**
     * 做进入地图前需要做的事，即离开地图需要做的事
     *
     * @param
     * @param mapId
     */
    private void leaveMap(String accountId, int mapId) {
        /** 1.清除上次地图中玩家存的缓存信息*/
        removeScenceAccountId(mapId, accountId);
        /** 2.在新的场景中添加玩家账号信息.*/
        setScenceAccountId(mapId, accountId);
    }

    /**
     * 判断玩家是否创角色 如果玩家没有角色 则进入创角
     *
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

        if (accountInfo.getAccountName() == null || "".equals(accountInfo.getAccountName())) {
            return false;
        }
        return true;
    }



    /**
     * 以后显示的信息会增加，先暂时传这些数据
     *
     * @param session
     * @param mapId
     */
    @Override
    public void showAllAccountInfo(TSession session, int mapId) {
        List<String> scenceAccountIds = scenceManger.getScenceAccountIds(mapId);
        SM_ShowAllAccountInfo sm = new SM_ShowAllAccountInfo();
        Map<String, String> accountInfos = new HashMap<>(scenceAccountIds.size());
        for (String accountId : scenceAccountIds) {
            AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
            AccountInfo accountInfo = accountEnt.getAccountInfo();
            accountInfos.put(accountId, accountInfo.getAccountName());
        }
        String context = parse(accountInfos);
        sm.setContext(context);
        if (logger.isDebugEnabled()) {
            logger.debug(sm.toString());
            logger.debug(session.getAccountId());
        }
        session.sendPacket(sm);
    }

    private String parse(Map<String, String> accountInfos) {
        StringBuffer context = new StringBuffer("");
        for (Map.Entry<String, String> entry : accountInfos.entrySet()) {
            context.append("accountId:" + entry.getKey() + "  昵称：" + entry.getValue() + ",");
        }
        return context.toString();
    }

    @Override
    public void showAccountInfo(TSession session, String accountId, int mapId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        if (accountEnt == null) {
            logger.warn("没有 [" + accountId + "]的账号信息");
            SM_ShowAccountInfo sm = new SM_ShowAccountInfo();
            sm.setAccountId(null);
            session.sendPacket(sm);
            return;
        }
        AccountInfo accountInfo = accountEnt.getAccountInfo();

        List<Long> playerIds = accountInfo.getPlayerIds();
        if (playerIds.size() < 1) {
            logger.warn("玩家[" + accountId + "]没有创建角色");
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
    public void removeScenceAccountId(int mapId, String accountId) {

        scenceManger.removeAccountId(mapId, accountId);
    }
    @Override
    public void move(TSession session, int x, int y) {
        String accountId = session.getAccountId();
        if (!checkMove(accountId, x, y)) {
            logger.warn("不能移动到当前位置");
            SM_Move sm = new SM_Move();
            sm.setStatus(0);
            sm.setX(x);
            sm.setY(y);
            session.sendPacket(sm);
            return;
        }

        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        List<Long> playerIds = accountInfo.getPlayerIds();
        Long playerId = playerIds.get(0);
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayer(playerId);
        Player player = playerEnt.getPlayer();
        player.setX(x);
        player.setY(y);
        SM_Move sm = new SM_Move();
        sm.setStatus(1);
        sm.setX(x);
        sm.setY(y);
        session.sendPacket(sm);
    }

    @Override
    public MapResource getMapResource(int mapId) {
        return (MapResource) StorageManager.getResource(MapResource.class, mapId );
    }

    private boolean checkMove(String accountId, int x, int y) {

        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        SceneType currentMapType = accountInfo.getCurrentMapType();
        if (currentMapType == null) {
            logger.warn("没有玩家当前地图的信息");
            return false;
        }

        MapResource mapResource = getMapResource(currentMapType.getMapid());
        String context = mapResource.getContext();
        String[] mapX = context.split(",");
        if (x > mapX.length || x < 1) {
            return false;
        }
        for (int i = 0; i < mapX.length; i++) {
            String[] mapY = mapX[0].split(" ");
            if (y > mapY.length || y < 1) {
                logger.warn("不能移动到x={},y={}",x,y);
                return false;
            }
            if (Integer.parseInt(mapY[y - 1])==1&&x==i){
                logger.warn("不能移动到x={},y={}",x,y);
                return false;
            }
            if(Integer.parseInt(mapY[y - 1])==0&&x==i){
                return true;
            }
        }
        return false;
    }

    private class RandPosition {
        private String accountId;
        private int x;
        private int y;

        public RandPosition(String accountId) {
            this.accountId = accountId;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public RandPosition rand() {
            x = (int) (1 + Math.random() * (10));
            y = (int) (1 + Math.random() * (10));
            while(checkMove(accountId, x, y)) {
                x = (int) (1 + Math.random() * (10));
                y = (int) (1 + Math.random() * (10));
            }
            return this;
        }
    }
}
