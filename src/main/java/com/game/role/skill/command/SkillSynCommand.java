package com.game.role.skill.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.model.Player;
import com.game.role.skill.model.SkillInfo;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.fight.model.CreatureUnit;
import com.game.scence.fight.model.PlayerUnit;

/**
 * @Author：xuxin
 * @Date: 2019/7/15 21:46
 */
public class SkillSynCommand extends AbstractSceneCommand {
    /**
     * 技能信息
     */
    private SkillInfo skillInfo;
    /**
     *
     */
    private Player player;

    public static SkillSynCommand valueOf(Player player, SkillInfo skillInfo) {
        SkillSynCommand command = new SkillSynCommand(player.getCurrMapId(), player.getCurrSceneId(), player.getAccountId());
        command.setSkillInfo(skillInfo);
        command.setPlayer(player);
        return command;
    }

    public SkillSynCommand(int mapId, int sceneId, String accountId) {
        super(mapId, sceneId, accountId);
    }

    @Override
    public String getName() {
        return "SkillSynCommand";
    }

    @Override
    public void active() {
        SkillInfo skillInfo = this.skillInfo;
        AbstractScene scene = SpringContext.getScenceSerivce().getScene(getMapId(), player.getAccountId());
        CreatureUnit unit = scene.getUnit(ObjectType.PLAYER, player.getObjectId());
        unit.setSkillInfo(skillInfo);
    }

    public SkillInfo getSkillInfo() {
        return skillInfo;
    }

    public void setSkillInfo(SkillInfo skillInfo) {
        this.skillInfo = skillInfo;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
