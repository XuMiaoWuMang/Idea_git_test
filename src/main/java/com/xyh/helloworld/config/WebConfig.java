package com.xyh.helloworld.config;

import com.xyh.helloworld.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/api/**")               // 拦截 /api 下所有请求
                .excludePathPatterns("/api/users/login"); // 仅放行登录接口（其他精细化放行在拦截器内实现）
    }
}