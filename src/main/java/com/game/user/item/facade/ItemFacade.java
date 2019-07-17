package com.game.user.item.facade;

import com.game.SpringContext;
import com.game.user.item.packet.*;
import com.socket.core.session.TSession;
import com.socket.dispatcher.anno.HandlerAnno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/6/14 12:08
 */
@Component
public class ItemFacade {
    private static final Logger logger = LoggerFactory.getLogger(ItemFacade.class);
    @HandlerAnno
    public void addItemToPack(TSession session, CM_AwardToPack cm){
        try{
            SpringContext.getItemService().gmAwardToPack(cm.getAccountId(), cm.getItemModelId(), cm.getNum());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("玩家{}添加道具{}失败",cm.getAccountId(), cm.getItemModelId());
        }
    }
    @HandlerAnno
    public void removeItem(TSession session, CM_RemoveItemFormPack cm){
        try{
            SpringContext.getItemService().removeItem(session,cm.getAccountId(),cm.getObjectId(), cm.getNum());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("玩家{}移除道具{}失败",cm.getAccountId(), cm.getAccountId(), cm.getObjectId());
        }
    }
    @HandlerAnno
    public void showItem(TSession session,CM_ShowPackItem cm){
        try{
            SpringContext.getItemService().showItem(session,cm.getAccountId());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查看玩家{}背包信息失败",cm.getAccountId());
        }
    }

    @HandlerAnno
    public void useItem(TSession session, CM_UseItem cm){
        try{
            SpringContext.getItemService().useItem(session, cm.getItemObjectId(),cm.getNum());
        }catch (Exception e){
            logger.error("玩家{}使用道具{}失败",session.getAccountId(),cm.getItemObjectId());
            e.printStackTrace();
        }
    }
    @HandlerAnno
    public void showItemInfo(TSession session, CM_ShowItemInfo cm){
        try{
            SpringContext.getItemService().showItemInfo(session, cm.getItemObjectId());
        }catch (Exception e){
            logger.error("玩家{}查看道具{}信息失败",session.getAccountId(),cm.getItemObjectId());
            e.printStackTrace();
        }
    }


}
