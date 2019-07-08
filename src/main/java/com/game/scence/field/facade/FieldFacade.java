package com.game.scence.field.facade;

import com.game.SpringContext;
import com.game.scence.field.packet.CM_ShowMonsterInfo;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/4 9:52
 */
@Component
public class FieldFacade {
    private static final Logger logger = LoggerFactory.getLogger(FieldFacade.class);

    @HandlerAnno
    public void showMonster(TSession session, CM_ShowMonsterInfo cm){
        try{
            SpringContext.getFieldService().showMonsterInfo(session.getAccountId(),cm.getMapId(),cm.getMonsterObjectId());
        }catch (Exception e){
            logger.error("查看地图[{}]中的怪物[{}]信息失败",cm.getMapId(),cm.getMonsterObjectId(),e);

        }
    }
}
