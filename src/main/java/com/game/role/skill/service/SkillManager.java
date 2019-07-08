package com.game.role.skill.service;

import com.db.EntityBuilder;
import com.db.cache.EntityCacheService;
import com.game.role.skill.entity.SkillEnt;
import com.game.role.skill.resource.JobSkillResource;
import com.game.role.skill.resource.SkillLevelResource;
import com.game.role.skill.resource.SkillResource;
import com.resource.core.StorageManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author：xuxin
 * @Date: 2019/7/8 16:10
 */
@Component
public class SkillManager {

    @Autowired
    private StorageManager storageManager;
    @Autowired
    private EntityCacheService<Long, SkillEnt> entityCacheService;

    public SkillResource getSkillResource(int id){
        return storageManager.getResource(id,SkillResource.class);
    }

    public SkillLevelResource getSkillLevelResource(String id){
        return storageManager.getResource(id,SkillLevelResource.class);
    }

    public JobSkillResource getJobSkillResource(int jobType){
        return storageManager.getResource(jobType,JobSkillResource.class);
    }

    public void save(SkillEnt skillEnt){
        entityCacheService.saveOrUpdate(skillEnt);
    }
    public SkillEnt getOrCreateSkillEnt(long playerId){
        return entityCacheService.findOrCreate(SkillEnt.class, playerId, new EntityBuilder<Long, SkillEnt>() {
            @Override
            public SkillEnt newInstance(Long id) {
                return SkillEnt.valueOf(id);
            }
        });
    }
}
