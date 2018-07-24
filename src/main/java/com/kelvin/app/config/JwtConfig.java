package com.kelvin.app.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Autowired
    JwtSetting jwtSetting;
    @Autowired
    ObjectMapper objectMapper;

    @Bean
    public FilterRegistrationBean jwtFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JwtFilter(jwtSetting, objectMapper));
        filterRegistrationBean.addUrlPatterns("/secured/*");
        return filterRegistrationBean;
    }
}
