package com.weilus.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import javax.sql.DataSource;

import static org.apache.coyote.http11.Constants.a;
import static org.springframework.security.config.http.MatcherType.ant;

/**
 * Created by liutq on 2018/10/23.
 */
@Configuration
@EnableOAuth2Client
@EnableWebSecurity
public class SecurityConfigure extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .csrf().ignoringAntMatchers("/actuator/**")
                .and()
                .antMatcher("/actuator/**").antMatcher("/oauth/check_token").httpBasic()
                .and()
                .authorizeRequests().mvcMatchers("/oauth/authorize").authenticated()
                .and()
                .formLogin().loginPage("/login").passwordParameter("password").usernameParameter("username");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        JdbcDaoImpl dao =new JdbcDaoImpl();
        dao.setDataSource(dataSource);
        dao.setRolePrefix("ROLE_");
//        dao.setUsersByUsernameQuery("select * from example");//自定义查询用户
//        dao.setAuthoritiesByUsernameQuery("select * example ");//自定义查询用户权限
//        dao.setEnableGroups(true);
//        dao.setGroupAuthoritiesByUsernameQuery("select * example ");//自定义查询用户角色
        auth.userDetailsService(dao);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}