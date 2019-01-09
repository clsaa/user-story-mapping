package com.clsaa.homework.usm.controller;

import com.clsaa.homework.usm.config.BizCodes;
import com.clsaa.homework.usm.model.dto.UserDtoV1;
import com.clsaa.homework.usm.model.vo.LoginUserV1;
import com.clsaa.homework.usm.model.vo.UserV1;
import com.clsaa.homework.usm.service.UserService;
import com.clsaa.homework.usm.util.BeanUtils;
import com.clsaa.rest.result.bizassert.BizAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.UUID;


/**
 * @author joyren
 */
@CrossOrigin
@RestController
public class LoginController {
    @Autowired
    private UserService userService;

    @PostMapping("/v1/login")
    public Mono<LoginUserV1> login(@RequestBody UserDtoV1 userDtoV1, ServerWebExchange exchange) {
        UserV1 user = this.userService.findUserByEmail(userDtoV1.getEmail());
        LoginUserV1 loginUserV1 = BeanUtils.convertType(user, LoginUserV1.class);

        BizAssert.validParam(user != null
                && user.getPassword().equals(userDtoV1.getPassword()), BizCodes.INVALID_LOGIN);
        String token = UUID.randomUUID().toString();
        return exchange.getSession().flatMap(session -> {
            String exitToken = (String) session.getAttributes().get(user.getId());
            if (StringUtils.isEmpty(exitToken)) {
                session.getAttributes().put(token, user.getId());
                session.getAttributes().put(user.getId(), token);
                loginUserV1.setToken(token);
            } else {
                loginUserV1.setToken(exitToken);
            }
            return Mono.just(loginUserV1);
        });
    }
}
