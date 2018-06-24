package com.n26.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.gson.Gson;
import com.n26.challenge.time.Time;
import com.n26.challenge.transaction.service.TransactionService;
import com.n26.challenge.transaction.service.TransactionServiceImpl;

@Configuration
public class Config {

	public static final int SECONDS_TO_CONSIDER = 60;
	public static final int SECOND_TO_MILLISECOND_MULTIPLIER = 1000;
	public static final long TEN_DAYS_IN_SECOND = 60L * 60L * 24L * 10L;
	public static final int NUM_OF_TRANSACTIONS_FOR_TEST = 5000;
	
	@Bean
	public Time time() {
		
		return new Time();
	}
	
	@Bean
	public Gson gson() {
		
		return new Gson();
	}
	
	@Bean
	public TransactionService transactionService() {
		
		return new TransactionServiceImpl();
	}
}
