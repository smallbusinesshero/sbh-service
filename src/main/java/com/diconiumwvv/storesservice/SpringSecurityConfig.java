package com.diconiumwvv.storesservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, "/stores/**").authenticated()
                .anyRequest().permitAll()
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    public void configureGlobal(
            AuthenticationManagerBuilder auth,
            @Value("${auth.basic.user}") final String basicAuthUser,
            @Value("${auth.basic.password}") final String basicAuthPassword
    ) throws Exception {
        // TODO remove noop
        auth.inMemoryAuthentication()
                .withUser(basicAuthUser)
                .password("{noop}" + basicAuthPassword)
                .roles("USER");
    }
}