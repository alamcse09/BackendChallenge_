package com.n26.challenge.data;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.n26.challenge.statistics.Statistics;
import com.n26.challenge.transaction.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
public class TransactionDataStoreTests {

	@Autowired
	private TransactionDataStore dataStore;
	
	@Before
	public void setUp() throws Exception {
		
		dataStore.reset();
	}

	@Test
	public void testEmptyStatsWithNothingPosted() {
		
		Statistics stat = dataStore.getStats();
		assertFalse( stat.hasData() );
	}
	
	@Test
	public void testSameDataWithOneTransaction() {
		
		final Transaction transaction = new Transaction( Math.random(), System.currentTimeMillis() );
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
		
		double sum = 0;
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		
		for (int i = 0; i < 10; i++) {
			
			final double amount = Math.random() * 1000;
			
			sum += amount;
			
			max = Math.max(max, amount);
			min = Math.min(min, amount);
			
			dataStore.insert( new Transaction( amount, System.currentTimeMillis() ) );
		}
		
		final double average = sum / 10;
		
		final Statistics stats = dataStore.getStats();
		
		assertEquals( stats.getSum(), sum, 0.00001 );
		assertEquals( stats.getAvg(), average, 0.00001 );
		assertEquals( stats.getMin(), min, 0.00001 );
		assertEquals( stats.getMax(), max, 0.00001 );
		assertEquals( stats.getCount(), 10, 0 );
	}
	
	@Test
	public void testEmptyDataAfterPassingOneMinute() throws InterruptedException {
		
		for (int i = 0; i < 10; i++) {
			
			dataStore.insert( new Transaction( Math.random(), System.currentTimeMillis() ) );
		}
		
		Thread.sleep( 70000 );
		Statistics stats = dataStore.getStats();
		assertFalse( stats.hasData() );
	}

}
