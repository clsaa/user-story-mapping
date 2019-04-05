package com.clsaa.homework.usm.service;

import com.clsaa.homework.usm.config.BizCodes;
import com.clsaa.homework.usm.dao.UserStoryMappingRepository;
import com.clsaa.homework.usm.enums.UserStoryMappingStatusEnum;
import com.clsaa.homework.usm.model.po.UserStoryMapping;
import com.clsaa.homework.usm.model.vo.UserStoryMappingV1;
import com.clsaa.homework.usm.util.BeanUtils;
import com.clsaa.rest.result.bizassert.BizAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author joyren
 */
@Service
public class UserStoryMappingService {
    @Autowired
    private UserStoryMappingRepository userStoryMappingRepository;


    public UserStoryMappingV1 addUserStoryMapping(String name, String description, String loginUserId) {
        UserStoryMapping userStoryMapping = UserStoryMapping.builder()
                .name(name)
                .description(description)
                .data("")
                .cuser(loginUserId)
                .ctime(LocalDateTime.now())
                .mtime(LocalDateTime.now())
                .status(UserStoryMappingStatusEnum.OK)
                .build();
        UserStoryMapping existUsm = this.userStoryMappingRepository.save(userStoryMapping);
        return BeanUtils.convertType(existUsm, UserStoryMappingV1.class);
    }

    public UserStoryMappingV1 updateUserStoryMapping(String id, String name, String description, String data,
                                                     UserStoryMappingStatusEnum status, String loginUserId) {
        UserStoryMapping existUsm = this.findById(id, loginUserId);
        existUsm.setName(name);
        existUsm.setDescription(description);
        existUsm.setData(data);
        existUsm.setMtime(LocalDateTime.now());
        existUsm.setStatus(status);
        existUsm = this.userStoryMappingRepository.save(existUsm);
        return BeanUtils.convertType(existUsm, UserStoryMappingV1.class);
    }

    private UserStoryMapping findById(String id, String loginUserId) {
        UserStoryMapping existUsm = this.userStoryMappingRepository.findById(id).orElse(null);
        BizAssert.found(existUsm != null, BizCodes.NOT_FOUND);
        BizAssert.found(existUsm.getCuser().equals(loginUserId), BizCodes.INVALID_USER);
        return existUsm;
    }

    public List<UserStoryMappingV1> findUserStoryMappingsByCuserAndStatusAndName(String cuser, UserStoryMappingStatusEnum status, String name) {
        if (name == null || StringUtils.isEmpty(name)) {
            return this.userStoryMappingRepository.findUserStoryMappingsByCuserAndStatus(cuser, status)
                    .stream().map(s -> BeanUtils.convertType(s, UserStoryMappingV1.class)).collect(Collectors.toList());
        }
        return this.userStoryMappingRepository.findUserStoryMappingsByCuserAndStatusAndNameLike(cuser, status, name)
                .stream().map(s -> BeanUtils.convertType(s, UserStoryMappingV1.class)).collect(Collectors.toList());
    }

    public UserStoryMappingV1 findUserStoryMappingById(String id, String loginUserId) {
        return BeanUtils.convertType(findById(id, loginUserId), UserStoryMappingV1.class);
    }
}
