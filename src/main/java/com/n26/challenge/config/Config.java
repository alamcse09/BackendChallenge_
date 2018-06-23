package com.n26.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.n26.challenge.statistics.service.StatisticsService;
import com.n26.challenge.statistics.service.StatisticsServiceImpl;
import com.n26.challenge.transaction.service.TransactionService;
import com.n26.challenge.transaction.service.TransactionServiceImpl;

@Configuration
public class Config {

	@Bean
	public Gson gson() {
		
		return new Gson();
	}
	
	@Bean
	public TransactionService transactionService() {
		
		return new TransactionServiceImpl();
	}
	
	@Bean
	public StatisticsService statisticsService() {
		
		return new StatisticsServiceImpl();
	}
}
