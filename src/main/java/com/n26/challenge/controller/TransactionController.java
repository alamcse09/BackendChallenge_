package com.n26.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.n26.challenge.transaction.Transaction;
import com.n26.challenge.transaction.service.TransactionService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class TransactionController {

	@Autowired
	private Gson gson;
	
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping( "/transactions" )
	@ResponseBody
	public ResponseEntity<String> insertTransaction( @RequestBody String reqstBody ) {
		
		log.debug( "post transaction get called with request body {}", reqstBody );

		Transaction transaction = gson.fromJson( reqstBody, Transaction.class );
		Transaction transactionInserted = transactionService.insert( transaction );
		
		if( transactionInserted == null ) {
			
			log.debug( "Sending HTTP Response No Content" );
			return new ResponseEntity<>( HttpStatus.NO_CONTENT );
		}
		
		log.debug( "Transaction is recorded, sending HTTP Response Created" );
		return new ResponseEntity<String>( HttpStatus.CREATED );
	}
}
