package com.clsaa.homework.usm.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.reactive.function.server.*;

import java.net.URI;

/**
 * @author joyren
 */
@CrossOrigin
@Controller
public class ViewController {
    private static final HandlerFunction<ServerResponse> INDEX_REDIRECT_FUNCTION = s ->
            ServerResponse.temporaryRedirect(URI.create("/login.html")).build();
    private static final HandlerFunction<ServerResponse> LOGIN_REDIRECT_FUNCTION = s ->
            ServerResponse.temporaryRedirect(URI.create("/login.html")).build();


    @Bean
    RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions
                .route(RequestPredicates.GET("/"), INDEX_REDIRECT_FUNCTION)
                .andRoute(RequestPredicates.GET("/index"), INDEX_REDIRECT_FUNCTION)
                .andRoute(RequestPredicates.GET("/login"), LOGIN_REDIRECT_FUNCTION);
    }


}
