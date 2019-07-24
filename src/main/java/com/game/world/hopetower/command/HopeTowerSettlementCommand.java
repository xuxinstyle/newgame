package com.game.world.hopetower.command;

import com.game.SpringContext;
import com.game.base.executor.account.impl.AbstractAccountCommand;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.base.executor.scene.impl.AbstractSceneDelayCommand;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.base.model.HopeTowerScene;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 20:39
 */
public class HopeTowerSettlementCommand extends AbstractSceneDelayCommand {


    public HopeTowerSettlementCommand(int mapId, int sceneId, long delay, String accountId) {
        super(mapId, sceneId, delay, accountId);
    }

    public HopeTowerSettlementCommand(AbstractScene scene, String accountId, long delay) {
        super(scene, accountId, delay);
    }

    @Override
    public String getName() {
        return "HopeTowerSettlementCommand";
    }

    @Override
    public void active() {
        HopeTowerScene scene = (HopeTowerScene) getScene();
        scene.doEnd();
    }
}
