package com.game.scence.service;

import com.game.SpringContext;
import com.game.role.constant.Job;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.scence.constant.SceneType;
import com.game.scence.model.ScenceInfo;
import com.game.scence.packet.*;
import com.game.scence.resource.MapResource;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.user.account.packet.SM_EnterCreatePlayer;
import com.resource.core.StorageManager;
import com.socket.core.session.SessionManager;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    @Autowired
    private SessionManager sessionManager;
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
            logger.info("玩家[{}]进入创角",accountId);
            return;
        }
        /**
         * 如果有角色信息，则进入玩家上次在的地图
         */
        logger.info("玩家[{}]有角色信息！开始进图地图",accountId);
        doEnterMap(session, accountId, mapId);

    }

    private Player setPosition(String accountId, MapResource mapResource) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        List<Long> playerIds = accountInfo.getPlayerIds();
        if (playerIds == null || playerIds.size() == 0) {
            logger.warn("玩家{}没有角色信息", accountId);
            return null;
        }
        Long playerId = playerIds.get(0);
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayer(playerId);
        Player player = playerEnt.getPlayer();
        if (player == null) {
            logger.warn("玩家{}没有角色信息", accountId);
            return null;
        }
        /** 初始化位置*/
        player.setX(mapResource.getInitX());
        player.setY(mapResource.getInitY());
        refreshScenceInfo(mapResource.getId(),player);
        SpringContext.getPlayerSerivce().save(playerEnt);
        return player;
    }
    public void refreshScenceInfo(int mapId, Player player){
        scenceManger.refreshScenceInfo(mapId,player);
    }
    /**
     * 进入地图
     *
     * @param session
     * @param accountId
     */
    @Override
    public void doEnterMap(TSession session, String accountId, int mapId) {
        leaveMap(accountId, mapId);
        /** 在新的场景中添加玩家账号信息.*/
        setScenceAccountId(mapId, accountId);
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        if (logger.isDebugEnabled()) {
            logger.debug("进入地图：" + accountEnt.toString(), accountInfo);
        }
        accountInfo.setCurrentMapType(SceneType.valueOf(mapId));
        MapResource mapResource = getMapResource(mapId);
        if (mapResource == null) {
            logger.warn("{}资源加载错误", MapResource.class);
            return;
        }
        if (logger.isDebugEnabled()) {
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
        sm.setPosition(player.getX()+","+player.getY());
        session.sendPacket(sm);
    }

    /**
     * 做进入地图前需要做的事，即离开地图需要做的事,清除之前的场景中的信息
     *
     * @param
     * @param mapId
     */
    private void leaveMap(String accountId, int mapId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        SceneType currentMapType = accountInfo.getCurrentMapType();
        if(currentMapType!=null){
            /** 1.清除上次地图中玩家存的缓存信息*/
            removeScenceAccountId(currentMapType.getMapid(), accountId);
        }
        showMap(currentMapType.getMapid());
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
        if(logger.isDebugEnabled()) {
            logger.debug(accountInfo.toString());
        }
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
        ScenceInfo scenceInfo = scenceManger.getScenceInfo(mapId);
        SM_ShowAllAccountInfo sm = new SM_ShowAllAccountInfo();
        List<Player> player = scenceInfo.getPlayers();
        String context = parse(player);
        sm.setContext(context);
        if (logger.isDebugEnabled()) {
            logger.debug(context);
        }
        session.sendPacket(sm);
    }

    private String parse(List<Player> players) {
        StringBuffer context = new StringBuffer("");
        for (Player player : players) {
            String accountId = player.getAccountId();
            AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
            String accountName = accountEnt.getAccountInfo().getAccountName();
            context.append("账号id：[" + accountId + "] ");
            context.append("昵称：["+accountName+"] ");
            context.append("角色名：[" + player.getPlayerName()+"] " );
            context.append("职业:["+Job.valueOf(player.getCareer()).getJobName()+"] ");
            context.append("等级：["+player.getLevel()+"] ");
            context.append("位置：[" + player.getX() + "," + player.getY() + "] ");
            context.append("#");
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
        sm.setCareer(Job.valueOf(player.getCareer()).getJobName());
        sm.setLevel(player.getLevel());
        sm.setPlayerName(player.getPlayerName());
        sm.setX(player.getX());
        sm.setY(player.getY());
        session.sendPacket(sm);

    }

    @Override
    public void setScenceAccountId(int mapId, String accountId) {
        scenceManger.setScenceInfo(mapId, accountId);
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
        // TODO:
        Long playerId = playerIds.get(0);
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayer(playerId);
        Player player = playerEnt.getPlayer();
        player.setX(x);
        player.setY(y);
        refreshScenceInfo(accountInfo.getCurrentMapType().getMapid(),player);
        SpringContext.getPlayerSerivce().save(playerEnt);

        SM_Move sm = new SM_Move();
        sm.setStatus(1);
        sm.setX(x);
        sm.setY(y);
        session.sendPacket(sm);
    }

    @Override
    public MapResource getMapResource(int mapId) {
        return (MapResource) StorageManager.getResource(MapResource.class, mapId);
    }

    @Override
    public void showMap(int mapId) {
        ScenceInfo scenceInfo = scenceManger.getScenceInfo(mapId);
        if(scenceInfo==null){
            return;
        }
        List<Player> players = scenceInfo.getPlayers();

        Map<String, TSession> accountSessionMap = sessionManager.getAccountSessionMap();
        /** 拼接位置字符串*/
        StringBuffer str = new StringBuffer();
        for(Player player:players){
            str.append(player.getX()+","+player.getY()+":");
        }
        for(Player player:players){
            TSession session = accountSessionMap.get(player.getAccountId());
            if(session==null){
                return;
            }
            SM_OnlinePlayerOperate sm = new SM_OnlinePlayerOperate();
            sm.setScenePositions(str.toString());
            session.sendPacket(sm);
        }


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
                logger.warn("不能移动到x={},y={}", x, y);
                return false;
            }
            if (Integer.parseInt(mapY[y - 1]) == 1 && x == i) {
                logger.warn("不能移动到x={},y={}", x, y);
                return false;
            }
            if (Integer.parseInt(mapY[y - 1]) == 0 && x == i) {
                return true;
            }
        }
        return false;
    }


}
