package com.game.scence.service;

import com.game.SpringContext;
import com.game.role.player.constant.Job;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.scence.command.EnterMapCommand;
import com.game.scence.command.MoveCommand;
import com.game.scence.event.PlayerMoveEvent;
import com.game.scence.model.PlayerPosition;
import com.game.scence.model.ScenceInfo;
import com.game.scence.packet.*;
import com.game.scence.packet.bean.PlayerVO;
import com.game.scence.resource.MapResource;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.user.account.packet.SM_EnterCreatePlayer;
import com.socket.core.session.SessionManager;
import com.socket.core.session.TSession;
import com.socket.utils.SendPacketUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
    private ScenceManger scenceMangaer;
    @Autowired
    private SessionManager sessionManager;

    @Override
    public void init() {
        scenceMangaer.init();
    }

    @Override
    public void enterMap(TSession session, String accountId, int targetMapId) {
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
        if(logger.isDebugEnabled()) {
            logger.debug("玩家[{}]有角色信息！开始进图地图", accountId);
        }
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        EnterMapCommand command = EnterMapCommand.valueOf(player, player.getCurrMapId(), targetMapId);
        SpringContext.getSceneExecutorService().submit(command);
    }

    public void refreshScenceInfo(int mapId, Player player){
        scenceMangaer.refreshScenceInfo(mapId,player);
    }
    /**
     * 进入地图
     *
     * @param targetMapId
     * @param accountId
     */
    @Override
    public void doEnterMap(String accountId, int targetMapId) {
        leaveMap(accountId);
        MapResource mapResource = getMapResource(targetMapId);
        if(mapResource==null){
            return;
        }
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        long playerId = accountInfo.getPlayerId();

        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();

        /** 初始化位置*/
        PlayerPosition playerPosition = PlayerPosition.valueOf(mapResource.getInitX(), mapResource.getInitY());
        player.setPosition(playerPosition);
        refreshScenceInfo(mapResource.getId(),player);
        player.setCurrentMapType(targetMapId);
        SpringContext.getPlayerSerivce().save(playerEnt);
        /** 在新的场景中添加玩家账号信息.*/
        setScencePlayer(targetMapId, player);
        if (mapResource == null) {
            logger.warn("{}资源加载错误", MapResource.class);
            return;
        }
        SM_EnterMap sm = new SM_EnterMap();
        sm.setPosition(player.getPosition());
        sm.setAccountId(accountId);
        sm.setMapId(targetMapId);
        sm.setStatus(1);
        SendPacketUtil.send(accountId,sm);
    }

    /**
     * 做进入地图前需要做的事，即离开地图需要做的事,清除之前的场景中的信息
     *
     * @param
     * @param
     */
    private void leaveMap(String accountId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        int currentMapId = player.getCurrentMapType();
        /** 1.清除上次地图中玩家存的缓存信息*/
        removeScenceAccountId(currentMapId, accountId);
        showMap(currentMapId);

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
        if (accountInfo.getAccountName() == null || "".equals(accountInfo.getAccountName())) {
            logger.info("玩家{}没有角色信息",accountId);
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
        ScenceInfo scenceInfo = scenceMangaer.getScenceInfo(mapId);
        SM_ShowAllAccountInfo sm = new SM_ShowAllAccountInfo();

        List<Player> playerList = scenceInfo.getPlayerList();
        List<PlayerVO> playerVOList = new ArrayList<>();
        for(Player player:playerList){
            AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(player.getAccountId());
            AccountInfo accountInfo = accountEnt.getAccountInfo();
            PlayerVO playerVO = new PlayerVO();
            playerVO.setAccountId(player.getAccountId());
            playerVO.setJobType(player.getPlayerJob());
            playerVO.setLevel(player.getLevel());
            playerVO.setNickName(accountInfo.getAccountName());
            playerVO.setPlayerName(player.getPlayerName());
            playerVO.setPosition(player.getPosition());
            playerVOList.add(playerVO);
        }
        sm.setPlayerVOList(playerVOList);
        session.sendPacket(sm);
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

        long playerId = accountInfo.getPlayerId();
        if (playerId == 0L) {
            logger.warn("玩家[" + accountId + "]没有创建角色");
            return;
        }
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        SM_ShowAccountInfo sm = new SM_ShowAccountInfo();
        sm.setAccountId(accountId);
        sm.setNickName(accountInfo.getAccountName());
        sm.setCareer(Job.valueOf(player.getPlayerJob()).getJobName());
        sm.setLevel(player.getLevel());
        sm.setPlayerName(player.getPlayerName());
        sm.setPosition(player.getPosition());
        session.sendPacket(sm);

    }
    @Override
    public void setScencePlayer(int mapId, Player player) {
        scenceMangaer.setScenceInfo(mapId,player);
    }

    @Override
    public void removeScenceAccountId(int mapId, String accountId) {
        scenceMangaer.removeAccountId(mapId, accountId);
    }


    @Override
    public void move(String accountId, PlayerPosition targetPos, int mapId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        MoveCommand command = MoveCommand.valueOf(mapId, player, player.getPosition(), targetPos);
        SpringContext.getSceneExecutorService().submit(command);
    }

    @Override
    public void doMove(String accountId, PlayerPosition targetPos, int mapId) {
        if (!checkMove(mapId, targetPos)) {
            if(logger.isDebugEnabled()){
                logger.debug("不能移动到当前位置");
            }
            SM_Move sm = new SM_Move();
            sm.setStatus(0);
            sm.setPosition(targetPos);
            SendPacketUtil.send(accountId, sm);
            return;
        }
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        long playerId = accountInfo.getPlayerId();

        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        PlayerPosition oldPosition = player.getPosition();
        player.setPosition(targetPos);
        SpringContext.getPlayerSerivce().save(playerEnt);
        PlayerMoveEvent event = PlayerMoveEvent.valueOf(mapId, player, oldPosition, targetPos);
        SpringContext.getEvenManager().syncSubmit(event);
        SM_Move sm = new SM_Move();
        sm.setStatus(1);
        sm.setPosition(targetPos);
        SendPacketUtil.send(accountId,sm);
    }

    @Override
    public void doPlayerMoveAfter(PlayerMoveEvent event) {
        refreshScenceInfo(event.getMapId(),event.getPlayer());
    }

    @Override
    public void doPlayerUpLevel( Player player) {
        int mapId = player.getCurrentMapType();
        refreshScenceInfo(mapId, player);
    }

    @Override
    public MapResource getMapResource(int mapId) {
        return scenceMangaer.getResource( mapId,MapResource.class);
    }

    @Override
    public void showMap(int mapId) {
        ScenceInfo scenceInfo = scenceMangaer.getScenceInfo(mapId);
        if(scenceInfo==null){
            return;
        }
        List<Player> players = scenceInfo.getPlayerList();

        Map<String, TSession> accountSessionMap = sessionManager.getAccountSessionMap();

        /**
         * 玩家的位置信息
          */
        List<PlayerPosition> playerPositionList = new ArrayList<>();
        for(Player player:players){
            playerPositionList.add(player.getPosition());
        }
        SM_OnlinePlayerOperate sm = new SM_OnlinePlayerOperate();
        sm.setPlayerPositionList(playerPositionList);
        for(Player player:players){
            TSession session = accountSessionMap.get(player.getAccountId());
            if(session==null){
                return;
            }

            session.sendPacket(sm);
        }
    }

    private boolean checkMove(int mapId, PlayerPosition targetPos) {
        MapResource mapResource = getMapResource(mapId);
        int[][] mapcontext = mapResource.getMapcontext();
        if(targetPos.getY() >= mapcontext.length||targetPos.getY()<0||
                targetPos.getX()>=mapcontext[0].length||targetPos.getX()<0){
            return false;
        }
        if(mapcontext[targetPos.getY()][targetPos.getX()]==1){
            return false;
        }
        return true;
    }


}
