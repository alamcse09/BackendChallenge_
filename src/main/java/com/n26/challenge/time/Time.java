package com.n26.challenge.time;

import com.n26.challenge.config.Config;

public class Time {

	private long timeOffset = 0;
	private long stoppedTime = 0;
	private boolean isTimeStopped = false;


	public long lastValidTransactionTime() {
		
		return getCurrentTimestamp() - Config.SECONDS_TO_CONSIDER * Config.SECOND_TO_MILLISECOND_MULTIPLIER;
	}

	public boolean isOutdatedTransaction( final long timestamp )
	{
		return timestamp < lastValidTransactionTime();
	}

	public boolean isSameSeconds( final long timestamp1, final long timestamp2 ) {
		
		final long lump1 = timestamp1 / Config.SECOND_TO_MILLISECOND_MULTIPLIER;
		final long lump2 = timestamp2 / Config.SECOND_TO_MILLISECOND_MULTIPLIER;
		return lump1 == lump2;
	}
	
	public long getCurrentTimestamp()
	{
		if ( isTimeStopped ) {
			
			return stoppedTime + timeOffset;
		}
		else {
			
			return System.currentTimeMillis() + timeOffset;
		}
	}

	public long getValidTimestamp() {
		
		return lastValidTransactionTime() + (long) ( Math.random() * ( Config.SECONDS_TO_CONSIDER * Config.SECOND_TO_MILLISECOND_MULTIPLIER ) );
	}

	public long getInvalidTimestamp() {
		
		return lastValidTransactionTime() - (long) ( Math.random() * Config.TEN_DAYS_IN_SECOND * Config.SECOND_TO_MILLISECOND_MULTIPLIER ) - 1000;
	}

	public void goToFuture( long milliSec ) {
		
		timeOffset += milliSec;
	}
	
	public void goToPast( long milliSec ) {
		
		timeOffset -= milliSec;
	}
	
	public void resetToPresentTime() {
		
		timeOffset = 0;
	}

	public void stopTime() {
		
		isTimeStopped = false;
		stoppedTime = getCurrentTimestamp();
		isTimeStopped = true;
	}

	public void resumeTime() {
		
		isTimeStopped = false;
	}
}
