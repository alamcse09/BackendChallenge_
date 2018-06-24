package com.n26.challenge.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.challenge.data.TransactionDataStore;
import com.n26.challenge.time.Time;
import com.n26.challenge.transaction.OutDatedTransactionException;
import com.n26.challenge.transaction.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private Time time;
	
	@Autowired
	private TransactionDataStore dataStore;
	
	public Transaction insert( Transaction transaction ) throws OutDatedTransactionException {
		
		assertNotOldTransaction( transaction.getTimestamp() );
		Transaction transactionInserted = dataStore.insert( transaction );
		
		log.debug( "Transaction stored {}", transactionInserted );
		return transactionInserted;
	}

	private void assertNotOldTransaction( long timestamp ) throws OutDatedTransactionException {
		
		if( time.isOutdatedTransaction( timestamp ) ) {
			
			log.debug( "Older than 60 seconds, Not inserted" );
			throw new OutDatedTransactionException( "Transaction is old" );
		}
	}
}
