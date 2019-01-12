package com.clsaa.homework.usm.controller;

import com.clsaa.homework.usm.enums.UserStoryMappingStatusEnum;
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
                                                       @RequestParam("name") String name,
                                                       @RequestParam("description") String description,
                                                       @RequestParam("data") String data,
                                                       @RequestParam("status") UserStoryMappingStatusEnum status,
                                                       @ApiIgnore @RequestHeader("X-LOGIN-USER-ID") String loginUserId) {
        return this.userStoryMappingService.updateUserStoryMapping(id, name, description, data, status, loginUserId);
    }
    @GetMapping("/v1/usms")
    public List<UserStoryMappingV1> findUserStoryMappingsByCuserAndStatusV1(@RequestParam("stauts") UserStoryMappingStatusEnum status,
                                                                            @ApiIgnore @RequestHeader("X-LOGIN-USER-ID") String loginUserId) {
        return this.userStoryMappingService.findUserStoryMappingsByCuserAndStatusV1(status, loginUserId);
    }
}
