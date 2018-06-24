package com.n26.challenge.data;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.n26.challenge.config.Config;
import com.n26.challenge.statistics.Statistics;
import com.n26.challenge.time.Time;
import com.n26.challenge.transaction.Transaction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TransactionDataStore {

	private final Statistics[] statistics;
	private final Object[] locks;

	private final Statistics cachedStatistics;
	private final Object cachedTransactionStatisticsLock;
	
	private Time time;
	
	@Autowired
	public TransactionDataStore( Time time ) {
		
		this.time = time;
		
		statistics = new Statistics[ Config.SECONDS_TO_CONSIDER ];
		
		locks = new Object[ Config.SECONDS_TO_CONSIDER ];
		
		cachedStatistics = new Statistics( time );
		
		cachedTransactionStatisticsLock = new Object();
		
		initializeLocks();
	}
	
	public void reset() {
		
		Arrays.fill( statistics, null );
		cachedStatistics.reset();
	}
	
	private void initializeLocks() {
		
		for (int i = 0; i < locks.length; i++) {
			
			locks[i] = new Object();
		}
	}

	public Transaction insert( Transaction transaction ) {
		
		final int index = getTransactionStatisticsIndexForTimestamp( transaction.getTimestamp() );
		
		synchronized ( locks[index] ) {
			
			statistics[index] = new Statistics( time, transaction, statistics[index] );
		}
		
		return transaction;
	}
	
	private int getTransactionStatisticsIndexForTimestamp( Long timestamp ) {
		
		return (int) (( timestamp / 1000 ) % statistics.length);
	}

	public Statistics getStats() {
		
		final long timestamp = time.getCurrentTimestamp();
		
		log.debug( "Get stats called. Cachestat timestamp: {}, currentTimestamp: {}. timediff: {}", cachedStatistics.getTimestamp(), timestamp, cachedStatistics.getTimestamp()-timestamp );
		
		if ( cachedStatistics.getTimestamp() < timestamp ) {
			
			log.debug( "Cache stats has lower timestamp. So re calculate" );
			
			synchronized ( cachedTransactionStatisticsLock ) {
				
				if ( cachedStatistics.getTimestamp() < timestamp ) {
					
					log.debug( "cacheStat reset" );
					
					cachedStatistics.reset();
					
					for ( int i = 0; i < statistics.length; i++ ) {
						
						log.debug( "Merging stats {}", statistics[i] );
						
						cachedStatistics.mergeTransactionStatistics( statistics[i] );
					}
					cachedStatistics.setTimestamp( timestamp );
				}
			}
		}
		return cachedStatistics;
	}
	
}
