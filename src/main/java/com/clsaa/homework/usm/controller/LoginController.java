package com.clsaa.homework.usm.controller;

import com.clsaa.homework.usm.config.BizCodes;
import com.clsaa.homework.usm.model.dto.UserDtoV1;
import com.clsaa.homework.usm.model.vo.UserV1;
import com.clsaa.homework.usm.service.UserService;
import com.clsaa.homework.usm.util.BeanUtils;
import com.clsaa.rest.result.bizassert.BizAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * @author joyren
 */
@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

}
