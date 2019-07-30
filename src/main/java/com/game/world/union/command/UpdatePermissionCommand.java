package com.game.world.union.command;

import com.game.SpringContext;
import com.game.base.executor.account.impl.AbstractAccountCommand;

/**
 * @Author：xuxin
 * @Date: 2019/7/30 10:26
 */
public class UpdatePermissionCommand extends AbstractAccountCommand {
    /**
     * 操作者id
     */
    private String handleAccountId;
    /**
     * 权限
     */
    private int permission;
    /**
     * 是否委任队长
     */
    private boolean isAppointCaptain;

    public static UpdatePermissionCommand valueOf(String handleAccountId, String targetAccountId, int permission, boolean isAppointCaptain) {
        UpdatePermissionCommand command = new UpdatePermissionCommand(targetAccountId);
        command.setHandleAccountId(handleAccountId);
        command.setPermission(permission);
        command.setAppointCaptain(isAppointCaptain);
        return command;
    }

    public boolean isAppointCaptain() {
        return isAppointCaptain;
    }

    public void setAppointCaptain(boolean appointCaptain) {
        isAppointCaptain = appointCaptain;
    }

    public UpdatePermissionCommand(String accountId) {
        super(accountId);
    }

    public String getHandleAccountId() {
        return handleAccountId;
    }

    public void setHandleAccountId(String handleAccountId) {
        this.handleAccountId = handleAccountId;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    @Override
    public String getName() {
        return "UpdatePermissionCommand";
    }

    @Override
    public void active() {
        SpringContext.getUnionService().doUpdatePermission(handleAccountId, getAccountId(), permission, isAppointCaptain);
    }


}
