package com.clsaa.homework.usm.controller;

import com.clsaa.homework.usm.enums.UserStoryMappingStatusEnum;
import com.clsaa.homework.usm.model.dto.UserStoryMappingDtoV1;
import com.clsaa.homework.usm.model.vo.UserStoryMappingV1;
import com.clsaa.homework.usm.service.UserStoryMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author joyren
 */
@CrossOrigin
@RestController
public class UserStoryMappingController {
    @Autowired
    private UserStoryMappingService userStoryMappingService;


    @PostMapping("/v1/usms")
    public UserStoryMappingV1 addUserStoryMappingV1(@RequestParam("name") String name,
                                                    @RequestParam("description") String description,
                                                    @ApiIgnore @RequestHeader("X-LOGIN-USER-ID") String loginUserId) {
        return this.userStoryMappingService.addUserStoryMapping(name, description, loginUserId);
    }

    @PutMapping("/v1/usms/{id}")
    public UserStoryMappingV1 updateUserStoryMappingV1(@PathVariable("id") String id,
                                                       @RequestBody UserStoryMappingDtoV1 userStoryMappingDtoV1,
                                                       @ApiIgnore @RequestHeader("X-LOGIN-USER-ID") String loginUserId) {
        return this.userStoryMappingService.updateUserStoryMapping(id,
                userStoryMappingDtoV1.getName(),
                userStoryMappingDtoV1.getDescription(),
                userStoryMappingDtoV1.getData(),
                userStoryMappingDtoV1.getStatus(),
                loginUserId);
    }

    @GetMapping("/v1/usms")
    public List<UserStoryMappingV1> findUserStoryMappingsByCuserAndStatusAndNameV1(@ApiIgnore @RequestHeader("X-LOGIN-USER-ID") String loginUserId,
                                                                                   @RequestParam("status") UserStoryMappingStatusEnum status,
                                                                                   @RequestParam(value = "name", required = false) String name) {
        return this.userStoryMappingService.findUserStoryMappingsByCuserAndStatusAndName(loginUserId, status, name);
    }

    @GetMapping("/v1/usms/{id}")
    public UserStoryMappingV1 findUserStoryMappingByIdV1(@PathVariable("id") String id,
                                                         @ApiIgnore @RequestHeader("X-LOGIN-USER-ID") String loginUserId) {
        return this.userStoryMappingService.findUserStoryMappingById(id, loginUserId);
    }
}
