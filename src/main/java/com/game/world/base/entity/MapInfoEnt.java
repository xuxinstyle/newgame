package com.game.world.base.entity;

import com.db.AbstractEntity;
import com.game.util.JsonUtils;
import com.game.world.base.model.MapInfo;

import javax.persistence.*;

/**
 * @Author：xuxin
 * @Date: 2019/7/22 10:19
 */
@Entity(name = "map_info")
@org.hibernate.annotations.Table(appliesTo = "map_info", comment = "地图信息")
public class MapInfoEnt extends AbstractEntity<String> {
    /**
     * 账号Id
     */
    @Id
    @Column(columnDefinition = "varchar(255) character set utf8 collate utf8_bin comment '账号Id'", nullable = false)
    private String accountId;


    @Lob
    @Column(columnDefinition = "MediumBlob comment '玩家副本数据'", nullable = false)
    private volatile byte[] mapInfoData;

    @Transient
    private MapInfo mapInfo;

    public static MapInfoEnt valueOf(String id) {
        MapInfoEnt mapInfoEnt = new MapInfoEnt();
        mapInfoEnt.setAccountId(id);
        mapInfoEnt.setMapInfo(MapInfo.valueOf());
        return mapInfoEnt;
    }

    @Override
    public void doSerialize() {
        mapInfoData = JsonUtils.object2Bytes(mapInfo);
    }

    @Override
    public void doDeserialize() {
        mapInfo = JsonUtils.bytes2Object(mapInfoData, MapInfo.class);
    }

    @Override
    public String getId() {
        return accountId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public byte[] getMapInfoData() {
        return mapInfoData;
    }

    public void setMapInfoData(byte[] mapInfoData) {
        this.mapInfoData = mapInfoData;
    }

    public MapInfo getMapInfo() {
        return mapInfo;
    }

    public void setMapInfo(MapInfo mapInfo) {
        this.mapInfo = mapInfo;
    }
}
