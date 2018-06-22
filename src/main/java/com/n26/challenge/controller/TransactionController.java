package com.n26.challenge.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.n26.challenge.Transaction;

@RestController
public class TransactionController {

	@Autowired
	private Gson gson;
	
	@PostMapping( "/transactions" )
	@ResponseBody
	public ResponseEntity<String> insertTransaction( @RequestBody String reqstBody ) {
		
		Transaction transaction = gson.fromJson( reqstBody, Transaction.class );
		
		System.out.println( transaction );
		
		Long timeDiff = Math.abs( transaction.getTimestamp() - System.currentTimeMillis() );
		
		if( timeDiff > 60 )
			return new ResponseEntity<>( HttpStatus.NO_CONTENT );
		
		return new ResponseEntity<String>( HttpStatus.CREATED );
	}
}
