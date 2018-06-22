package com.n26.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;

@Configuration
public class Config {

	@Bean
	public Gson gson() {
		
		return new Gson();
	}
}
