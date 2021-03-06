package com.game.world.hopetower.service;

import com.game.SpringContext;
import com.game.base.executor.ICommand;
import com.game.role.player.model.Player;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.base.model.HopeTowerScene;
import com.game.scence.fight.model.PlayerUnit;
import com.game.scence.visible.command.EnterMapCommand;
import com.game.scence.visible.constant.MapType;
import com.game.scence.visible.resource.MapResource;
import com.game.scence.visible.service.ScenceManger;
import com.game.util.SendPacketUtil;
import com.game.world.base.entity.MapInfoEnt;
import com.game.world.base.model.AbstractMapInfo;
import com.game.world.base.model.MapInfo;
import com.game.world.hopetower.command.HopeTowerSettlementCommand;
import com.game.world.hopetower.model.HopeTowerInfo;
import com.game.world.hopetower.packet.SM_ShowHopeTowerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 15:46
 */
@Component
public class HopeTowerServiceImpl implements HopeTowerService {
    /**
     * 查看希望之塔信息
     *
     * @param accountId
     */
    @Override
    public void showHopeTowerInfo(String accountId) {
        MapInfoEnt mapInfoEnt = SpringContext.getMapInfoService().getMapInfoEnt(accountId);
        MapInfo mapInfo = mapInfoEnt.getMapInfo();
        Map<Integer, AbstractMapInfo> infoMap = mapInfo.getInfoMap();
        AbstractMapInfo abstractMapInfo = infoMap.get(MapType.HOPE_TOWER.getId());
        if (abstractMapInfo == null) {
            return;
        }
        HopeTowerInfo hopeTowerInfo = (HopeTowerInfo) abstractMapInfo;

        SM_ShowHopeTowerInfo sm = new SM_ShowHopeTowerInfo();
        sm.setCurrMapId(hopeTowerInfo.getCurrMapId());
        SendPacketUtil.send(accountId, sm);
    }

    @Override
    public void doMonsterDead(int mapId, int sceneId, String accountId) {

        AbstractScene scene = SpringContext.getScenceSerivce().getScene(mapId, accountId);
        if (scene == null) {
            return;
        }
        if (scene instanceof HopeTowerScene) {
            HopeTowerScene hopeTowerScene = (HopeTowerScene) scene;
            if (hopeTowerScene.doCheckAndEnd()) {
                SpringContext.getScenceSerivce().removeCopyScene(accountId);
            }
        }
    }


    @Override
    public void doPlayerDeadEvent(PlayerUnit playerUnit) {

        int mapId = playerUnit.getMapId();
        MapResource mapResource = SpringContext.getScenceSerivce().getMapResource(mapId);
        if (mapResource.getMapType() != MapType.HOPE_TOWER.getMapId()) {
            return;
        }
        AbstractScene scene = playerUnit.getScene();
        if (scene != null) {
            scene.doEnd();
            SpringContext.getScenceSerivce().removeCopyScene(playerUnit.getAccountId());
        }

    }

    @Override
    public void exit(String accountId) {
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        int currMapId = player.getCurrMapId();
        MapType mapType = MapType.getMapType(currMapId);
        if (mapType != MapType.HOPE_TOWER) {
            return;
        }

        AbstractScene scene = SpringContext.getScenceSerivce().getScene(currMapId, accountId);
        if (scene != null) {
            // 停止时间倒计时
            scene.clearCommand();
            // 立刻结算
            HopeTowerSettlementCommand command = new HopeTowerSettlementCommand(scene.getMapId(), scene.getSceneId(), 0, accountId);
            SpringContext.getSceneExecutorService().submit(command);
        }
    }
}
