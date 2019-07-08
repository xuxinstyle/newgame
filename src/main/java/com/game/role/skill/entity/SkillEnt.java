package com.game.role.skill.entity;

import com.db.AbstractEntity;
import com.game.SpringContext;
import com.game.role.skill.model.SkillInfo;
import com.game.role.player.entity.PlayerEnt;
import com.game.role.player.model.Player;
import com.game.util.JsonUtils;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 14:37
 */
@Entity(name = "skill")
@Table(appliesTo = "skill", comment = "技能栏信息")
public class SkillEnt extends AbstractEntity<Long> {
    /**
     * 角色唯一id
     */
    @Id
    @Column(columnDefinition = "bigint default 10000 comment '角色id'", nullable = false)
    private long playerId;

    @Lob
    @Column(columnDefinition = "blob comment '技能数据'")
    private byte[] skillData;

    @Transient
    private SkillInfo skillInfo;
    public static SkillEnt valueOf(long playerId){
        SkillEnt skillEnt = new SkillEnt();
        skillEnt.setPlayerId(playerId);
        PlayerEnt playerEnt = SpringContext.getPlayerSerivce().getPlayerEnt(playerId);
        Player player = playerEnt.getPlayer();
        skillEnt.setSkillInfo(SkillInfo.valueOf(player));
        return skillEnt;
    }

    @Override
    public void doSerialize() {
        skillData = JsonUtils.object2Bytes(skillInfo);
    }

    @Override
    public void doDeserialize() {
        skillInfo = JsonUtils.bytes2Object(skillData,SkillInfo.class);
    }

    @Override
    public Long getId() {
        return playerId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public byte[] getSkillData() {
        return skillData;
    }

    public void setSkillData(byte[] skillData) {
        this.skillData = skillData;
    }

    public SkillInfo getSkillInfo() {
        return skillInfo;
    }

    public void setSkillInfo(SkillInfo skillInfo) {
        this.skillInfo = skillInfo;
    }
}
