package com.n26.challenge.transaction.service;

import org.springframework.stereotype.Service;

import com.n26.challenge.transaction.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService{

	public Transaction insert( Transaction transaction ) {
		
		Long timeDiff = Math.abs( transaction.getTimestamp() - System.currentTimeMillis() );
		if( timeDiff > 60*1000 ) {
			
			log.debug( "Older than 60 seconds, Not inserted" );
			return null;
		}
		
		return transaction;
	}
}
