package com.mainproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mainproject.common.interceptor.PreviousPageInterceptor;
import com.mainproject.common.interceptor.ViewNameInterceptor;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	
	@Autowired
    private PreviousPageInterceptor previousPageInterceptor;
	
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ViewNameInterceptor()).addPathPatterns("/*/*.do");
        registry.addInterceptor(previousPageInterceptor).addPathPatterns("/*/*.do");
    }
}
 