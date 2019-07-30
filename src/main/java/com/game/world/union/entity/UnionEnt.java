package com.game.world.union.entity;

import com.db.AbstractEntity;
import com.game.util.JsonUtils;
import com.game.world.union.model.UnionInfo;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @Author：xuxin
 * @Date: 2019/7/28 12:47
 */
@Entity(name = "union_info")
@Table(appliesTo = "union_info", comment = "工会信息")
public class UnionEnt extends AbstractEntity<String> {
    /**
     * 工会id
     */
    @Id
    @Column(columnDefinition = "varchar(255) character set utf8 collate utf8_bin comment '工会Id'", nullable = false)
    private String unionId;

    @Lob
    @Column(columnDefinition = "Blob comment '工会数据'", nullable = false)
    private byte[] unionData;

    public static UnionEnt valueOf(String unionId) {
        UnionEnt unionEnt = new UnionEnt();
        unionEnt.setUnionId(unionId);
        unionEnt.setUnionInfo(UnionInfo.valueOf());
        return unionEnt;
    }

    @Transient
    private UnionInfo unionInfo;

    @Override
    public void doSerialize() {
        unionData = JsonUtils.object2Bytes(unionInfo);
    }

    @Override
    public void doDeserialize() {
        unionInfo = JsonUtils.bytes2Object(unionData, UnionInfo.class);
    }

    @Override
    public String getId() {
        return unionId;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public byte[] getUnionData() {
        return unionData;
    }

    public void setUnionData(byte[] unionData) {
        this.unionData = unionData;
    }

    public UnionInfo getUnionInfo() {
        return unionInfo;
    }

    public void setUnionInfo(UnionInfo unionInfo) {
        this.unionInfo = unionInfo;
    }
}
