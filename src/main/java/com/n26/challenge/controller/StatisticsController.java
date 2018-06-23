package com.n26.challenge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.challenge.statistics.Statistics;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class StatisticsController {

	@GetMapping( "/statistics" )
	public Statistics getStats() {
		
		log.debug( "Statistics get controller called" );
		return new Statistics();
	}
}
