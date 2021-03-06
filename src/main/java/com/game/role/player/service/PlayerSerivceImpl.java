package com.game.role.player.service;

import com.db.cache.EntityCacheService;
import com.game.SpringContext;
import com.game.base.attribute.Attribute;
import com.game.base.attribute.container.AbstractAttributeContainer;
import com.game.base.attribute.constant.AttributeKind;
import com.game.base.attribute.constant.AttributeType;
import com.game.base.gameobject.constant.ObjectType;
import com.game.role.skill.entity.SkillEnt;
import com.game.role.skill.model.SkillInfo;
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
    private EntityCacheService<Long, PlayerEnt> entityCacheService;
    @Override
    public void save(PlayerEnt playerEnt) {
        entityCacheService.saveOrUpdate(playerEnt);
    }
    @Override
    public void save(Player player){
        PlayerEnt playerEnt = entityCacheService.find(PlayerEnt.class, player.getObjectId());
        playerEnt.setPlayer(player);
        save(playerEnt);
    }
    @Override
    public PlayerEnt getPlayerEnt(long playerId) {
        return entityCacheService.find(PlayerEnt.class, playerId);
    }

    @Override
    public void insert(PlayerEnt playerEnt) {
        entityCacheService.saveOrUpdate(playerEnt);
    }

    @Override
    public PlayerEnt createPlayer(String accountId, int job, String nickName) {
        /**
         * 生成角色
         */
        PlayerEnt playerEnt = new PlayerEnt();
        long playerId = SpringContext.getIdentifyService().getNextIdentify(ObjectType.PLAYER);
        playerEnt.setPlayerId(playerId);
        playerEnt.setAccountId(accountId);

        Player player = Player.valueOf(playerId, accountId, job, nickName);
        /**
         * 生成技能栏
         */
        SkillEnt skillEnt = new SkillEnt();
        skillEnt.setPlayerId(playerId);
        skillEnt.setSkillInfo(SkillInfo.valueOf(player));
        SpringContext.getSkillService().saveSkill(skillEnt);
        /**
         * 生成装备栏
         */
        EquipmentEnt equipmentEnt = new EquipmentEnt();
        equipmentEnt.setPlayerId(playerId);
        equipmentEnt.setEquipmentInfo(EquipmentInfo.valueOf());
        SpringContext.getEquipService().save(equipmentEnt);

        playerEnt.setPlayer(player);
        insert(playerEnt);
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
            if(attribute.getType().getKind()==AttributeKind.FIRST_ATTRIBUTE){
                firstList.add(attribute);
            }else if(attribute.getType().getKind()==AttributeKind.SECOND_ATTRIBUTE){
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

    @Override
    public List<PlayerEnt> getPlayerAll() {
        return entityCacheService.findAll(PlayerEnt.class);
    }

}
