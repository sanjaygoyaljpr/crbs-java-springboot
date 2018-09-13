package com.sanjaygoyaljpr.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sanjaygoyaljpr.configuration.security.ActiveUserStore;

@Configuration
public class AppConfig {
	// beans

	@Bean
	public ActiveUserStore activeUserStore() {
		return new ActiveUserStore();
	}

}