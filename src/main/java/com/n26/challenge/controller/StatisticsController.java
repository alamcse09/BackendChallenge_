package com.n26.challenge.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.n26.challenge.Statistics;

@RestController
public class StatisticsController {

	@GetMapping( "/statistics" )
	public Statistics getStats() {
		
		return new Statistics();
	}
}
