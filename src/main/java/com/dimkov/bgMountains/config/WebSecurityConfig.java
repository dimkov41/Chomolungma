package com.dimkov.bgMountains.config;

import com.dimkov.bgMountains.web.handler.AccessRestrictedHandlerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String SESSION_ATTR_NAME = "_csrf";

    private static final String LOGIN_PAGE_URL = "/users/login";
    private static final String USERNAME_PARAM = "username";
    private static final String PASSWORD_PARAM = "password";
    private static final String SUCCESS_URL = "/";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and()
                .authorizeRequests()
                .antMatchers("/", "/users/login",
                        "/users/register", "/css/**", "/js/**", "/images/**",
                        "/users/checkUsername/**", "/users/checkEmail/**")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(LOGIN_PAGE_URL)
                .usernameParameter(USERNAME_PARAM)
                .passwordParameter(PASSWORD_PARAM)
                .defaultSuccessUrl(SUCCESS_URL)
                .and()
                .logout()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
    }

    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository =
                new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName(SESSION_ATTR_NAME);
        return repository;
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new AccessRestrictedHandlerImpl();
    }
}
