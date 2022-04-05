package com.itprotopics.examples.springactuator.security;

import java.util.Arrays;
import java.util.Collections;

import com.itprotopics.examples.springactuator.actuator.GenericEndpointsActuator;
import com.itprotopics.examples.springactuator.actuator.VigenciaActuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class ResourceServerConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable()
				.authorizeRequests().antMatchers("/msuser-manager/actuator").permitAll()
				.antMatchers("/msuser-manager/v1/**").authenticated()
				.and()
				.httpBasic();
		;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.inMemoryAuthentication()
				.withUser("usermanager")
				.password("{noop}1111xxxx2")
				.roles("USER");
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Collections.singletonList("*"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));

		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		FilterRegistrationBean corsFilter = new FilterRegistrationBean(new CorsFilter(source));
		corsFilter.setOrder(Ordered.HIGHEST_PRECEDENCE);

		return source;
	}

	@Bean
	public FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(
				new CorsFilter(corsConfigurationSource()));
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}

	@Bean
	public VigenciaActuator vigenciaActuator() {
		return new VigenciaActuator();
	}

	@Bean
	public GenericEndpointsActuator genericEndpointsActuator() {
		return new GenericEndpointsActuator();
	}

}