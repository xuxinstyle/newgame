package com.game.scence.visible.service;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.common.exception.RequestException;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.command.PlayerLevelSyncCommand;
import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.visible.command.*;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.model.Position;
import com.game.scence.visible.packet.*;
import com.game.scence.visible.resource.MapResource;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.user.account.packet.SM_EnterCreatePlayer;
import com.game.user.task.event.EnterMapEvent;
import com.game.util.I18nId;
import com.game.world.base.entity.MapInfoEnt;
import com.game.world.base.model.AbstractMapInfo;
import com.game.world.base.model.MapInfo;
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
    public void loginAfterEnterMap(TSession session, String accountId, int targetMapId) {
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
        EnterMapCommand command = EnterMapCommand.valueOf(player, player.getCurrSceneId(), targetMapId, true);
        SpringContext.getSceneExecutorService().submit(command);
    }

    @Override
    public void putPlayerUnit(Player player) {
        scenceMangaer.putPlayerUnit(player);
    }

    @Override
    public PlayerUnit getPlayerUnit(Player player) {
        return scenceMangaer.getPlayerUnit(player);
    }
    /**
     * TODO:等玩家
     *  @param player
     * @param targetMapId
     * @param loginEnterRequest
     */
    @Override
    public void doEnterMap(Player player, int targetMapId, boolean loginEnterRequest) {
        try {
            // 在抛EnterMapCommand之前已经做了验证 所以mapResource不可能会等于null
            MapResource mapResource = getMapResource(targetMapId);
            /** 初始化位置*/
            player.setPosition(Position.valueOf(mapResource.getInitX(), mapResource.getInitY()));

            AbstractScene scence = scenceMangaer.getScence(targetMapId, player.getAccountId());
            // 只要场景为null,就进入新手村
            if (scence == null) {
                // 第一次进入的地图为空时直接让玩家 进入新手村
                scence = scenceMangaer.getScence(MapType.NoviceVillage.getId(), player.getAccountId());
            }
            // 进入地图
            /** 在新的场景中添加玩家战斗单元信息.*/
            scence.doEnter(player);
            player.setChangeIngMap(false);
            // 抛 进入地图事件
            SpringContext.getEvenManager().syncSubmit(EnterMapEvent.valueOf(player.getAccountId(), targetMapId));
            /**
             * 通知客户端 登陆时请求进入，
             */
            if (loginEnterRequest) {
                SendPacketUtil.send(player, SM_LoginEnterMap.valueOf(player.getAccountId(), scence.getMapId()));
            } else {
                SendPacketUtil.send(player, SM_EnterMap.valueOf(player.getAccountId(), scence.getMapId()));
            }
        }catch (Exception e){
            /**
             * 进入地图错误返回新手村
             */
            logger.error("[{}]进入地图失败，原因[{}] 进入新手村", player.getAccountId(), e);
            //player.setChangeIngMap(false);
            EnterMapCommand command = EnterMapCommand.valueOf(player, player.getCurrSceneId(), MapType.NoviceVillage.getMapId(), loginEnterRequest);
            SpringContext.getSceneExecutorService().submit(command);
            SendPacketUtil.send(player, SM_EnterMapErr.valueOf(2));
        }
    }

    @Override
    public void doLogoutBefore(String accountId) {
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(accountId);
        Player player = playerEnt.getPlayer();
        player.setLastLogoutMapId(player.getCurrMapId());

        // 离开地图需要到对应的场景线程中处理数据
        LeaveMapCommand command = new LeaveMapCommand(player.getCurrMapId(), player.getCurrSceneId(), accountId, false);
        SpringContext.getSceneExecutorService().submit(command);
    }

    /**
     * 做进入地图前需要做的事，即离开地图需要做的事,清除之前的场景中的信息
     * @param
     * @param
     * @param targetScene
     * @param clientRequest
     */
    @Override
    public void leaveMap(String accountId, AbstractScene targetScene, boolean clientRequest) {
        /**
         *
         * 离开失败的时候留在原地图
         * 只有在副本的时候再会存在离开地图失败 在这种情况下可以断线重连副本
         * 或者通过发包发来一个错的mapId也有可能
         */
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        int currentMapId = player.getCurrMapId();
        /** 1.清除上次地图中玩家存的信息*/
        AbstractScene oldScence = scenceMangaer.getScence(currentMapId, player.getAccountId());
        // 登出，强制让玩家离开
        if (!clientRequest) {
            if (oldScence == null) {
                return;
            }
            oldScence.doLeave(player);
            return;
        }
        // 能否离开
        if (!oldScence.isCanLeave()) {
            RequestException.throwException(I18nId.LEAVE_MAP_ERROR);
        }
        // 能否进入目标地图  只有玩家登出的时候才调用LeaveMapCommand
        if (!oldScence.canChangeToMap(player, targetScene.getMapId())) {
            RequestException.throwException(I18nId.NOT_ENTER_MAP);
        }
        player.setChangeIngMap(true);
        // 离开
        oldScence.doLeave(player);
        /**
         * 显示地图
         */
        showMap(currentMapId, accountId);

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
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId, session.getAccountId());
        if (scene == null) {
            return;
        }
        ShowAllVisibleCommand command = ShowAllVisibleCommand.valueOf(mapId, scene.getSceneId(), session.getAccountId());
        SpringContext.getSceneExecutorService().submit(command);
    }

    @Override
    public void showObjectInfo(String accountId, int mapId, ObjectType objectType, long objectId) {
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId, accountId);
        if (scene == null) {
            return;
        }
        // todo：这里的sceneId 在做副本的时候再考虑换成对应的sceneId
        ShowTargetCommand command = ShowTargetCommand.valueOf(mapId, scene.getSceneId(), accountId, objectType, objectId);
        SpringContext.getSceneExecutorService().submit(command);

    }
    @Override
    public void move(long playerId, Position targetPos, int mapId) {
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        MoveCommand command = MoveCommand.valueOf(player, targetPos);
        SpringContext.getSceneExecutorService().submit(command);
    }

    @Override
    public void doMove(Player player, Position targetPos, int mapId) {
        if (!checkMove(mapId, targetPos)) {
            if(logger.isDebugEnabled()){
                logger.debug("不能移动到位置{}",targetPos.toString());
            }
            RequestException.throwException(I18nId.MOVE_ERROR);
        }
        AbstractScene scence = scenceMangaer.getScence(mapId, player.getAccountId());
        if (scence == null) {
            RequestException.throwException(I18nId.MOVE_ERROR);
        }
        if (!scence.moveAndThrow(player.getObjectId(), targetPos)) {
            SM_Move sm = new SM_Move();
            sm.setStatus(0);
            sm.setPosition(targetPos);
            SendPacketUtil.send(player, sm);
            return;
        }
        SM_Move sm = new SM_Move();
        sm.setStatus(1);
        sm.setPosition(targetPos);
        SendPacketUtil.send(player, sm);
    }
    /**
     * 角色将属性同步到场景中
     * @param player
     */
    @Override
    public void doPlayerUpLevelSync(Player player) {
        PlayerLevelSyncCommand command = PlayerLevelSyncCommand.valueOf(player.getCurrMapId(), player.getAccountId(), player);
        SpringContext.getSceneExecutorService().submit(command);
    }

    @Override
    public void removeCopyScene(String accountId) {
        scenceMangaer.removeCopyScene(accountId);
    }

    @Override
    public void changeMap(String accountId, int targetMapId,boolean clientRequest) {

        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        if (player.isChangeIngMap()) {
            RequestException.throwException(I18nId.CHANGE_MAP_ERROE);
        }
        AbstractScene scene = getScene(player.getCurrMapId(), accountId);
        if (scene == null) {
            RequestException.throwException(I18nId.CHANGE_MAP_ERROE);
        }
        if (!scene.isCanLeave()) {
            RequestException.throwException(I18nId.LEAVE_MAP_ERROR);
        }
        // 玩家当前地图无法去目标地图
        if (!scene.canChangeToMap(player, targetMapId)) {
            RequestException.throwException(I18nId.NOT_ENTER_MAP);
        }

        // 个人副本通过accountId获取场景
        AbstractScene targetScene = getScene(targetMapId, accountId);
        // 如果当前的地图是副本 切目标地图也是副本 则不能切图 只有目标地图不是副本才能从副本切 每个地图能否切至其他地图 写在抽象方法canChangeMap中
        // 是副本 初始化地图
        if (targetScene == null) {
            targetScene = checkAndInitScene(targetMapId, player);
        }

        // 判断目标地图能否进入
        if (!targetScene.canEnter(player)) {
            RequestException.throwException(I18nId.ENTER_MAP_ERROR);
        }

        ChangeMapCommand command = ChangeMapCommand.valueOf(player, targetScene, clientRequest);
        SpringContext.getSceneExecutorService().submit(command);
    }

    public AbstractScene checkAndInitScene(int mapId, Player player) {
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(mapId);
        // 前面已经验证mapResource 此处不需要再验证
        MapType mapType = MapType.getMapTypeMap().get(mapResource.getMapType());
        if (mapType == null) {
            RequestException.throwException(I18nId.NOT_ENTER_MAP);
        }
        // 判断副本是否开启
        if (!isOpenMap(player, mapId, mapType)) {
            RequestException.throwException(I18nId.NOT_OPEN_MAP);
        }

        //初始化创建场景处理类
        AbstractScene scenceInfo = mapType.create();
        scenceInfo.setMapId(mapId);
        scenceInfo.init();
        scenceMangaer.putCopyScene(player.getAccountId(), scenceInfo);
        return scenceInfo;
    }

    private boolean isOpenMap(Player player, int mapId, MapType mapType) {
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(player.getAccountId());
        MapInfo mapInfo = mapInfoEnt.getMapInfo();
        Map<Integer, AbstractMapInfo> infoMap = mapInfo.getInfoMap();
        AbstractMapInfo abstractMapInfo = infoMap.get(mapType.getId());
        if (abstractMapInfo.isOpen(mapId)) {
            return true;
        }
        return false;
    }

    @Override
    public void doChangeMap(Player player, AbstractScene targetScene, boolean clientRequest) {
        try {

            if (player.isChangeIngMap()) {
                return;
            }
            /**
             * 离开当前地图
             */
            leaveMap(player.getAccountId(), targetScene, clientRequest);

            /**
             * 进入地图
             */
            EnterMapCommand command = EnterMapCommand.valueOf(player, targetScene.getSceneId(), targetScene.getMapId(), false);
            SpringContext.getSceneExecutorService().submit(command);
        }catch (Exception e){

            //设置切图状态
            player.setChangeIngMap(false);
            logger.error("玩家[{}]请求从[{}]进入[{}]地图失败,失败原因[{}]", player.getAccountId(), player.getCurrMapId(), targetScene.getMapId(), e);
            SM_ChangeMapErr sm = SM_ChangeMapErr.valueOf(2);
            SendPacketUtil.send(player, sm);
        }
    }


    @Override
    public MapResource getMapResource(int mapId) {
        return scenceMangaer.getResource( mapId,MapResource.class);
    }

    /**
     * TODO:放进场景线程
     * @param mapId
     * @param accountId
     */
    @Override
    public void showMap(int mapId, String accountId) {
        AbstractScene scence = scenceMangaer.getScence(mapId, accountId);
        if(scence==null){
            return;
        }

        Map<Integer, List<Position>> visiblePosition = scence.getVisiblePosition();
        List<String> accountIds = scence.getAccountIds();
        SM_ScenePositionVisible sm = new SM_ScenePositionVisible();
        sm.setPositionMap(visiblePosition);
        for (String account : accountIds) {
            SendPacketUtil.send(account, sm);
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
    public AbstractScene getScene(int mapId, String accountId) {
        return scenceMangaer.getScence(mapId, accountId);
    }

    @Override
    public ScenceManger getScenceMangaer() {
        return scenceMangaer;
    }

    public void setScenceMangaer(ScenceManger scenceMangaer) {
        this.scenceMangaer = scenceMangaer;
    }
}
