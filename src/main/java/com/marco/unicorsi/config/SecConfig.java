package com.marco.unicorsi.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecConfig extends WebSecurityConfigurerAdapter{

    private String USER_QUERY = "select username, password, active from user where username = ?";
    private String ROLE_QUERY = "select u.username, r.role from user u inner join role_user ur on (u.id_user = ur.id_user) inner join role r on (ur.id_role = r.id_role) where username = ?";

    @Autowired
    DataSource dataSource;

    @Bean
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
        .usersByUsernameQuery(USER_QUERY)
        .authoritiesByUsernameQuery(ROLE_QUERY)
        .dataSource(dataSource)
        .passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       
        http.authorizeRequests()
        .antMatchers("/").permitAll()
        .and().csrf().disable()
        .formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .permitAll()
        .defaultSuccessUrl("/index")
        .and()
        .logout()
        .logoutSuccessUrl("/index");
    }



}