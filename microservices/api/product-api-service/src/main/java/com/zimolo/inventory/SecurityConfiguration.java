package com.zimolo.inventory;

 import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {




    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
            .antMatchers("/scripts/**/*.{js,html}")
            .antMatchers("/bower_components/**")
            .antMatchers("/i18n/**")
            .antMatchers("/assets/**")
            .antMatchers("/swagger-ui/**")
            .antMatchers("/test/**");
    }



  @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
        .and()
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
            .antMatchers("/api/imgget/**").permitAll()
            .antMatchers("/api/categories").permitAll();

    }



}
