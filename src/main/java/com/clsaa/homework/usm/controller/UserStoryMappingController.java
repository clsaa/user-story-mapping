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



}
