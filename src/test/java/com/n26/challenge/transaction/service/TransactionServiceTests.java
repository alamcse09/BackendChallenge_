package com.n26.challenge.transaction.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.challenge.config.Config;
import com.n26.challenge.data.TransactionDataStore;
import com.n26.challenge.models.Statistics;
import com.n26.challenge.models.Transaction;
import com.n26.challenge.time.Time;
import com.n26.challenge.transaction.exception.OutDatedTransactionException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
public class TransactionServiceTests {

	private static final double EPSILON = 0.0001;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private Time time;
	
	@Autowired
	private TransactionDataStore dataStore;
	
	@Before
	public void setUp() throws Exception {
		
		dataStore.reset();
	}
	
	@Test( expected = OutDatedTransactionException.class )
	public void testExceptionUponInvalidTimestampInsert() throws OutDatedTransactionException {
		
		log.debug( "Trying to insert a transaction with 70 sec earlier time" );
		Transaction transaction = new Transaction( Math.random() * 1000, time.getInvalidTimestamp() );
		transactionService.insert( transaction );
	}
	
	@Test
	public void testForMultipleTransactionIncludingInvalids() throws OutDatedTransactionException {
		
		time.stopTime();
		
		double sum = 0;
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		long count = 0;
		
		for (int i = 0; i < Config.NUM_OF_TRANSACTIONS_FOR_TEST; i++) {
			
			final double amount = Math.random() * 1000;
			
			if ( Math.random() < 0.5D ) {
				
				try {
					
					transactionService.insert( new Transaction( amount, time.getInvalidTimestamp() ) );
				}
				catch ( OutDatedTransactionException e) {}
			}
			else {
				
				count++;
				sum += amount;
				max = Math.max(max, amount);
				min = Math.min(min, amount);
				transactionService.insert( new Transaction( amount, time.getValidTimestamp() ) );
				
				log.debug( "Transaction inserted at Time: {}, Sum: {}, count: {}, max: {}, min: {}", new Date( time.getCurrentTimestamp() ), sum, count, max, min );
			}
		}
		
		final double average = sum / count;
		
		final Statistics statistics = dataStore.getStats();
		
		log.debug( "Stats - {}", statistics );
		log.debug( "Diff {}", Math.abs( sum - statistics.getSum() ) );
		
		assertEquals( sum, statistics.getSum(), EPSILON );
		assertEquals( average, statistics.getAvg(), EPSILON );
		assertEquals( min, statistics.getMin(), EPSILON );
		assertEquals( max, statistics.getMax(), EPSILON );
		assertEquals( count, statistics.getCount(), 0 );
		
		log.debug( "Stopped time {}", new Date( time.getCurrentTimestamp() ) );
		
		time.resumeTime();
		
		log.debug( "Real time {}", new Date( time.getCurrentTimestamp() ) );
	}
}
