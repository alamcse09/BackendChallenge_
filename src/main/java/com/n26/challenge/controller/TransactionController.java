package com.n26.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.n26.challenge.transaction.OutDatedTransactionException;
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
	@ResponseStatus( HttpStatus.CREATED )
	public void insertTransaction( @RequestBody String reqstBody ) throws OutDatedTransactionException {
		
		log.debug( "post transaction get called with request body {}", reqstBody );
			
		Transaction transaction = gson.fromJson( reqstBody, Transaction.class );
		transactionService.insert( transaction );
		
		log.debug( "Transaction is recorded, sending HTTP Response Created" ); 

	}
	
	@ExceptionHandler( OutDatedTransactionException.class )
    @ResponseStatus( HttpStatus.NO_CONTENT )
    public void handleException( OutDatedTransactionException ex ) {
        
		log.debug( "Sending HTTP Response No Content" );
	}
}
