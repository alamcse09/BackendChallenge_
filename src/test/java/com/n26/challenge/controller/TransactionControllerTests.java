package com.n26.challenge.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.google.gson.Gson;
import com.n26.challenge.models.Transaction;
import com.n26.challenge.time.Time;
import com.n26.challenge.transaction.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
public class TransactionControllerTests {

	@Autowired
	private Time time;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private Gson gson;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		
		log.debug( "Setting up mockmvc for transaction controller with context - {}", context );
		
		mockMvc = MockMvcBuilders
					.webAppContextSetup( context )
					.build();
		
		MockitoAnnotations.initMocks( this );
	}
	
	@Test
	public void insertTransactionTest() throws Exception {
		
		Transaction currentTransaction = new Transaction( 12.5, time.getCurrentTimestamp() );
		
		log.debug( "Performing post request with data - {}", currentTransaction );
		mockMvc
			.perform( post( "/transactions" )
					.contentType( MediaType.APPLICATION_JSON_UTF8 )
					.content( gson.toJson( currentTransaction) ) )
			.andExpect( status().isCreated() );
		
		Transaction oldTransaction = new Transaction( 12.5, time.getInvalidTimestamp() );
		
		log.debug( "Performing post request with data - {}", oldTransaction );
		mockMvc
			.perform( post( "/transactions" )
				.contentType( MediaType.APPLICATION_JSON_UTF8 )
				.content( gson.toJson( oldTransaction ) ) )
			.andExpect( status().isNoContent() );
		
	}
}
