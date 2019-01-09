package com.clsaa.homework.usm.filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

/**
 * @author joyren
 */
@Configuration
@Order(-1)
public class LoginWebFilter implements WebFilter {
    private static final String LOGIN_PATH = "/login";
    private static final String CSS_PATH = "css";
    private static final String JS_PATH = "js";
    private static final String IMG_PATH = "img";
    private static final String HTML_FILE = ".html";
    private static final String LOGIN_FORWARD_PATH = "/login";

    private boolean shouldFilter(String path) {
        return !path.contains(LOGIN_PATH)
                && !path.contains(CSS_PATH)
                && !path.contains(JS_PATH)
                && !path.contains(IMG_PATH)
                && !path.contains(HTML_FILE);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        System.out.println("login filter-----");
        ServerHttpRequest request = serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();
        String path = request.getURI().getPath();
        System.out.println(path + "---");
        if (shouldFilter(path)) {
            return serverWebExchange.getSession().map(session -> {
                String token = request.getHeaders().getFirst("X-LOGIN-USER-TOKEN");
                System.out.println("X-LOGIN-USER-TOKEN : " + token);
                if (StringUtils.isEmpty(token) || StringUtils.isEmpty(session.getAttributes().get(token))) {
                    //登录页面路径
                    String loginForwardUrl = request.getURI().getScheme() +
                            "://" +
                            request.getURI().getAuthority() +
                            LOGIN_FORWARD_PATH;
                    URI loginForwardUri = UriComponentsBuilder.fromHttpUrl(loginForwardUrl).build().toUri();
                    //创建返回值
                    ServerWebExchange loginExchange = serverWebExchange.mutate().request(build ->
                            build.uri(loginForwardUri)).build();
                    return loginExchange;
                } else {
                    String userId = (String) session.getAttributes().get(token);
                    ServerHttpRequest loginPassRequest = serverWebExchange.getRequest()
                            .mutate()
                            .header("X-LOGIN-USER-ID", userId)
                            .build();
                    ServerWebExchange loginPassExchange = serverWebExchange.mutate()
                            .request(loginPassRequest)
                            .build();
                    return loginPassExchange;
                }
            }).flatMap(webFilterChain::filter).then();
        }
        return webFilterChain.filter(serverWebExchange);
    }
}