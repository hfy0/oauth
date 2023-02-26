package com.example.client.config;


import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@EnableOAuth2Sso //这个注解包含了@EnableOAuth2Client
@Configuration
@Order(200)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 配置 HttpSecurity
     * <p>
     * 配置 /路径和 /login路径允许匿名访问，其它的路径需要身份认证后才能访问
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 配置 /路径和 /login路径允许匿名访问
                .antMatchers("/", "/login**")
                .permitAll()
                // 其它的路径需要身份认证后才能访问
                .anyRequest()
                .authenticated();
    }

    /**
     * 定义了OAuth2RestTemplate，网上一些比较老的资料给出的是手动读取配置文件来实现，最新版本已经可以自动注入OAuth2ProtectedResourceDetails
     *
     * @param oAuth2ClientContext
     * @param details
     * @return
     */
    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ClientContext oAuth2ClientContext, OAuth2ProtectedResourceDetails details) {
        return new OAuth2RestTemplate(details, oAuth2ClientContext);
    }
}