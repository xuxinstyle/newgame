package com.game.world.union.entity;

import com.db.AbstractEntity;
import com.game.util.JsonUtils;
import com.game.world.union.model.UnionMemberInfo;
import org.hibernate.annotations.Table;

import javax.persistence.*;

/**
 * @Author：xuxin
 * @Date: 2019/7/29 9:56
 */
@Entity(name = "account_union")
@Table(appliesTo = "account_union", comment = "工会成员信息")
public class UnionMemberEnt extends AbstractEntity<String> {
    @Id
    @Column(columnDefinition = "varchar(255) character set utf8 collate utf8_bin comment '账号id'", nullable = false)
    private String accountId;

    @Column(columnDefinition = "varchar(255) character set utf8 collate utf8_bin comment '工会id'")
    private String unionId;

    @Lob
    @Column(columnDefinition = "Blob comment '工会数据'", nullable = false)
    private byte[] unionMemberData;

    @Transient
    private UnionMemberInfo unionMemberInfo;

    public static UnionMemberEnt valueOf(String accountId) {
        UnionMemberEnt unionMemberEnt = new UnionMemberEnt();
        unionMemberEnt.setAccountId(accountId);
        unionMemberEnt.setUnionId(null);
        unionMemberEnt.setUnionMemberInfo(new UnionMemberInfo());
        return unionMemberEnt;
    }


    @Override
    public void doSerialize() {
        unionMemberData = JsonUtils.object2Bytes(unionMemberInfo);
    }

    @Override
    public void doDeserialize() {
        unionMemberInfo = JsonUtils.bytes2Object(unionMemberData, UnionMemberInfo.class);
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

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public byte[] getUnionMemberData() {
        return unionMemberData;
    }

    public void setUnionMemberData(byte[] unionMemberData) {
        this.unionMemberData = unionMemberData;
    }

    public UnionMemberInfo getUnionMemberInfo() {
        return unionMemberInfo;
    }

    public void setUnionMemberInfo(UnionMemberInfo unionMemberInfo) {
        this.unionMemberInfo = unionMemberInfo;
    }
}
