package com.urlshortener.config.security;


import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@EnableWebSecurity
public class ShortenerWebConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
         http
                 .csrf ().disable ().cors ().disable ()
                 .authorizeRequests()
                 .antMatchers(HttpMethod.POST,"/api/v1/**")
                 .permitAll()
                 .anyRequest ().authenticated ()
                 .and ();
    }

}
