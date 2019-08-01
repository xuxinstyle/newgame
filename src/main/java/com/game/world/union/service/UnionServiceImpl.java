package com.game.world.union.service;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.common.exception.RequestException;
import com.game.util.I18nId;
import com.game.util.SendPacketUtil;
import com.game.util.TimeUtil;
import com.game.world.union.command.AgreeApplyCommand;
import com.game.world.union.command.KickCommand;
import com.game.world.union.constant.UnionJob;
import com.game.world.union.entity.UnionEnt;
import com.game.world.union.entity.UnionAccountEnt;
import com.game.world.union.model.UnionInfo;
import com.game.world.union.model.UnionMemberInfo;
import com.game.world.union.packet.*;
import com.game.world.union.packet.bean.UnionMemberVO;
import com.game.world.union.packet.bean.UnionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;

/**
 * @Author：xuxin
 * @Date: 2019/7/29 9:09
 */
@Component
public class UnionServiceImpl implements UnionService {

    private static final Logger logger = LoggerFactory.getLogger(UnionServiceImpl.class);

    @Autowired
    private UnionManager unionManager;

    /**
     * 创建工会
     *
     * @param accountId
     * @param unionName
     */
    @Override
    public void createUnion(String accountId, String unionName) {
        // 判断玩家是否加入工会
        UnionAccountEnt unionAccountEnt = unionManager.getUnionMember(accountId);
        if (unionAccountEnt == null || unionAccountEnt.getUnionId() != null) {
            RequestException.throwException(I18nId.NOT_CREATE_UNION);
        }

        // 生成行会id
        long nextUnionId = SpringContext.getIdentifyService().getNextIdentify(ObjectType.UNION);
        String unionId = SpringContext.getServerConfigValue().getServerId() + "_" + nextUnionId;
        // 创建行会
        UnionEnt unionEnt = UnionEnt.valueOf(unionId);
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        // 设置默认的会长id 添加自己为成员
        unionInfo.setUnionName(unionName);
        unionInfo.setPresidentId(accountId);

        // 修改unionMember信息
        unionAccountEnt.setUnionId(unionId);
        UnionMemberInfo unionMemberInfo = new UnionMemberInfo();
        unionMemberInfo.setEnterTime(TimeUtil.now());
        unionMemberInfo.setUnionJob(UnionJob.PRESIDENT);
        Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
        memberInfoMap.put(accountId, unionMemberInfo);
        logger.info("玩家[{}]创建公会[{}]成功", accountId, unionId);
        // 保存
        unionManager.saveUnion(unionEnt);
        unionManager.saveUnionMember(unionAccountEnt);
        // 通知客户端
        SendPacketUtil.send(accountId, new SM_CreateUnionSucc());
    }

    /**
     * 申请加入行会
     *
     * @param accountId
     * @param unionId
     */
    @Override
    public void applyJoinUnion(String accountId, String unionId) {
        // 检查行会是否存在
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        if (unionEnt == null) {
            RequestException.throwException(I18nId.UNION_NOT_EXIST);
        }
        // fixme:这里的申请列表没有上限所以没有加锁
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        if (unionInfo == null) {
            RequestException.throwException(I18nId.UNION_NOT_EXIST);
        }
        // 判断玩家是否加入工会
        UnionAccountEnt unionAccountEnt = unionManager.getUnionMember(accountId);
        if (unionAccountEnt == null || unionAccountEnt.getUnionId() != null) {
            RequestException.throwException(I18nId.NOT_APPLY_UNION);
        }
        // 有可能在这里的时候行会被解散了 但是对象还在 影响不大所以不加锁
        // 添加到申请列表
        unionInfo.getApplyList().add(accountId);
        logger.info("玩家[{}]申请进入行会[{}]", accountId, unionId);
        // 保存
        unionManager.saveUnion(unionEnt);
        SendPacketUtil.send(accountId, new SM_ApplyJoinUnionSucc());
    }

