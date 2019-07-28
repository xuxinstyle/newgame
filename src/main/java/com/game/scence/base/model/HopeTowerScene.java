package com.game.scence.base.model;

import com.game.SpringContext;
import com.game.base.executor.ICommand;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.MonsterUnit;
import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.resource.MapResource;
import com.game.user.task.event.PassMapEvent;
import com.game.util.CommonUtil;
import com.game.util.SendPacketUtil;
import com.game.world.base.entity.MapInfoEnt;
import com.game.world.base.model.AbstractMapInfo;
import com.game.world.base.model.MapInfo;
import com.game.world.hopetower.command.HopeTowerSettlementCommand;
import com.game.world.hopetower.packet.SM_CloseScene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 希望之塔副本
 *
 * @Author：xuxin
 * @Date: 2019/7/22 10:43
 */
public class HopeTowerScene extends AbstractMonsterScene {

    private static final Logger logger = LoggerFactory.getLogger(HopeTowerScene.class);
    /**
     * 副本倒计时的command   《command.getClass,command》
     */
    Map<Class<? extends ICommand>, ICommand> commandMap = new HashMap<>();
    /**
     * 玩家战斗单元
     */
    private PlayerUnit playerUnit;

    /**
     * 在副本中玩家不能离开
     */
    private boolean isCanLeave = false;


    @Override
    public void init() {
        //初始化怪物
        Map<Long, MonsterUnit> monsterUnits = SpringContext.getMonsterService().getMonsterUnits(getMapId());
        setMonsterUnits(monsterUnits);
        // 设置场景id
        setSceneId(SCENE_ID.getAndDecrement());

        for (MonsterUnit monsterUnit : monsterUnits.values()) {
            monsterUnit.setScene(this);
        }
    }

    private void countdown() {
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(getMapId());
        long duration = mapResource.getDuration();
        HopeTowerSettlementCommand command = new HopeTowerSettlementCommand(this, playerUnit.getAccountId(), duration);
        SpringContext.getSceneExecutorService().submit(command);
        commandMap.put(command.getClass(), command);
    }

    /**
     * 每个怪物死亡一次就check一次
     */
    public void doCheckEnd() {
        //如果怪物全死光了 则做结算
        if (checkMonsterDeadAll()) {
            // 停止时间倒计时
            for (ICommand command : getCommandMap().values()) {
                command.cancel();
                getCommandMap().clear();
            }
            // 做结算
            doEnd();
        }
    }

    /**
     * 离开当前地图做结算
     *
     * @param player
     */
    @Override
    public void doLeave(Player player) {
        // 停止时间倒计时
        for (ICommand command : getCommandMap().values()) {
            command.cancel();
            getCommandMap().clear();
        }
    }

    public boolean checkMonsterDeadAll() {
        boolean isAllDeal = false;
        for (MonsterUnit monsterUnit : getMonsterUnits().values()) {
            if (monsterUnit.isDead()) {
                isAllDeal = true;
            }
            if (!monsterUnit.isDead()) {
                isAllDeal = false;
                break;
            }
        }
        return isAllDeal;
    }

    /**
     * fixme:不能在changemap处调用该方法
     * 在调用该方法前不能
     * 副本时间到了的 结算奖励
     */
    @Override
    public void doEnd() {
        // 如果怪物死光了则发送奖励
        // 通过
        if (checkMonsterDeadAll()) {
            MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(getMapId());

            //如果是首次通关则发送首次通关奖励
            MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(playerUnit.getAccountId());
            MapInfo mapInfo = mapInfoEnt.getMapInfo();
            Map<Integer, AbstractMapInfo> infoMap = mapInfo.getInfoMap();
            AbstractMapInfo abstractMapInfo = infoMap.get(getMapId());
            int rewardId = 0;
            // 首次通关
            if (!abstractMapInfo.isPass(getMapId())) {
                rewardId = mapResource.getFirstReward();
                // 开启下一个关卡
                abstractMapInfo.openNext(getMapId());
            } else {
                // 非首次通关
                rewardId = mapResource.getRepeatReward();
            }

            int finalRewardId = rewardId;
            // 抛回账号线程发奖
            SpringContext.getAccountExecutorService().addTask(playerUnit.getAccountId(), "HopeTowerSceneDoEnd", () -> {
                SpringContext.getRewardService().doReward(playerUnit.getAccountId(), finalRewardId);
            });
            // 抛通关事件
            SpringContext.getEvenManager().syncSubmit(PassMapEvent.valueOf(playerUnit.getAccountId(), getMapId()));
            // 保存通关状态
            SpringContext.getMapInfoService().save(mapInfoEnt);
            // 通知客户端副本通过成功关闭
            Map<Long, CreatureUnit> creatureUnitMap = getCreatureUnitMap();
            for (CreatureUnit unit : creatureUnitMap.values()) {
                SM_CloseScene sm = new SM_CloseScene();
                sm.setStatus(1);
                SendPacketUtil.send(unit.getAccountId(), sm);
            }

        } else {
            // 通知客户端副本关闭 通过失败
            Map<Long, CreatureUnit> creatureUnitMap = getCreatureUnitMap();
            for (CreatureUnit unit : creatureUnitMap.values()) {
                SM_CloseScene sm = new SM_CloseScene();
                sm.setStatus(2);
                SendPacketUtil.send(unit.getAccountId(), sm);
            }
        }

        setCanLeave(true);
        // 离开地图，前往新手村
        SpringContext.getScenceSerivce().changeMap(playerUnit.getAccountId(), CommonUtil.NoviceVillage, false);
        SpringContext.getScenceSerivce().removeCopyScene(playerUnit.getAccountId());


    }


    @Override
    public CreatureUnit getUnit(ObjectType objectType, long objectId) {
        if (objectType == ObjectType.MONSTER) {
            return getMonsterUnits().get(objectId);
        }
        if (objectType == ObjectType.PLAYER) {
            return getCreatureUnitMap().get(objectId);
        }
        return null;
    }

    @Override
    public boolean canChangeToMap(int targetMapId) {
        if (!isCanLeave()) {
            return false;
        }
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(targetMapId);
        if (mapResource == null) {
            return false;
        }
        if (mapResource.getMapType() == MapType.NoviceVillage.getMapId()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canEnter(Player player) {
        int currMapId = player.getCurrMapId();
        if (currMapId == MapType.NoviceVillage.getMapId()) {
            return true;
        }
        return false;
    }


    @Override
    public boolean isCanUseSkill() {
        return true;
    }

    @Override
    public void doEnter(Player player) {
        super.doEnter(player);
        // 从缓存中拿playerUnit
        PlayerUnit playerUnit = SpringContext.getScenceSerivce().getPlayerUnit(player);
        this.playerUnit = playerUnit;
        //抛出副本定时事件
        countdown();
    }

    @Override
    public boolean isCanLeave() {
        return isCanLeave;
    }

    public void setCanLeave(boolean canLeave) {
        isCanLeave = canLeave;
    }

    public Map<Class<? extends ICommand>, ICommand> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<Class<? extends ICommand>, ICommand> commandMap) {
        this.commandMap = commandMap;
    }

    public PlayerUnit getPlayerUnit() {
        return playerUnit;
    }

    public void setPlayerUnit(PlayerUnit playerUnit) {
        this.playerUnit = playerUnit;
    }
}
