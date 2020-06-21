package com.lee.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册登录拦截配置
 * 拦截/admin下的路径，除了/admin/login和 /admin/register
 */
@Configuration
public class AdminMvcConfig  implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AdminLoginHandlerInterceptor()).
                addPathPatterns("/admin/**").
                excludePathPatterns("/admin/login","/admin/register");
    }
}
