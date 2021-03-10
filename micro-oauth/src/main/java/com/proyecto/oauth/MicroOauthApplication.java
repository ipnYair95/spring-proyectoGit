package com.proyecto.oauth;
 
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;  

@SpringBootApplication 
public class MicroOauthApplication  {
	
	 
	public static void main(String[] args) {
		SpringApplication.run(MicroOauthApplication.class, args);
	}
	
	@EnableWebSecurity
	@Configuration
	class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			
			http.cors().disable();
			
			http.csrf().disable() 
				.authorizeRequests()
				.antMatchers("**").permitAll()
				.anyRequest().authenticated();
		}
	}
	
	
	

	 

}
