package com.pibox.springmvcsecuritydemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

	private final DataSource securityDataSource;

	public DemoSecurityConfig(DataSource securityDataSource) {
		this.securityDataSource = securityDataSource;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(securityDataSource);
	}

	/*	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		User.UserBuilder users = User.withDefaultPasswordEncoder();
		auth.inMemoryAuthentication()
				.withUser(users.username("john").password("test123").roles("EMPLOYEE"))
				.withUser(users.username("mary").password("test123").roles("EMPLOYEE", "MANAGER"))
				.withUser(users.username("mike").password("test123").roles("EMPLOYEE", "ADMIN"));
	}*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/").permitAll()
				.antMatchers("/employees").hasRole("EMPLOYEE")
				.antMatchers("/leaders/**").hasRole("MANAGER")
				.antMatchers("/systems/**").hasRole("ADMIN")
				.and()
					.formLogin()
						.loginPage("/login")
						.loginProcessingUrl("/authenticateUser")
						.permitAll()
				.and()
					.logout().permitAll()
						.logoutSuccessUrl("/").permitAll()
				.and()
					.exceptionHandling()
					.accessDeniedPage("/access-denied");
	}
}
