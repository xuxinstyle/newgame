package com.game.scence.visible.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.role.player.model.Player;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 10:19
 */
public class ChangeMapCommand extends AbstractSceneCommand {

    private Player player;
    /**
     * 目标地图id
     */
    private int targetMapId;

    public ChangeMapCommand(Player player,int mapId,int sceneId){
        super(mapId,sceneId,player.getAccountId());
        this.player = player;

    }
    public static ChangeMapCommand valueOf(Player player,int targetMapId){
        ChangeMapCommand command = new ChangeMapCommand(player,player.getCurrMapId(),player.getCurrSceneId());
        command.setTargetMapId(targetMapId);
        return command;
    }

    public int getTargetMapId() {
        return targetMapId;
    }

    public void setTargetMapId(int targetMapId) {
        this.targetMapId = targetMapId;
    }

    @Override
    public String getName() {
        return "ChangeMapCommand";
    }

    @Override
    public void active() {
        SpringContext.getScenceSerivce().doChangeMap(player,targetMapId);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
