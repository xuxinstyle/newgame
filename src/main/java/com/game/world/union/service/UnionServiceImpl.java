package com.game.world.union.service;

import com.game.SpringContext;
import com.game.base.gameobject.constant.ObjectType;
import com.game.common.exception.RequestException;
import com.game.util.I18nId;
import com.game.util.SendPacketUtil;
import com.game.util.TimeUtil;
import com.game.world.union.command.AgreeApplyCommand;
import com.game.world.union.command.AppoinCaptainCommand;
import com.game.world.union.command.KickCommand;
import com.game.world.union.command.UpdatePermissionCommand;
import com.game.world.union.constant.UnionJob;
import com.game.world.union.entity.UnionEnt;
import com.game.world.union.entity.UnionMemberEnt;
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
import java.util.Set;

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
        UnionMemberEnt unionMemberEnt = unionManager.getUnionMember(accountId);
        if (unionMemberEnt == null || unionMemberEnt.getUnionId() != null) {
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
        unionInfo.addMember(accountId);
        // 修改unionMember信息
        unionMemberEnt.setUnionId(unionId);
        UnionMemberInfo unionMemberInfo = unionMemberEnt.getUnionMemberInfo();
        unionMemberInfo.setEnterTime(TimeUtil.now());
        unionMemberInfo.setUnionJob(UnionJob.PRESIDENT);
        logger.info("玩家[{}]创建公会[{}]成功", accountId, unionId);
        // 保存
        unionManager.saveUnion(unionEnt);
        unionManager.saveUnionMember(unionMemberEnt);
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
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        if (unionInfo == null) {
            RequestException.throwException(I18nId.UNION_NOT_EXIST);
        }
        // 判断玩家是否加入工会
        UnionMemberEnt unionMemberEnt = unionManager.getUnionMember(accountId);
        if (unionMemberEnt == null || unionMemberEnt.getUnionId() != null) {
            RequestException.throwException(I18nId.NOT_APPLY_UNION);
        }

        // 添加到申请列表
        unionInfo.getApplyList().add(accountId);
        logger.info("玩家[{}]申请进入行会[{}]", accountId, unionId);
        SendPacketUtil.send(accountId, new SM_ApplyJoinUnionSucc());
    }

    @Override
    public void showApplyList(String accountId) {
        // 检查玩家是否加入行会
        UnionMemberEnt unionMemberEnt = unionManager.getUnionMember(accountId);
        if (unionMemberEnt.getUnionId() == null) {
            // 没有加入行会
            logger.info("玩家[{}]没有加入行会,无法查看的申请列表", accountId);
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        // 检查玩家在行会中的权限
        String unionId = unionMemberEnt.getUnionId();
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        // 检查玩家的工会是否存在
        if (unionEnt == null) {
            RequestException.throwException(I18nId.UNION_NOT_EXIST);
        }
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        Set<String> applyList = unionInfo.getApplyList();
        // 通知客户端
        SM_ShowApplyList sm = new SM_ShowApplyList();
        sm.setApplyList(applyList);
        SendPacketUtil.send(accountId, sm);
    }

    @Override
    public void agreeApply(String accountId, String targetAccountId) {
        // 验证自己是否在行会中
        UnionMemberEnt unionMemberEnt = unionManager.getUnionMember(accountId);
        String unionId = unionMemberEnt.getUnionId();
        if (unionId == null) {
            // 没有加入行会
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        // 验证自己的行会权限
        UnionMemberInfo unionMemberInfo = unionMemberEnt.getUnionMemberInfo();
        if (unionMemberInfo.getUnionJob().getPermission() <= UnionJob.VICE_PRESIDENT.getPermission()) {
            // 没有权限
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        // 玩家不在申请列表中  需要二次验证
        if (!unionInfo.getApplyList().contains(targetAccountId)) {
            RequestException.throwException(I18nId.NOT_APPLY);
        }

        // 验证目标账号是否加入其他工会 需要二次验证
        // 有可能在在该方法中 玩家进入其他行会 所以在command中需要再验证一次
        // 这里本应是在目标线程中验证
        // 再这里验证是为了提高效率 为了避免不必要的加入操作  因为加入总是要一个个来 如果通过了太多的不必要的操作会总是阻塞性能降低
        UnionMemberEnt targetUnionMemberEnt = unionManager.getUnionMember(targetAccountId);
        String targetUnionId = targetUnionMemberEnt.getUnionId();
        if (targetUnionId != null) {
            RequestException.throwException(I18nId.JOIN_OTHER_UNION);
        }

        // 验证行会人数是否已经满了 需要二次验证
        if (unionInfo.getMemberIds().size() >= unionInfo.getMaxNum()) {
            // 工会人数已满
            RequestException.throwException(I18nId.UNION_NUM_LIMIT);
        }
        AgreeApplyCommand command = AgreeApplyCommand.valueOf(accountId, targetAccountId, unionId);
        SpringContext.getAccountExecutorService().submit(command);

    }

    @Override
    public void doAgreeApply(String handleAccountId, String unionId, String targetAccountId) {

        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        // 对工会对象加锁 因为下列代码修改了申请列表和工会成员列表
        synchronized (unionEnt) {
            UnionInfo unionInfo = unionEnt.getUnionInfo();
            Set<String> applyList = unionInfo.getApplyList();
            // 验证玩家是否在申请列表中
            if (!applyList.contains(targetAccountId)) {
                // 这里应该通知操作者 所以这里不能用RequestException
                SendPacketUtil.send(handleAccountId, SM_AgreeApply.valueOf(1));
                return;
            }
            // 验证目标账号是否加入其他工会
            UnionMemberEnt targetUnionMemberEnt = unionManager.getUnionMember(targetAccountId);
            if (targetUnionMemberEnt.getUnionId() != null) {
                // 加入了其他工会 移除申请列表
                applyList.remove(targetAccountId);
                SendPacketUtil.send(handleAccountId, SM_AgreeApply.valueOf(2));
                return;
            }

            // 验证行会人数是否已经满了 二次验证
            if (unionInfo.getMemberIds().size() >= unionInfo.getMaxNum()) {
                // 工会人数已满
                SendPacketUtil.send(handleAccountId, SM_AgreeApply.valueOf(3));
                return;
            }
            applyList.remove(targetAccountId);
            // 将目标加入
            unionInfo.addMember(targetAccountId);
            // 修改accountIdUnion
            targetUnionMemberEnt.setUnionId(unionId);
            UnionMemberInfo unionMemberInfo = targetUnionMemberEnt.getUnionMemberInfo();
            unionMemberInfo.setUnionJob(UnionJob.MEMBER);
            unionMemberInfo.setEnterTime(TimeUtil.now());
            // 保存 通知客户端
            unionManager.saveUnionMember(targetUnionMemberEnt);
            unionManager.saveUnion(unionEnt);
            SendPacketUtil.send(targetAccountId, SM_AgreeApply.valueOf(0));
        }
    }

    @Override
    public void refuse(String handleAccounrId, String targetAccountId) {
        // 验证操作者
        UnionMemberEnt unionMemberEnt = unionManager.getUnionMember(handleAccounrId);
        String unionId = unionMemberEnt.getUnionId();
        if (unionId == null) {
            // 操作者不在行会
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        // 验证操作者权限
        UnionJob unionJob = unionMemberEnt.getUnionMemberInfo().getUnionJob();
        if (unionJob == null) {
            return;
        }
        if (unionJob.getPermission() < UnionJob.VICE_PRESIDENT.getPermission()) {
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        if (unionEnt == null) {
            return;
        }
        // 从列表移除 由于集合是线程安全的所以不需要加锁
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        if (!unionInfo.getApplyList().remove(targetAccountId)) {
            RequestException.throwException(I18nId.NOT_APPLY);
        }

        // 保存 通知客户端
        unionManager.saveUnion(unionEnt);
        SendPacketUtil.send(targetAccountId, SM_RefuseApply.valueOf(1));
        SendPacketUtil.send(handleAccounrId, SM_RefuseApply.valueOf(2));

    }

    @Override
    public void showMySelfUnion(String accountId) {
        UnionMemberEnt unionMember = unionManager.getUnionMember(accountId);
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
        Set<String> memberIds = unionInfo.getMemberIds();
        List<UnionMemberVO> unionMemberVOList = new ArrayList<>();
        for (String memberAccountId : memberIds) {
            UnionMemberEnt unionMemberEnt = unionManager.getUnionMember(memberAccountId);
            UnionMemberInfo unionMemberInfo = unionMemberEnt.getUnionMemberInfo();
            if (unionMemberEnt.getUnionId() == null || !unionId.equals(unionMemberEnt.getUnionId()) || unionMemberInfo.getUnionJob() == null) {
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
        unionVO.setCurrNum(unionInfo.getMemberIds().size());
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
            UnionVO unionVO = new UnionVO();
            unionVO.setUnionId(unionEnt.getUnionId());
            unionVO.setPresidentId(unionInfo.getPresidentId());
            unionVO.setCurrNum(unionInfo.getMemberIds().size());
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
     *
     * @param accountId
     */
    @Override
    public void exitUnion(String accountId) {
        // 验证是否加入工会
        UnionMemberEnt unionMemberEnt = unionManager.getUnionMember(accountId);
        String unionId = unionMemberEnt.getUnionId();
        if (unionId == null) {
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        UnionMemberInfo unionMemberInfo = unionMemberEnt.getUnionMemberInfo();
        UnionJob unionJob = unionMemberInfo.getUnionJob();
        // 验证权限 是工会会长的玩家需要先移交会长位置
        if (unionJob.getPermission() >= UnionJob.PRESIDENT.getPermission()) {
            RequestException.throwException(I18nId.NOT_EXIT_UNION);
        }

        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        // 验证行会中是否有自己
        UnionInfo unionInfo = unionEnt.getUnionInfo();
        Set<String> memberIds = unionInfo.getMemberIds();
        if (!memberIds.remove(accountId)) {
            // 移除失败 不在行会中
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }

        // 移除成功 修改自己的工会信息

        unionMemberEnt.setUnionId(null);
        unionMemberInfo.setEnterTime(0);
        unionMemberInfo.setUnionJob(null);
        // 保存 通知客户端
        unionManager.saveUnionMember(unionMemberEnt);
        unionManager.saveUnion(unionEnt);
        SendPacketUtil.send(accountId, new SM_ExitUnion());
    }

    @Override
    public void kickOther(String accountId, String targetAccountId) {
        UnionMemberEnt unionMember = unionManager.getUnionMember(accountId);
        UnionMemberInfo unionMemberInfo = unionMember.getUnionMemberInfo();
        UnionMemberEnt targetUnionMember = unionManager.getUnionMember(targetAccountId);
        // 验证目标是否有工会
        String unionId = unionMember.getUnionId();
        if (targetUnionMember == null || targetUnionMember.getUnionId() == null) {
            // 目标不在工会中
            RequestException.throwException(I18nId.TARGET_NOT_JOIN_UNION);
        }
        UnionMemberInfo targetUnionMemberInfo = targetUnionMember.getUnionMemberInfo();
        // 验证操作者是否在目标工会 需要二次验证 防止在进入command后其他人也移除了他 让他不在工会
        if (unionId == null || !targetUnionMember.getUnionId().equals(unionId)) {
            // 无法操作 其他工会成员
            RequestException.throwException(I18nId.NOT_HANDLE);
        }
        // 验证权限

        int permission = unionMemberInfo.getUnionJob().getPermission();
        int targetPermission = targetUnionMemberInfo.getUnionJob().getPermission();
        if (permission <= targetPermission) {
            // 没有权限
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }
        KickCommand command = KickCommand.valueOf(accountId, unionId, targetAccountId);
        SpringContext.getAccountExecutorService().submit(command);

    }

    @Override
    public void doKickOther(String handleAccountId, String unionId, String targetAccountId) {
        UnionMemberEnt unionMember = unionManager.getUnionMember(targetAccountId);
        // 二次验证目标是否在工会中
        if (unionMember.getUnionId() == null) {
            SendPacketUtil.send(handleAccountId, SM_KickOther.valueOf(2));
            return;
        }
        if (!unionMember.getUnionId().equals(unionId)) {
            SendPacketUtil.send(handleAccountId, SM_KickOther.valueOf(2));
            return;
        }
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);

        synchronized (unionEnt) {
            UnionInfo unionInfo = unionEnt.getUnionInfo();
            // 从工会成员列表中 移除目标
            unionInfo.getMemberIds().remove(targetAccountId);
        }
        UnionMemberInfo unionMemberInfo = unionMember.getUnionMemberInfo();
        unionMemberInfo.setUnionJob(null);
        unionMemberInfo.setEnterTime(0);
        unionMember.setUnionId(null);
        // 保存
        unionManager.saveUnion(unionEnt);
        unionManager.saveUnionMember(unionMember);
        // 通知目标和操作者
        SendPacketUtil.send(handleAccountId, SM_KickOther.valueOf(1));
        SendPacketUtil.send(targetAccountId, SM_KickOther.valueOf(3));
    }

    @Override
    public void updatePermission(String accountId, String targetAccountId, int permission) {
        // 验证权限是否合法
        UnionJob unionJob = UnionJob.getUnionJob(permission);
        if (unionJob == null) {
            RequestException.throwException(I18nId.PERMISSION_ERROR);
        }
        // 验证自己和目标是否在工会中 double Check
        UnionMemberEnt unionMember = unionManager.getUnionMember(accountId);
        UnionMemberEnt targetUnionMember = unionManager.getUnionMember(targetAccountId);
        if (unionMember.getUnionId() == null || targetUnionMember.getUnionId() == null ||
                !unionMember.getUnionId().equals(targetUnionMember.getUnionId())) {
            logger.warn("操作者工会[{}]和目标工会[{}]不一致，无法修改权限", unionMember.getUnionId(), targetUnionMember.getUnionId());
            RequestException.throwException(I18nId.TARGET_NOT_JOIN_UNION);
        }
        // 验证权限 需要二次验证
        UnionMemberInfo unionMemberInfo = unionMember.getUnionMemberInfo();
        UnionMemberInfo targetUnionMemberInfo = targetUnionMember.getUnionMemberInfo();
        if (unionMemberInfo.getUnionJob() == null || targetUnionMemberInfo.getUnionJob() == null) {
            return;
        }
        if (unionMemberInfo.getUnionJob().getPermission() <= permission) {
            logger.warn("玩家[{}-{}]修改玩家[{}-{}]权限至[{}]失败", accountId, unionMemberInfo.getUnionJob().getPermission(),
                    targetAccountId, targetUnionMemberInfo.getUnionJob().getPermission(), permission);
            // 权限不足
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }
        // 抛到目标玩家线程去修改玩家的权限
        UpdatePermissionCommand command = UpdatePermissionCommand.valueOf(accountId, targetAccountId, permission, false);
        SpringContext.getAccountExecutorService().submit(command);

    }

    @Override
    public void doUpdatePermission(String handleAccountId, String targetAccountId, int permission, boolean isAppointCaptain) {
        // 防止玩家退出了行会
        UnionMemberEnt unionMember = unionManager.getUnionMember(handleAccountId);
        UnionMemberEnt targetUnionMember = unionManager.getUnionMember(targetAccountId);
        if (unionMember.getUnionId() == null || targetUnionMember.getUnionId() == null ||
                !unionMember.getUnionId().equals(targetUnionMember.getUnionId())) {
            logger.warn("操作者工会[{}]和目标工会[{}]不一致，无法修改权限", unionMember.getUnionId(), targetUnionMember.getUnionId());
            SendPacketUtil.send(handleAccountId, SM_UpdatePermission.valueOf(2));
            return;
        }

        // 验证权限 二次验证
        UnionMemberInfo unionMemberInfo = unionMember.getUnionMemberInfo();
        UnionMemberInfo targetUnionMemberInfo = targetUnionMember.getUnionMemberInfo();
        if (unionMemberInfo.getUnionJob() == null || targetUnionMemberInfo.getUnionJob() == null) {
            return;
        }
        if (unionMemberInfo.getUnionJob().getPermission() <= permission && !isAppointCaptain) {
            logger.warn("玩家[{}-{}]修改玩家[{}-{}]权限至[{}]失败", handleAccountId, unionMemberInfo.getUnionJob().getPermission(),
                    targetAccountId, targetUnionMemberInfo.getUnionJob().getPermission(), permission);
            // 权限不足
            SendPacketUtil.send(handleAccountId, SM_UpdatePermission.valueOf(2));
            return;
        }
        UnionJob unionJob = UnionJob.getUnionJob(permission);
        if (unionJob == null) {
            SendPacketUtil.send(handleAccountId, SM_UpdatePermission.valueOf(2));
            return;
        }
        // 修改
        targetUnionMemberInfo.setUnionJob(unionJob);
        // 保存 通知客户端
        unionManager.saveUnionMember(targetUnionMember);
        SendPacketUtil.send(handleAccountId, SM_UpdatePermission.valueOf(1));

    }

    @Override
    public void appoinCaptain(String accountId, String targetAccountId) {
        // 验证自己是否在工会中 权限是否足够
        UnionMemberEnt unionMember = unionManager.getUnionMember(accountId);
        String unionId = unionMember.getUnionId();
        if (unionId == null) {
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        // 验证目标是否在工会中 需要二次验证
        // 验证目标在工会中
        UnionMemberEnt targetUnionMember = unionManager.getUnionMember(targetAccountId);
        String targetUnionId = targetUnionMember.getUnionId();
        if (targetUnionId == null || !targetUnionId.equals(unionId)) {
            RequestException.throwException(I18nId.TARGET_NOT_JOIN_UNION);
        }

        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        if (unionEnt == null) {
            RequestException.throwException(I18nId.NOT_JOIN_UNION);
        }
        UnionMemberInfo unionMemberInfo = unionMember.getUnionMemberInfo();
        if (unionMemberInfo.getUnionJob().getPermission() < UnionJob.PRESIDENT.getPermission()) {
            RequestException.throwException(I18nId.NOT_PERMISSION);
        }
        AppoinCaptainCommand command = AppoinCaptainCommand.valueOf(accountId, unionId, targetAccountId);
        SpringContext.getAccountExecutorService().submit(command);

    }

    // 如果handleAccountId 同时点两下 点了任命不同的人怎么办 在accountId 需要修改handleAccountId的数据
    @Override
    public void doAppoinCaptain(String handleAccountId, String unionId, String accountId) {
        // 验证目标在工会中
        UnionMemberEnt unionMember = unionManager.getUnionMember(accountId);
        String targetUnionId = unionMember.getUnionId();
        if (targetUnionId == null || !targetUnionId.equals(unionId)) {
            SendPacketUtil.send(handleAccountId, SM_AppointCaptain.valueOf(2));
            return;
        }
        // 验证工会是否存在 有可能点完任命后立刻 解散工会
        UnionEnt unionEnt = unionManager.getUnionEnt(unionId);
        if (unionEnt == null) {
            return;
        }
        synchronized (unionEnt) {

            // 修改会长
            UnionInfo unionInfo = unionEnt.getUnionInfo();
            unionInfo.setPresidentId(accountId);
            // 修改目标会员信息 中的职位
            UnionMemberInfo unionMemberInfo = unionMember.getUnionMemberInfo();
            UnionJob oldUnionJob = unionMemberInfo.getUnionJob();
            unionMemberInfo.setUnionJob(UnionJob.PRESIDENT);

            // 修改操作者的信息 应该在操作者的线程修改 并且要在那个线程验证权限
            UpdatePermissionCommand command = UpdatePermissionCommand.valueOf(handleAccountId, handleAccountId, oldUnionJob.getPermission(), true);
            SpringContext.getAccountExecutorService().submit(command);
        }

    }
}
