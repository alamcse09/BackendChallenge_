package com.n26.challenge.transaction.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.challenge.transaction.OutDatedTransactionException;
import com.n26.challenge.transaction.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
public class TransactionServiceTests {

	@Autowired
	private TransactionService transactionService;
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	@Test( expected = OutDatedTransactionException.class )
	public void testExceptionUponInvalidTimestampInsert() throws OutDatedTransactionException {
		
		log.debug( "Trying to insert a transaction with 70 sec earlier time" );
		
		Transaction transaction = new Transaction( Math.random()*1000, System.currentTimeMillis() - 70*1000 );
		transactionService.insert( transaction );
	}
}
