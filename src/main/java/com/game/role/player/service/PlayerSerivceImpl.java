package com.game.role.player.service;

import com.db.HibernateDao;
import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.container.AbstractAttributeContainer;
import com.game.base.attribute.constant.AttributeKind;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.player.packet.SM_ShowAttribute;
import com.game.role.player.resource.PlayerLevelResource;
import com.game.user.account.entity.AccountEnt;
import com.game.user.account.model.AccountInfo;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.role.equip.entity.EquipmentEnt;
import com.game.role.equip.model.EquipmentInfo;
import com.resource.core.StorageManager;
import com.socket.core.session.TSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/6/3 11:36
 */
@Component
public class PlayerSerivceImpl implements PlayerService {
    private static Logger logger = LoggerFactory.getLogger(PlayerSerivceImpl.class);
    @Autowired
    private StorageManager storageManager;
    @Autowired
    private HibernateDao hibernateDao;
    @Override
    public void save(PlayerEnt playerEnt) {
        hibernateDao.update(PlayerEnt.class, playerEnt);
    }

    @Override
    public PlayerEnt getPlayerEnt(long playerId) {
        PlayerEnt playerEnt = hibernateDao.find(PlayerEnt.class, playerId);
        return playerEnt;
    }

    @Override
    public void insert(PlayerEnt playerEnt) {
        hibernateDao.save(PlayerEnt.class, playerEnt);
    }

    @Override
    public PlayerEnt createPlayer(String accountId, int type, String nickName) {
        /**
         * 生成角色
         */
        PlayerEnt playerEnt = new PlayerEnt();
        long playerId = SpringContext.getIdentifyService().getNextIdentify(ObjectType.PLAYER);
        playerEnt.setPlayerId(playerId);
        playerEnt.setAccountId(accountId);
        Player player = Player.valueOf(playerId, accountId, type,nickName);

        playerEnt.setPlayer(player);
        insert(playerEnt);
        /**
         * 生成装备栏
         */
        EquipmentEnt equipmentEnt = new EquipmentEnt();
        equipmentEnt.setPlayerId(playerId);
        equipmentEnt.setEquipmentInfo(EquipmentInfo.valueOf());
        SpringContext.getEquipService().save(equipmentEnt);

        return playerEnt;
    }
    @Override
    public PlayerLevelResource getPlayerLevelResource(Object id){
        return storageManager.getResource(id, PlayerLevelResource.class);
    }
    @Override
    public Player getPlayer(String accountId){
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        AccountInfo accountInfo = accountEnt.getAccountInfo();
        long playerId = accountInfo.getPlayerId();
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        if(playerEnt==null){
            return null;
        }
        return playerEnt.getPlayer();
    }

    @Override
    public PlayerEnt getPlayerEnt(String accountId) {
        AccountEnt accountEnt = SpringContext.getAccountService().getAccountEnt(accountId);
        long playerId = accountEnt.getAccountInfo().getPlayerId();
        return SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
    }

    @Override
    public void showPlayerAttribute(TSession session, String accountId){
        Player player = SpringContext.getPlayerSerivce().getPlayer(accountId);
        AbstractAttributeContainer attributeContainer = player.getAttributeContainer();

        Map<AttributeType, Attribute> finalAttributes = attributeContainer.getFinalAttributes();
        List<Attribute> firstList = new ArrayList<>();
        List<Attribute> secondList = new ArrayList<>();
        List<Attribute> otherList = new ArrayList<>();
        for(Attribute attribute:finalAttributes.values()){
            if(attribute.getAttributeType().getKind()==AttributeKind.FIRST_ATTRIBUTE){
                firstList.add(attribute);
            }else if(attribute.getAttributeType().getKind()==AttributeKind.SECOND_ATTRIBUTE){
               secondList.add(attribute);
            }else {
                otherList.add(attribute);
            }
        }
        SM_ShowAttribute sm = new SM_ShowAttribute();
        sm.setFirstAttributeList(firstList);
        sm.setSecondAttributeList(secondList);
        sm.setOtherAttributeList(otherList);
        sm.setPlayerName(player.getPlayerName());
        sm.setPlayerLevel(player.getLevel());
        session.sendPacket(sm);
    }


}
