package com.n26.challenge.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
public class StatisticsControllerTests {

	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Gson gson;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		
		log.debug( "Setting up MockMvc for stat controller test " );
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup( context )
					.build();
	}
	
	@Test
	public void getStatsTest() throws Exception {
		
		log.debug( "Performing get request to statistics controller" );
		mockMvc
			.perform( get( "/statistics" ) )
			.andExpect( status().isOk() );
	}

}
