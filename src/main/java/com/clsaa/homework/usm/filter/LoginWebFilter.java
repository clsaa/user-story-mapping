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
    private static final String SWAGGER = "swagger";
    private static final String SWAGGER_API_DOCS = "/v2/api-docs";
    private static final String SWAGGER_WEB_JAR = "webjars";
    private static final String LOGIN_FORWARD_PATH = "/login";

    private boolean shouldFilter(String path) {
        return !path.contains(LOGIN_PATH)
                && !path.contains(CSS_PATH)
                && !path.contains(JS_PATH)
                && !path.contains(IMG_PATH)
                && !path.contains(SWAGGER)
                && !path.contains(SWAGGER_API_DOCS)
                && !path.contains(SWAGGER_WEB_JAR);
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
                String userId = (String) session.getAttributes().get("X-LOGIN-USER-ID");
                if (StringUtils.isEmpty(userId)) {
                    //登录页面路径
                    String loginForwardUrl = request.getURI().getScheme() +
                            "://" +
                            request.getURI().getAuthority() +
                            LOGIN_FORWARD_PATH;
                    URI loginForwardUri = UriComponentsBuilder.fromHttpUrl(loginForwardUrl).build().toUri();
                    //创建返回值
                    return serverWebExchange.mutate().request(build ->
                            build.uri(loginForwardUri)).build();
                } else {
                    ServerHttpRequest loginPassRequest = serverWebExchange.getRequest()
                            .mutate()
                            .header("X-LOGIN-USER-ID", userId)
                            .build();
                    return serverWebExchange.mutate()
                            .request(loginPassRequest)
                            .build();
                }
            }).flatMap(webFilterChain::filter).then();
        }
        return webFilterChain.filter(serverWebExchange);
    }
}