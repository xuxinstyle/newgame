package com.game.scence.command;

import com.game.SpringContext;
import com.game.base.executor.scene.Impl.AbstractSceneCommand;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author：xuxin
 * @Date: 2019/6/14 9:21
 */
public class EnterMapCommand extends AbstractSceneCommand {
    private static final Logger logger = LoggerFactory.getLogger(EnterMapCommand.class);
    private TSession session;
    private String accountId;

    public EnterMapCommand(TSession session, String accountId,int mapId) {
        super(mapId);
        this.session = session;
        this.accountId = accountId;
    }

    @Override
    public String getName() {
        return "EnterMapCommand";
    }

    @Override
    public void active() {
        try {
            SpringContext.getScenceSerivce().doEnterMap(session, accountId, getMapId());
        }catch (Exception e){
            logger.error("进入地图失败");
            e.printStackTrace();
        }
    }
}
