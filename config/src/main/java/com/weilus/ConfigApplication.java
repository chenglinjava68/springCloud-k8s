package com.weilus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableDiscoveryClient
@EnableConfigServer
@SpringBootApplication
public class ConfigApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}

	@EnableWebSecurity
	static class WebSecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().ignoringAntMatchers("/actuator/**");
			http.authorizeRequests().mvcMatchers("/actuator/info","/actuator/health").permitAll();
			super.configure(http);
		}
	}
	
}
