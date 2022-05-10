package com.kula.growthchannelapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig {

    private final UserIdentityResolver userIdentityResolver;

    public WebConfig(UserIdentityResolver userIdentityResolver) {
        this.userIdentityResolver = userIdentityResolver;
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
                resolvers.add(userIdentityResolver);
            }
        };
    }

}
