package com.blog.configUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SuppressWarnings("deprecation")
@Configuration // To create custom beans if needed
@EnableWebSecurity // For Security Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers("/actuator/**").permitAll().antMatchers(HttpMethod.GET)
				.hasAnyRole("ADMIN", "REGULAR").antMatchers("/**").hasRole("ADMIN").anyRequest().authenticated().and()
				.httpBasic();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

//		For inMemoryAuthentication
//		auth.inMemoryAuthentication().withUser("Shivam").password(this.passwordEncoder().encode("12345"))
//				.roles("ADMIN");
//		auth.inMemoryAuthentication().withUser("Ayush").password(this.passwordEncoder().encode("67890"))
//				.roles("REGULAR");

//		For database Authentication
		auth.userDetailsService(customUserDetailService).passwordEncoder(new BCryptPasswordEncoder(10));
	}

//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder(10);
//	}

}