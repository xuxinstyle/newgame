package com.game.world.union.service;

import com.db.EntityBuilder;
import com.db.cache.EntityCacheService;
import com.game.world.union.entity.UnionEnt;
import com.game.world.union.entity.UnionAccountEnt;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：xuxin
 * @Date: 2019/7/29 9:09
 */
@Component
public class UnionManager {
    @Autowired
    private EntityCacheService<String, UnionEnt> unionCacheService;
    @Autowired
    private EntityCacheService<String, UnionAccountEnt> unionMemberCacheService;

    @Autowired
    private StorageManager storageManager;

    public UnionEnt getUnionEnt(String unionId) {
        return unionCacheService.find(UnionEnt.class, unionId);
    }

    public UnionAccountEnt getUnionMember(String accountId) {
        return unionMemberCacheService.findOrCreate(UnionAccountEnt.class, accountId, new EntityBuilder<String, UnionAccountEnt>() {
            @Override
            public UnionAccountEnt newInstance(String id) {
                return UnionAccountEnt.valueOf(id);
            }
        });
    }

    public void delete(String unionId) {
        unionCacheService.remove(UnionEnt.class, unionId);
    }

    public List<UnionEnt> getUnionList() {
        return unionCacheService.findAll(UnionEnt.class);
    }

    public void saveUnion(UnionEnt unionEnt) {
        unionCacheService.saveOrUpdate(unionEnt);
    }

    public void saveUnionMember(UnionAccountEnt unionAccountEnt) {
        unionMemberCacheService.saveOrUpdate(unionAccountEnt);
    }

}
