package com.game.scence.base.model;

import com.game.base.executor.ICommand;
import com.game.role.player.model.Player;
import com.game.scence.fight.model.FightAccount;
import com.game.scence.visible.model.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：xuxin
 * @Date: 2019/7/3 16:14
 */
public abstract class AbstractFightScene extends AbstractScene{

    /**
     * 场景战斗数据
     */
    private Map<String, FightAccount> fightAccounts;

    public AbstractFightScene(){

    }
    public AbstractFightScene(int mapId) {
        super(mapId);
    }

    public AbstractFightScene(int mapId, int sceneId) {
        super(mapId, sceneId);
    }
    /**
     * 场景的定时任务
     */
    private Map<Class<? extends ICommand>, ICommand> commandMap = new HashMap<>();



    @Override
    public void leave(String accountId){
        super.leave(accountId);
        fightAccounts.remove(accountId);
    }
    @Override
    public void enter(Player player){
        if(getAccountIds()==null){
            setAccountIds(new ArrayList<>());
        }
        List<String> accountIds = getAccountIds();
        accountIds.add(player.getAccountId());
        fightAccounts.put(player.getAccountId(),FightAccount.valueOf(player));
    }

    public void addCommand(ICommand command){
        ICommand iCommand = commandMap.get(command.getClass());
        if(iCommand!=null){
            iCommand.cancel();
        }
        commandMap.put(command.getClass(),command);
    }

    public <T extends ICommand> T getCommand(Class<T> clz){
        return (T) commandMap.get(clz);
    }

    public Map<Class<? extends ICommand>, ICommand> getCommandMap() {
        return commandMap;
    }

    public void setCommandMap(Map<Class<? extends ICommand>, ICommand> commandMap) {
        this.commandMap = commandMap;
    }

    public Map<String, FightAccount> getFightAccounts() {
        return fightAccounts;
    }

    public void setFightAccounts(Map<String, FightAccount> fightAccounts) {
        this.fightAccounts = fightAccounts;
    }
}