    @Override
    public void showApplyList(String accountId) {

        // 检查玩家是否加入行会
        UnionAccountEnt unionAccountEnt = unionManager.getUnionMember(accountId);
        if (unionAccountEnt.getUnionId() == null) {
            // 没有加入行会
            logger.info("玩家[{}]没有加入行会,无法查看的申请列表", accountId);
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        // 检查玩家在行会中的权限
        String unionId = unionAccountEnt.getUnionId();
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        // 检查玩家的工会是否存在
        if (unionEnt == null) {
            RequestException.throwException(I18nId.UNION_NOT_EXIST);
        }
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        if (unionInfo == null) {
            RequestException.throwException(I18nId.UNION_NOT_EXIST);
        }

        Set<String> applyList = unionInfo.getApplyList();
        // 通知客户端
        SM_ShowApplyList sm = new SM_ShowApplyList();
        sm.setApplyList(applyList);
        SendPacketUtil.send(accountId, sm);

    }

    /**
     * 验证自己是否在行会中
     *
     * @param unionId
     * @return
     */
    public UnionEnt getUnionAndThrow(String unionId) {
        // 验证自己是否在行会中

        if (unionId == null) {
            // 没有加入行会
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        if (unionEnt == null) {
            RequestException.throwException(I18nId.UNION_NOT_EXIST);
        }
        return unionEnt;
    }
    @Override
    public void agreeApply(String accountId, String targetAccountId) {
        // double check 防止进入到
        // 验证自己是否在行会中
        UnionAccountEnt unionAccountEnt = unionManager.getUnionMember(accountId);
        String unionId = unionAccountEnt.getUnionId();
        // 获取行会并验证行会是否为空
        UnionEnt unionEnt = getUnionAndThrow(unionId);
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        // 验证自己的行会权限 需要二次验证
        UnionMemberInfo unionMemberInfo = unionInfo.getMemberInfoMap().get(accountId);
        UnionJob unionJob = unionMemberInfo.getUnionJob();
        if (unionJob == null) {
            return;
        }
        if (unionJob.getPermission() <= UnionJob.VICE_PRESIDENT.getPermission()) {
            // 没有权限 需要二次验证
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }
        // 玩家不在申请列表中  需要二次验证
        if (!unionInfo.getApplyList().contains(targetAccountId)) {
            RequestException.throwException(I18nId.NOT_APPLY);
        }
        // 验证目标账号是否加入其他工会 需要二次验证
        // 有可能在在该方法中 玩家进入其他行会 所以在command中需要再验证一次
        // 这里本应是在目标线程中验证
        // 再这里验证是为了提高效率 为了避免不必要的加入操作  因为加入总是要一个个来 如果通过了太多的不必要的操作会总是阻塞性能降低
        UnionAccountEnt targetUnionAccountEnt = unionManager.getUnionMember(targetAccountId);
        String targetUnionId = targetUnionAccountEnt.getUnionId();
        if (targetUnionId != null) {
            RequestException.throwException(I18nId.JOIN_OTHER_UNION);
        }
        // 验证行会人数是否已经满了 需要二次验证
        if (unionInfo.getMemberInfoMap().size() >= unionInfo.getMaxNum()) {
            // 工会人数已满
            RequestException.throwException(I18nId.UNION_NUM_LIMIT);
        }

        // 抛到对应的玩家线程去同意 防止多个工会同时同意他加入行会
        AgreeApplyCommand command = AgreeApplyCommand.valueOf(accountId, targetAccountId, unionId);
        SpringContext.getAccountExecutorService().submit(command);

    }

    @Override
    public void doAgreeApply(String handleAccountId, String unionId, String targetAccountId) {
        // 二次验证 防止行会被解散
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        if (unionEnt == null) {
            return;
        }
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        Lock lock = unionInfo.getLock();
        // 对工会对象加锁 因为下列代码修改了申请列表和工会成员列表
        try {
            lock.lock();
            unionEnt = unionManager.getUnionEnt(unionId);
            if (unionEnt == null) {
                return;
            }
            unionInfo = unionEnt.getUnionInfo();
            Set<String> applyList = unionInfo.getApplyList();
            // 验证玩家是否在申请列表中
            if (!applyList.contains(targetAccountId)) {
                // 这里应该通知操作者 所以这里不能用RequestException
                SendPacketUtil.send(handleAccountId, SM_AgreeApply.valueOf(1));
                return;
            }
            // 验证目标账号是否加入其他工会
            UnionAccountEnt targetUnionAccountEnt = unionManager.getUnionMember(targetAccountId);
            if (targetUnionAccountEnt.getUnionId() != null) {
                // 加入了其他工会 移除申请列表
                applyList.remove(targetAccountId);
                SendPacketUtil.send(handleAccountId, SM_AgreeApply.valueOf(2));
                return;
            }
            Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
            // 验证行会人数是否已经满了 二次验证
            if (memberInfoMap.size() >= unionInfo.getMaxNum()) {
                // 工会人数已满
                SendPacketUtil.send(handleAccountId, SM_AgreeApply.valueOf(3));
                return;
            }

            UnionMemberInfo handleUnionMemberInfo = memberInfoMap.get(handleAccountId);
            if (handleUnionMemberInfo == null) {
                // 你不在行会
                SendPacketUtil.send(handleAccountId, SM_AgreeApply.valueOf(4));
                return;
            }
            // 验证操作者权限
            if (handleUnionMemberInfo.getUnionJob() == null) {
                SendPacketUtil.send(handleAccountId, SM_AgreeApply.valueOf(5));
                return;
            }
            if (handleUnionMemberInfo.getUnionJob().getPermission() <= UnionJob.VICE_PRESIDENT.getPermission()) {
                SendPacketUtil.send(handleAccountId, SM_AgreeApply.valueOf(5));
                return;
            }

            // 将目标加入
            // 修改accountIdUnion
            targetUnionAccountEnt.setUnionId(unionId);
            UnionMemberInfo targetUnionMemberInfo = new UnionMemberInfo();
            targetUnionMemberInfo.setUnionJob(UnionJob.MEMBER);
            targetUnionMemberInfo.setEnterTime(TimeUtil.now());
            memberInfoMap.put(targetAccountId, targetUnionMemberInfo);
            // 从申请列表移除
            applyList.remove(targetAccountId);
            // 保存 通知客户端
            unionManager.saveUnionMember(targetUnionAccountEnt);
            unionManager.saveUnion(unionEnt);
            SendPacketUtil.send(targetAccountId, SM_AgreeApply.valueOf(0));
        } finally {
            lock.unlock();
        }
    }

    /**
     * 可能被其他人修改权限 可能行会被解散（不需要二次验证） 可能被踢出行会（踢人需要在对方线程 所以也不需要二次验证）
     *
     * @param handleAccounrId 操作者
     * @param targetAccountId 申请者
     */
    @Override
    public void refuse(String handleAccounrId, String targetAccountId) {
        // 这里应该和同意是一样的 需要抛到对方线程去 因为玩家的权限有可能会被改变 或者被踢出工会
        // 验证操作者
        UnionAccountEnt unionAccountEnt = unionManager.getUnionMember(handleAccounrId);
        String unionId = unionAccountEnt.getUnionId();
        // 验证自己是否在行会中
        UnionEnt unionEnt = getUnionAndThrow(unionId);
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
        UnionMemberInfo unionMemberInfo = memberInfoMap.get(handleAccounrId);
        // 验证操作者权限
        UnionJob unionJob = unionMemberInfo.getUnionJob();
        if (unionJob == null) {
            return;
        }
        if (unionJob.getPermission() < UnionJob.VICE_PRESIDENT.getPermission()) {
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }
        Lock lock = unionInfo.getLock();
        try {
            lock.lock();
            // 验证操作者
            unionInfo = unionEnt.getUnionInfo();
            memberInfoMap = unionInfo.getMemberInfoMap();
            unionMemberInfo = memberInfoMap.get(handleAccounrId);
            if (unionMemberInfo == null) {
                RequestException.throwException(I18nId.NOT_JOIN_UNION);
            }
            // 验证操作者权限
            unionJob = unionMemberInfo.getUnionJob();
            if (unionJob == null) {
                return;
            }
            if (unionJob.getPermission() < UnionJob.VICE_PRESIDENT.getPermission()) {
                RequestException.throwException(I18nId.NOT_PERMISSION);
            }
            // 移除目标
            unionInfo.getApplyList().remove(targetAccountId);
            // 保存 通知客户端
            unionManager.saveUnion(unionEnt);
            SendPacketUtil.send(targetAccountId, SM_RefuseApply.valueOf(1));
            SendPacketUtil.send(handleAccounrId, SM_RefuseApply.valueOf(2));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void showMySelfUnion(String accountId) {
        UnionAccountEnt unionMember = unionManager.getUnionMember(accountId);
        String unionId = unionMember.getUnionId();
        if (unionId == null) {
            // 没有加入工会
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        showUnionMembers(accountId, unionId);
    }

    @Override
    public void showUnionMembers(String accountId, String unionId) {
        // 验证工会是否存在
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        if (unionEnt == null) {
            RequestException.throwException(I18nId.UNION_NOT_EXIST);
        }
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        UnionAccountEnt unionAccountEnt = unionManager.getUnionMember(accountId);
        Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
        List<UnionMemberVO> unionMemberVOList = new ArrayList<>();

        for (String memberAccountId : memberInfoMap.keySet()) {
            UnionMemberInfo unionMemberInfo = memberInfoMap.get(memberAccountId);

            if (unionAccountEnt.getUnionId() == null || !unionId.equals(unionAccountEnt.getUnionId()) || unionMemberInfo.getUnionJob() == null) {
                logger.warn("成员：[{}]不在工会[{}]成员列表中", memberAccountId, unionId);
                continue;
            }
            UnionMemberVO unionMemberVO = new UnionMemberVO();
            int permission = unionMemberInfo.getUnionJob().getPermission();
            unionMemberVO.setAccountId(memberAccountId);
            unionMemberVO.setPermission(permission);
            unionMemberVO.setEnterTime(unionMemberInfo.getEnterTime());
            unionMemberVOList.add(unionMemberVO);
        }
        UnionVO unionVO = new UnionVO();
        unionVO.setUnionName(unionInfo.getUnionName());
        unionVO.setMaxNum(unionInfo.getMaxNum());
        unionVO.setCurrNum(memberInfoMap.size());
        unionVO.setPresidentId(unionInfo.getPresidentId());
        unionVO.setUnionId(unionId);
        SM_ShowUnionMembers sm = new SM_ShowUnionMembers();
        sm.setUnionVO(unionVO);
        sm.setUnionMemberVOList(unionMemberVOList);
        SendPacketUtil.send(accountId, sm);

    }

    /**
     * 查看行会列表
     *
     * @param accountId
     */
    @Override
    public void showUnionList(String accountId) {


        List<UnionEnt> unionList = unionManager.getUnionList();
        List<UnionVO> unionVOList = new ArrayList<>();
        for (UnionEnt unionEnt : unionList) {
            UnionInfo unionInfo = unionEnt.getUnionInfo();
            Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
            UnionVO unionVO = new UnionVO();
            unionVO.setUnionId(unionEnt.getUnionId());
            unionVO.setPresidentId(unionInfo.getPresidentId());
            unionVO.setCurrNum(memberInfoMap.size());
            unionVO.setMaxNum(unionInfo.getMaxNum());
            unionVO.setUnionName(unionInfo.getUnionName());
            unionVOList.add(unionVO);
        }
        SM_ShowUnionList sm = new SM_ShowUnionList();
        sm.setUnionVOList(unionVOList);
        SendPacketUtil.send(accountId, sm);

    }

    /**
     * 退出当前工会
     * 因为踢人也是在我的线程踢 所以不需要加锁
     * 如果解散行会 的时候退出就会报错
     * @param accountId
     */
    @Override
    public void exitUnion(String accountId) {
        // 验证是否加入工会
        UnionAccountEnt unionAccountEnt = unionManager.getUnionMember(accountId);
        String unionId = unionAccountEnt.getUnionId();
        // 获取union 并验证行会
        UnionEnt unionEnt = getUnionAndThrow(unionId);
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
        UnionMemberInfo unionMemberInfo = memberInfoMap.get(accountId);
        if (unionMemberInfo == null) {
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        UnionJob unionJob = unionMemberInfo.getUnionJob();
        // 验证权限 是工会会长的玩家需要先移交会长位置
        if (unionJob.getPermission() >= UnionJob.PRESIDENT.getPermission()) {
            RequestException.throwException(I18nId.NOT_EXIT_UNION);
        }
        // 这里上锁是因为 有可能你可能被踢出行会 或者可能被修改了职位
        Lock lock = unionInfo.getLock();
        try {
            lock.lock();
            unionJob = unionMemberInfo.getUnionJob();
            // 验证权限 是工会会长的玩家需要先移交会长位置
            if (unionJob.getPermission() >= UnionJob.PRESIDENT.getPermission() || memberInfoMap.size() == 0) {
                RequestException.throwException(I18nId.NOT_EXIT_UNION);
            }
            memberInfoMap.remove(accountId);
            // 移除成功 修改自己的工会信息
            unionAccountEnt.setUnionId(null);
            // 保存 通知客户端
            unionManager.saveUnionMember(unionAccountEnt);
            unionManager.saveUnion(unionEnt);
            SendPacketUtil.send(accountId, new SM_ExitUnion());
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void kickOther(String accountId, String targetAccountId) {
        UnionAccountEnt unionMember = unionManager.getUnionMember(accountId);
        UnionAccountEnt targetUnionMember = unionManager.getUnionMember(targetAccountId);
        String targetUnionId = targetUnionMember.getUnionId();
        String unionId = unionMember.getUnionId();
        // 验证自己是否在工会中
        UnionEnt unionEnt = getUnionAndThrow(unionId);
        // 验证目标是否有工会
        if (targetUnionId == null) {
            // 目标不在工会中
            RequestException.throwException(I18nId.TARGET_NOT_JOIN_UNION);
        }
        // 验证操作者是否在目标工会 需要二次验证 防止在进入command后其他人也移除了他 让他不在工会
        if (!targetUnionId.equals(unionId)) {
            // 无法操作其他工会的成员
            RequestException.throwException(I18nId.NOT_HANDLE);
        }
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
        UnionMemberInfo targetUnionMemberInfo = memberInfoMap.get(targetAccountId);
        UnionMemberInfo unionMemberInfo = memberInfoMap.get(accountId);
        if (unionMemberInfo == null || unionMemberInfo.getUnionJob() == null) {
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        if (targetUnionMemberInfo == null || targetUnionMemberInfo.getUnionJob() == null) {
            RequestException.throwException(I18nId.TARGET_NOT_JOIN_UNION);
        }
        // 验证权限
        int permission = unionMemberInfo.getUnionJob().getPermission();
        int targetPermission = targetUnionMemberInfo.getUnionJob().getPermission();
        if (permission <= targetPermission) {
            // 没有权限
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }
        // 抛到对方线程去踢他
        KickCommand command = KickCommand.valueOf(accountId, unionId, targetAccountId);
        SpringContext.getAccountExecutorService().submit(command);

    }

    @Override
    public void doKickOther(String handleAccountId, String unionId, String targetAccountId) {
        UnionAccountEnt targetUnionAccountEnt = unionManager.getUnionMember(targetAccountId);
        String targetUnionId = targetUnionAccountEnt.getUnionId();
        if (targetAccountId == null) {
            SendPacketUtil.send(handleAccountId, SM_KickOther.valueOf(2));
            return;
        }
        UnionAccountEnt unionMember = unionManager.getUnionMember(handleAccountId);
        String handleUnionId = unionMember.getUnionId();
        if (!handleUnionId.equals(unionId) || !targetUnionId.equals(unionId)) {
            SendPacketUtil.send(handleAccountId, SM_KickOther.valueOf(4));
            return;
        }
        // 二次验证目标是否在工会中
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        Lock lock = unionInfo.getLock();
        try {
            lock.lock();
            // 验证自己的行会和目标的行会
            if (unionId == null || !unionId.equals(targetUnionId)) {
                SendPacketUtil.send(handleAccountId, SM_KickOther.valueOf(2));
                return;
            }
            Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
            UnionMemberInfo targetUnionMemberInfo = memberInfoMap.get(targetAccountId);
            UnionMemberInfo handleunionMemberInfo = memberInfoMap.get(handleAccountId);

            // 验证目标和操作者本身
            if (targetUnionMemberInfo == null || handleunionMemberInfo == null) {
                SendPacketUtil.send(handleAccountId, SM_KickOther.valueOf(2));
                return;
            }
            // 有可能在之前被别人降了权限或者目标被升了权限 验证权限
            int permission = handleunionMemberInfo.getUnionJob().getPermission();
            int targetPermission = targetUnionMemberInfo.getUnionJob().getPermission();
            if (permission <= targetPermission) {
                // 没有权限
                SendPacketUtil.send(handleAccountId, SM_KickOther.valueOf(4));
                return;
            }
            // 从工会成员列表中 移除目标
            memberInfoMap.remove(targetAccountId);
            // 修改目标的数据
            targetUnionAccountEnt.setUnionId(null);
            // 保存
            unionManager.saveUnion(unionEnt);
            unionManager.saveUnionMember(targetUnionAccountEnt);
            // 通知目标和操作者
            SendPacketUtil.send(handleAccountId, SM_KickOther.valueOf(1));
            SendPacketUtil.send(targetAccountId, SM_KickOther.valueOf(3));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void updatePermission(String accountId, String targetAccountId, int permission) {
        if (accountId.equals(targetAccountId)) {
            RequestException.throwException(I18nId.NOT_UPDATE_MYSELF);
        }
        // 验证权限是否合法
        UnionJob unionJob = UnionJob.getUnionJob(permission);
        if (unionJob == null) {
            RequestException.throwException(I18nId.PERMISSION_ERROR);
        }
        // 验证自己和目标是否在工会中 double Check
        UnionAccountEnt unionAccount = unionManager.getUnionMember(accountId);
        UnionAccountEnt targetUnionAccount = unionManager.getUnionMember(targetAccountId);
        String unionId = unionAccount.getUnionId();
        String targetUnionId = targetUnionAccount.getUnionId();
        if (unionAccount.getUnionId() == null || targetUnionAccount.getUnionId() == null ||
                !unionId.equals(targetUnionId)) {
            logger.warn("操作者工会[{}]和目标工会[{}]不一致，无法修改权限", unionAccount.getUnionId(), targetUnionAccount.getUnionId());
            RequestException.throwException(I18nId.TARGET_NOT_JOIN_UNION);
        }

        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        if (unionEnt == null) {
            RequestException.throwException(I18nId.UNION_NOT_EXIST);
        }
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
        UnionMemberInfo unionMemberInfo = memberInfoMap.get(accountId);
        UnionMemberInfo targetUnionMemberInfo = memberInfoMap.get(targetAccountId);
        UnionJob handleUnionJob = unionMemberInfo.getUnionJob();
        UnionJob targetUnionJob = targetUnionMemberInfo.getUnionJob();
        // 与自己权限相同的人无法修改权限 且自己不能修改自己的权限
        if (handleUnionJob == null || targetUnionJob == null || handleUnionJob.getPermission() <= targetUnionJob.getPermission()) {
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }
        // 验证权限 需要二次验证 验证目标权限
        if (unionMemberInfo.getUnionJob().getPermission() <= permission) {
            logger.warn("玩家[{}-{}]修改玩家[{}-{}]权限至[{}]失败", accountId, unionMemberInfo.getUnionJob().getPermission(),
                    targetAccountId, targetUnionMemberInfo.getUnionJob().getPermission(), permission);
            // 权限不足
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }

        Lock lock = unionInfo.getLock();
        try {
            lock.lock();
            // 防止玩家退出了行会
            unionAccount = unionManager.getUnionMember(accountId);
            targetUnionAccount = unionManager.getUnionMember(targetAccountId);
            unionId = unionAccount.getUnionId();
            targetUnionId = targetUnionAccount.getUnionId();
            if (unionAccount.getUnionId() == null || targetUnionAccount.getUnionId() == null ||
                    !unionId.equals(targetUnionId)) {
                logger.warn("操作者工会[{}]和目标工会[{}]不一致，无法修改权限", unionAccount.getUnionId(), targetUnionAccount.getUnionId());
                SendPacketUtil.send(accountId, SM_UpdatePermission.valueOf(2));
                return;
            }

            targetUnionMemberInfo = memberInfoMap.get(targetAccountId);
            if (targetUnionMemberInfo == null) {
                // 目标不在工会中
                RequestException.throwException(I18nId.TARGET_NOT_JOIN_UNION);
            }
            handleUnionJob = unionMemberInfo.getUnionJob();
            targetUnionJob = targetUnionMemberInfo.getUnionJob();
            if (handleUnionJob == null || targetUnionJob == null || handleUnionJob.getPermission()
                    <= targetUnionJob.getPermission()) {
                RequestException.throwException(I18nId.NOT_PERMISSION);
            }
            // 验证权限 二次验证
            if (unionMemberInfo.getUnionJob().getPermission() <= permission) {
                logger.warn("玩家[{}-{}]修改玩家[{}-{}]权限至[{}]失败", accountId, unionMemberInfo.getUnionJob().getPermission(),
                        targetAccountId, targetUnionMemberInfo.getUnionJob().getPermission(), permission);
                // 权限不足
                SendPacketUtil.send(accountId, SM_UpdatePermission.valueOf(2));
                return;
            }
            // 修改
            targetUnionMemberInfo.setUnionJob(unionJob);
            // 保存 通知客户端
            unionManager.saveUnionMember(targetUnionAccount);
            SendPacketUtil.send(accountId, SM_UpdatePermission.valueOf(1));
        } finally {
            lock.unlock();
        }

    }


    @Override
    public void appoinCaptain(String accountId, String targetAccountId) {
        // 验证自己是否在工会中 权限是否足够
        UnionAccountEnt unionMember = unionManager.getUnionMember(accountId);
        String unionId = unionMember.getUnionId();

        // 验证目标是否在工会中 需要二次验证 可能被踢走

        UnionAccountEnt targetUnionMember = unionManager.getUnionMember(targetAccountId);
        String targetUnionId = targetUnionMember.getUnionId();
        if (targetUnionId == null || !targetUnionId.equals(unionId)) {
            RequestException.throwException(I18nId.TARGET_NOT_JOIN_UNION);
        }
        // 验证自己是否在工会中
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        if (unionEnt == null) {
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
        if (memberInfoMap == null) {
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        // 验证自己的权限
        UnionMemberInfo unionMemberInfo = memberInfoMap.get(accountId);
        if (unionMemberInfo.getUnionJob().getPermission() < UnionJob.PRESIDENT.getPermission()) {
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }

        Lock lock = unionInfo.getLock();
        try {
            lock.lock();

            // 验证自己的权限
            unionMemberInfo = memberInfoMap.get(accountId);
            if (unionMemberInfo.getUnionJob() == null || unionMemberInfo.getUnionJob().getPermission() < UnionJob.PRESIDENT.getPermission()) {
                RequestException.throwException(I18nId.NOT_PERMISSION);
            }
            UnionMemberInfo targetUnionMemberInfo = memberInfoMap.get(targetAccountId);
            // 验证目标在工会中
            if (targetUnionMemberInfo == null) {
                RequestException.throwException(I18nId.TARGET_NOT_JOIN_UNION);
            }
            // 将目标任命为会长
            targetUnionMemberInfo.setUnionJob(UnionJob.PRESIDENT);
            // 将操作者任命为普通成员
            UnionMemberInfo handleUnionMemberInfo = memberInfoMap.get(accountId);
            handleUnionMemberInfo.setUnionJob(UnionJob.MEMBER);
            // 修改行会会长id
            unionInfo.setPresidentId(targetAccountId);
            // 保存行会信息
            unionManager.saveUnion(unionEnt);
            // 通知操作者客户端
            SendPacketUtil.send(accountId, SM_AppointCaptain.valueOf(1));
        } finally {
            lock.unlock();
        }

    }

    @Override
    public void disband(String accountId) {
        UnionAccountEnt unionMember = unionManager.getUnionMember(accountId);
        // 验证accountId是否有工会，
        String unionId = unionMember.getUnionId();
        if (unionId == null) {
            RequestException.throwException(I18nId.NOT_JOIN_UNION);

        }
        //验证对应的工会中是否有我的信息
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        if (unionEnt == null) {
            RequestException.throwException(I18nId.UNION_NOT_EXIST);
        }
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        Map<String, UnionMemberInfo> memberInfoMap = unionInfo.getMemberInfoMap();
        UnionMemberInfo unionMemberInfo = memberInfoMap.get(accountId);
        if (unionMemberInfo == null) {
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        // 解散的时候 其他玩家必须不在行会 所以需要验证行会中是否还有其他成员 需要二次验证
        if (memberInfoMap.size() > 1 || unionMemberInfo.getUnionJob() != UnionJob.PRESIDENT) {
            RequestException.throwException(I18nId.NOT_DISBAND);
        }
        Lock lock = unionInfo.getLock();
        try {
            lock.lock();
            unionEnt = unionManager.getUnionEnt(unionId);
            unionInfo = unionEnt.getUnionInfo();
            memberInfoMap = unionInfo.getMemberInfoMap();
            unionMemberInfo = memberInfoMap.get(accountId);
            if (unionMemberInfo == null) {
                RequestException.throwException(I18nId.NOT_JOIN_UNION);
            }
            // 解散的时候 其他玩家必须不在行会 所以需要验证行会中是否还有其他成员 需要二次验证
            if (memberInfoMap.size() > 1 || unionMemberInfo.getUnionJob() != UnionJob.PRESIDENT) {
                RequestException.throwException(I18nId.NOT_DISBAND);
            }
            // 清除自己的数据
            unionMember = unionManager.getUnionMember(accountId);
            unionMember.setUnionId(null);
            memberInfoMap.clear();
            unionManager.saveUnion(unionEnt);
            unionManager.delete(unionId);
            // 通知客户端
            SM_DisbandUnionSucc sm = new SM_DisbandUnionSucc();
            SendPacketUtil.send(accountId, sm);

        } finally {
            lock.unlock();
        }

    }
}
