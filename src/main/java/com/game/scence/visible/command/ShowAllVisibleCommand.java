package com.game.scence.visible.command;

import com.game.SpringContext;
import com.game.base.executor.scene.impl.AbstractSceneCommand;
import com.game.scence.base.model.AbstractScene;
import com.game.scence.visible.packet.SM_ShowAllVisibleInfo;
import com.game.scence.visible.packet.bean.VisibleVO;
import com.game.util.SendPacketUtil;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/18 23:15
 */
public class ShowAllVisibleCommand extends AbstractSceneCommand {


    public ShowAllVisibleCommand(int mapId, int sceneId, String accountId) {
        super(mapId, sceneId, accountId);
    }

    public static ShowAllVisibleCommand valueOf(int mapId, int sceneId, String accountId) {
        ShowAllVisibleCommand command = new ShowAllVisibleCommand(mapId, sceneId, accountId);
        return command;
    }

    @Override
    public String getName() {
        return "ShowAllVisibleCommand";
    }

    @Override
    public void active() {
        AbstractScene scence = SpringContext.getScenceSerivce().getScene(getMapId());
        if (scence == null) {
            return;
        }
        SM_ShowAllVisibleInfo sm = new SM_ShowAllVisibleInfo();
        List<VisibleVO> visibleVOList = scence.getVisibleVOList();
        sm.setVisibleVOList(visibleVOList);
        SendPacketUtil.send(getAccountId(), sm);
    }
}
