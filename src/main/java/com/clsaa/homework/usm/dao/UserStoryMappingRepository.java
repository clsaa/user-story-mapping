package com.clsaa.homework.usm.dao;

import com.clsaa.homework.usm.enums.UserStoryMappingStatusEnum;
import com.clsaa.homework.usm.model.po.UserStoryMapping;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author joyren
 */
public interface UserStoryMappingRepository extends MongoRepository<UserStoryMapping, String> {

    /**
     * @param cuser  创建人
     * @param status 状态
     * @return {@link List<UserStoryMapping>}
     */
    List<UserStoryMapping> findUserStoryMappingsByCuserAndStatus(String cuser, UserStoryMappingStatusEnum status);
}
