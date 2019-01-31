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
    @PostMapping("/v1/login")
    public Mono<UserV1> login(@RequestBody UserDtoV1 userDtoV1, ServerWebExchange exchange) {
        UserV1 user = this.userService.findUserByEmail(userDtoV1.getEmail());
        UserV1 userV1 = BeanUtils.convertType(user, UserV1.class);
        BizAssert.validParam(user != null
                && user.getPassword().equals(userDtoV1.getPassword()), BizCodes.INVALID_LOGIN);
        return exchange.getSession().flatMap(session -> {
            String userId = (String) session.getAttributes().get("X-LOGIN-USER-ID");
            if (StringUtils.isEmpty(userId)) {
                session.getAttributes().put("X-LOGIN-USER-ID", user.getId());
            }
            return Mono.just(userV1);
        });
    }
}
