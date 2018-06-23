package com.n26.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.challenge.data.TransactionDataStore;
import com.n26.challenge.statistics.Statistics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class StatisticsController {

	@Autowired
	private TransactionDataStore dataStore;
	
	@GetMapping( "/statistics" )
	public Statistics getStats() {
		
		log.debug( "Statistics get controller called" );
		
		Statistics stats = dataStore.getStats();
		
		log.debug( "Sending statistics response- {}", stats );
		
		return stats;
	}
}
