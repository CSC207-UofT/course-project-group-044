package com.hr.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
/**
 * Controller for providing the web based security configuration.
 *  - Require the user to be authenticated prior to accessing any URL within our application
 *  - Create a user with the username “user”, password “password”, and role of “USER”
 *  - automatically render a login page and logout success page
 *
 */
public class SecurityController extends WebSecurityConfigurerAdapter {


    @Override
    /**
     * Grant to access the database without login as user, while in other case web security controller still work.
     */
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/h2", "/h2/*");
    }
}

