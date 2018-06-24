package com.n26.challenge.time;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith( SpringRunner.class )
@SpringBootTest
public class TimeTests {

	@Autowired
	private Time time;
	
	@Test
	public void testValidateTime() {
		
		log.debug( "Validate time test called" );
		
		assertFalse( time.isOutdatedTransaction( time.getCurrentTimestamp() ) );
		assertFalse( time.isOutdatedTransaction( time.lastValidTransactionTime() ) );
		assertFalse( time.isOutdatedTransaction( time.getValidTimestamp() ) );
		
		assertTrue( time.isOutdatedTransaction( time.getInvalidTimestamp() ) );
		assertTrue( time.isOutdatedTransaction( time.lastValidTransactionTime() - 1 ) );
	}
	
	@Test
	public void testTimeJump() {
		
		time.stopTime();
		
		long timestampBeforeJump = time.getCurrentTimestamp();
		time.goToFuture( 60000 );
		
		long timestampAfterJump = time.getCurrentTimestamp();
		
		assertEquals( timestampAfterJump, timestampBeforeJump + 60000 );
		
		time.resumeTime();
	}

	@Test
	public void testTimeStop() throws InterruptedException {
		
		time.stopTime();
		
		long initialTime = time.getCurrentTimestamp();
		Thread.sleep(10);
		long timeAfterFirstSleep = time.getCurrentTimestamp();
		
		time.resumeTime();
		
		Thread.sleep(10);
		long timeAfterSecondSleep = time.getCurrentTimestamp();
		
		assertEquals( initialTime, timeAfterFirstSleep );
		assertNotEquals( initialTime, timeAfterSecondSleep );
	}
}
