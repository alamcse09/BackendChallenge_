package com.n26.challenge.transaction.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.challenge.data.TransactionDataStore;
import com.n26.challenge.transaction.OutDatedTransactionException;
import com.n26.challenge.transaction.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService{

	@Autowired
	private TransactionDataStore dataStore;
	
	public Transaction insert( Transaction transaction ) throws OutDatedTransactionException {
		
		assertNotOldTransaction( transaction );
		return dataStore.insert( transaction );
	}

	private void assertNotOldTransaction(Transaction transaction) throws OutDatedTransactionException {
		
		Long timeDiff = Math.abs( transaction.getTimestamp() - System.currentTimeMillis() );
		if( timeDiff > 60*1000 ) {
			
			log.debug( "Older than 60 seconds, Not inserted" );
			throw new OutDatedTransactionException( "Transaction is old" );
		}
	}
}
