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

    public static UnionMemberEnt valueOf(String accountId) {
        UnionMemberEnt unionMemberEnt = new UnionMemberEnt();
        unionMemberEnt.setAccountId(accountId);
        unionMemberEnt.setUnionId(null);

        return unionMemberEnt;
    }


    @Override
    public void doSerialize() {

    }

    @Override
    public void doDeserialize() {

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

}
