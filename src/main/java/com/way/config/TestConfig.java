package com.way.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class TestConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //表单提交
        http.formLogin()
                //发现/login时认为是登录，必须和表单提交的地址一样，去执行UserDetailsServiceImpl
                .loginProcessingUrl("/login")
                //自定义登录页面
                .loginPage("/login.html")
                //登录成功后跳转页面，必须是post请求
                .successForwardUrl("/toMain")
                //登录失败后跳转页面，必须是post请求
                .failureForwardUrl("/toError");

        //授权认证
        http.authorizeRequests()
                //error.html不需要被认证
                .antMatchers("/error.html").permitAll()
                //login.html不需要被认证
                .antMatchers("/login.html").permitAll()
                //所有请求都必须被认证，必须登录之后被访问
                .anyRequest().authenticated();

        //关闭scrf防护
        http.csrf().disable();
    }

}
