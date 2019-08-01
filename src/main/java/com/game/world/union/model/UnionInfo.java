package com.game.world.union.model;

import com.game.util.CommonUtil;
import com.game.util.TimeUtil;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.Transient;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author：xuxin
 * @Date: 2019/7/28 12:51
 */
public class UnionInfo {

    @JsonIgnore
    private Lock lock = new ReentrantLock();
    /**
     * 工会名称
     */
    private String unionName;
    /**
     * 会长id
     */
    private String presidentId;
    /**
     * 工会成员
     */
    private Map<String, UnionMemberInfo> memberInfoMap;

    /**
     * 申请入会列表
     */
    private Set<String> applyList;
    /**
     * 工会创建时间
     */
    private Long createTime;
    /**
     * 工会最大人数
     */
    private int maxNum;

    public static UnionInfo valueOf() {
        UnionInfo unionInfo = new UnionInfo();
        unionInfo.setApplyList(Collections.synchronizedSet(new HashSet()));
        unionInfo.setCreateTime(TimeUtil.now());
        unionInfo.setMemberInfoMap(new ConcurrentHashMap<>());
        unionInfo.setMaxNum(CommonUtil.MAX_MEMBER_NUM);

        return unionInfo;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    public Map<String, UnionMemberInfo> getMemberInfoMap() {
        return memberInfoMap;
    }

    public void setMemberInfoMap(Map<String, UnionMemberInfo> memberInfoMap) {
        this.memberInfoMap = memberInfoMap;
    }

    public String getUnionName() {
        return unionName;
    }

    public void setUnionName(String unionName) {
        this.unionName = unionName;
    }

    public String getPresidentId() {
        return presidentId;
    }

    public void setPresidentId(String presidentId) {
        this.presidentId = presidentId;
    }


    public Set<String> getApplyList() {
        return applyList;
    }

    public void setApplyList(Set<String> applyList) {
        this.applyList = applyList;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public int getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(int maxNum) {
        this.maxNum = maxNum;
    }
}
