package com.n26.challenge.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.challenge.config.Config;
import com.n26.challenge.statistics.Statistics;
import com.n26.challenge.time.Time;
import com.n26.challenge.transaction.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
public class TransactionDataStoreTests {

	private static final double EPSILON = 0.0001;

	@Autowired
	private Time time;
	
	@Autowired
	private TransactionDataStore dataStore;
	
	@Before
	public void setUp() throws Exception {
		
		dataStore.reset();
	}

	@Test
	public void testEmptyStatsWithNothingPosted() {
		
		log.debug( "Testing with no transaction" );
		Statistics stat = dataStore.getStats();
		assertFalse( stat.hasData() );
	}
	
	@Test
	public void testSameDataWithOneTransaction() {
		
		log.debug( "Testing with one transaction" );
		final Transaction transaction = new Transaction( Math.random(), time.getCurrentTimestamp() );
		
		dataStore.insert( transaction );
		
		Statistics stat = dataStore.getStats();
		
		assertEquals( transaction.getAmount(), stat.getSum(), 0 );
		assertEquals( transaction.getAmount(), stat.getAvg(), 0 );
		assertEquals( transaction.getAmount(), stat.getMax(), 0 );
		assertEquals( transaction.getAmount(), stat.getMin(), 0 );
		assertEquals( 1L, stat.getCount(), 0 );
	}
	
	@Test
	public void testDataStoreForMultipleValidTransaction() {
		
		log.debug( "Testing with multiple valid transaction" );
		
		double sum = 0;
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		
		for (int i = 0; i < Config.NUM_OF_TRANSACTIONS_FOR_TEST; i++) {
			
			final double amount = Math.random() * 1000;
			
			sum += amount;
			
			max = Math.max(max, amount);
			min = Math.min(min, amount);
			
			dataStore.insert( new Transaction( amount, time.getValidTimestamp() ) );
		}
		
		final double average = sum / Config.NUM_OF_TRANSACTIONS_FOR_TEST;
		
		log.debug( "Total {} num of transaction inserted. Sum: {}, Max: {}, Min: {}, Avg: {}", Config.NUM_OF_TRANSACTIONS_FOR_TEST, sum, max, min, average );
		
		final Statistics stats = dataStore.getStats();
		
		log.debug( "Stats got - {}", stats );
		
		assertEquals( stats.getSum(), sum, EPSILON );
		assertEquals( stats.getAvg(), average, EPSILON );
		assertEquals( stats.getMin(), min, EPSILON );
		assertEquals( stats.getMax(), max, EPSILON );
		assertEquals( stats.getCount(), Config.NUM_OF_TRANSACTIONS_FOR_TEST, 0 );
	}
	
	@Test
	public void testEmptyStatsAfterTwoMinutesOfLastTransaction() {
		
		log.debug( "Testing not stat after two minutes of transaction" );
		
		time.stopTime();
		
		for (int i = 0; i < Config.NUM_OF_TRANSACTIONS_FOR_TEST; i++) {
			
			dataStore.insert( new Transaction( Math.random(), time.getValidTimestamp() ) );
		}
		
		time.goToFuture( 120000 );
		
		Statistics stats = dataStore.getStats();
		
		log.debug( "Stat got after going to future {}", stats );
		
		time.resumeTime();
		time.resetToPresentTime();
		
		assertFalse( stats.hasData() );
	}
	
}
