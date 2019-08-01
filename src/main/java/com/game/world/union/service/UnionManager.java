package com.game.world.union.service;

import com.db.EntityBuilder;
import com.db.cache.EntityCacheService;
import com.game.world.union.entity.UnionEnt;
import com.game.world.union.entity.UnionMemberEnt;
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
    private EntityCacheService<String, UnionMemberEnt> unionMemberCacheService;

    @Autowired
    private StorageManager storageManager;

    public UnionEnt getUnionEnt(String unionId) {
        return unionCacheService.find(UnionEnt.class, unionId);
    }

    public UnionMemberEnt getUnionMember(String accountId) {
        return unionMemberCacheService.findOrCreate(UnionMemberEnt.class, accountId, new EntityBuilder<String, UnionMemberEnt>() {
            @Override
            public UnionMemberEnt newInstance(String id) {
                return UnionMemberEnt.valueOf(id);
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

    public void saveUnionMember(UnionMemberEnt unionMemberEnt) {
        unionMemberCacheService.saveOrUpdate(unionMemberEnt);
    }

}
