package com.example.springboot.config;

import com.example.springboot.interceptor.Jwtinterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//对全局拦截器进行配置
public class InterceptorConfigs implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtinterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/user/registor","/role/**");

    }

    @Bean
    public Jwtinterceptor jwtinterceptor() {
        return new Jwtinterceptor();
    }
}
