package com.game.scence.visible.service;

import com.game.SpringContext;
import com.game.common.exception.RequestException;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.command.PlayerLevelSyncCommand;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.visible.command.*;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.*;
import com.game.scence.visible.packet.bean.VisibleVO;
import com.game.scence.visible.resource.MapResource;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.user.account.packet.SM_EnterCreatePlayer;
import com.game.util.I18nId;
import com.socket.core.session.SessionManager;
import com.socket.core.session.TSession;
import com.game.util.SendPacketUtil;
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
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        EnterMapCommand command = EnterMapCommand.valueOf(player, player.getCurrMapId(), targetMapId);
        SpringContext.getSceneExecutorService().submit(command);
    }

    /**
     * TODO:等玩家
     *
     * @param targetMapId
     * @param accountId
     */
    @Override
    public void doEnterMap(String accountId, int targetMapId) {
        try {
            MapResource mapResource = getMapResource(targetMapId);
            if(mapResource==null){
                return;
            }
            AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
            PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(accountId);
            Player player = playerEnt.getPlayer();
            player.setCurrMapId(targetMapId);
            /** 初始化位置*/
            Position position = Position.valueOf(mapResource.getInitX(), mapResource.getInitY());
            player.setPosition(position);

            AbstractScene scence = scenceMangaer.getScence(targetMapId);

            /** 在新的场景中添加玩家战斗单元信息.*/
            scence.enter(player);
            /**
             * 通知客户端
             */
            player.setChangeMapId(false);
            SpringContext.getPlayerSerivce().save(playerEnt);
            SpringContext.getAccountService().save(accountEnt);
            SendPacketUtil.send(accountId,SM_EnterMap.valueOf(accountId,targetMapId));
        }catch (Exception e){
            /**
             * 进入地图错误返回新手村
             */
            AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
            PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(accountId);
            Player player = playerEnt.getPlayer();
            player.setChangeMapId(false);
            SpringContext.getAccountService().save(accountEnt);
            SpringContext.getScenceSerivce().changeMap(accountId,MapType.NoviceVillage.getMapId(),false);
            SendPacketUtil.send(accountId,SM_EnterMapErr.valueOf(2));
            e.printStackTrace();
        }
    }

    @Override
    public void doLogoutBefore(String accountId) {
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(accountId);
        Player player = playerEnt.getPlayer();
        int currMapId = player.getCurrMapId();
        player.setLastLogoutMapId(currMapId);

        LeaveMapCommand command = new LeaveMapCommand(currMapId,0,accountId);
        SpringContext.getSceneExecutorService().submit(command);
    }

    /**
     * 做进入地图前需要做的事，即离开地图需要做的事,清除之前的场景中的信息
     *
     * @param
     * @param
     */
    @Override
    public void leaveMap(String accountId) {
        /**
         * TODO:在有离开或进入地图的条件的情况下 需要判断能否离开地图 如果不能离开地图 离开失败怎么办
         * 只有在副本的时候再会存在离开地图失败 在这种情况下可以断线重连副本
         */
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        int currentMapId = player.getCurrMapId();
        /** 1.清除上次地图中玩家存的信息*/
        AbstractScene scence = scenceMangaer.getScence(currentMapId);
        scence.leave(player);
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        accountEnt.getAccountInfo().getIsChangeMap().getAndSet(true);
        /**
         * 显示地图
         */
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
     * 以后显示的信息会增加，先暂时传这些数据 这里需要抛到场景线程中拿吗
     *
     * @param session
     * @param mapId
     */
    @Override
    public void showAllVisibleInfo(TSession session, int mapId) {
        AbstractScene scenceInfo = scenceMangaer.getScence(mapId);
        SM_ShowAllVisibleInfo sm = new SM_ShowAllVisibleInfo();
        List<VisibleVO> visibleVOList = scenceInfo.getVisibleVOList();
        sm.setVisibleVOList(visibleVOList);
        session.sendPacket(sm);
    }
    @Override
    public void showAccountIdInfo(TSession session, int mapId, long objectId) {
        AbstractScene scence = scenceMangaer.getScence(mapId);
        if(scence==null){
            if(logger.isDebugEnabled()) {
                logger.debug("玩家[{}]查看的场景为空");
            }
            return;
        }

        Map<Long, CreatureUnit> creatureUnitMap = scence.getCreatureUnitMap();
        CreatureUnit creatureUnit = creatureUnitMap.get(objectId);
        /**
         * 如果是玩家战斗单元则发送消息给客户端 否则不发消息
         */
        if(creatureUnit instanceof PlayerUnit){
            session.sendPacket(SM_ShowAccountInfo.valueOf((PlayerUnit) creatureUnit));
        }
    }

    @Override
    public void move(String accountId, Position targetPos, int mapId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        MoveCommand command = MoveCommand.valueOf(mapId, player, player.getPosition(), targetPos);
        SpringContext.getSceneExecutorService().submit(command);
    }

    @Override
    public void doMove(String accountId, Position targetPos, int mapId) {
        if (!checkMove(mapId, targetPos)) {
            if(logger.isDebugEnabled()){
                logger.debug("不能移动到位置{}",targetPos.toString());
            }
            RequestException.throwException(I18nId.MOVE_ERROR);
        }
        AbstractScene scence = scenceMangaer.getScence(mapId);
        scence.move(accountId,targetPos);
        SM_Move sm = new SM_Move();
        sm.setStatus(1);
        sm.setPosition(targetPos);
        SendPacketUtil.send(accountId,sm);
    }
    /**
     * 角色将属性同步到场景中
     * @param player
     */
    @Override
    public void doPlayerUpLevelSyncScene(Player player) {
        PlayerLevelSyncCommand command = PlayerLevelSyncCommand.valueOf(player.getCurrMapId(), player.getAccountId(), player);
        SpringContext.getSceneExecutorService().submit(command);
    }

    @Override
    public void changeMap(String accountId, int targetMapId,boolean clientRequest) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        ChangeMapCommand command = ChangeMapCommand.valueOf(player, targetMapId);
        SpringContext.getSceneExecutorService().submit(command);
    }
    @Override
    public void doChangeMap(Player player,int targetMapId){
        try {
            if(player.isChangeMap()){
                return;
            }
            if(!checkChangeMap(player,targetMapId)){
                SendPacketUtil.send(player,SM_ChangeMapErr.valueOf(2));
                RequestException.throwException(I18nId.CHANGE_MAP_ERROE);
                return;
            }
            /**
             * 离开当前地图
             */
            leaveMap(player.getAccountId());
            /**
             * 进入地图
             */
            EnterMapCommand command = EnterMapCommand.valueOf(player,player.getCurrMapId(),targetMapId);
            SpringContext.getSceneExecutorService().submit(command);
        }catch (Exception e){
            AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(player.getAccountId());
            accountEnt.getAccountInfo().getIsChangeMap().getAndSet(false);
            SendPacketUtil.send(player,SM_ChangeMapErr.valueOf(2));
            logger.error("玩家[{}]请求从[{}]进入[{}]地图失败,失败原因[{}]",player.getAccountId(),player.getCurrMapId(),targetMapId,e);
            RequestException.throwException(I18nId.CHANGE_MAP_ERROE);

        }
    }

    /**
     * TODO:判断进入地图的条件
     * @param player
     * @param targetMapId
     * @return
     */
    private boolean checkChangeMap(Player player,int targetMapId) {
        if(player.getCurrMapId()==targetMapId){
            return false;
        }
        MapResource resource = scenceMangaer.getResource(targetMapId, MapResource.class);
        if(resource==null){
            return false;
        }
        return true;
    }

    @Override
    public MapResource getMapResource(int mapId) {
        return scenceMangaer.getResource( mapId,MapResource.class);
    }

    /**
     * TODO:放进场景线程
     * @param mapId
     */
    @Override
    public void showMap(int mapId) {
        AbstractScene scence = scenceMangaer.getScence(mapId);
        if(scence==null){
            return;
        }

        Map<Integer, List<Position>> visiblePosition = scence.getVisiblePosition();
        List<String> accountIds = scence.getAccountIds();
        SM_ScenePositionVisible sm = new SM_ScenePositionVisible();
        sm.setPositionMap(visiblePosition);
        for(String accountId:accountIds){
            SendPacketUtil.send(accountId,sm);
        }

    }

    private boolean checkMove(int mapId, Position targetPos) {
        MapResource mapResource = getMapResource(mapId);
        int[][] mapcontext = mapResource.getMapcontext();
        if(targetPos.getY() >= mapcontext.length||targetPos.getY()<0||
                targetPos.getX()>=mapcontext[0].length||targetPos.getX()<0){
            return false;
        }
        if(mapcontext[targetPos.getX()][targetPos.getY()]==-1){
            return false;
        }
        return true;
    }

    @Override
    public AbstractScene getScene(int mapId) {
        return scenceMangaer.getScence(mapId);
    }
}
