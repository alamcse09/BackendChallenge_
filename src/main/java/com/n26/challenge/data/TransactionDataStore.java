package com.n26.challenge.data;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.n26.challenge.statistics.Statistics;
import com.n26.challenge.transaction.Transaction;

@Component
public class TransactionDataStore {

	private final Statistics[] statistics;
	private final Object[] locks;

	private final Statistics cachedStatistics;
	private final Object cachedTransactionStatisticsLock;
	
	public TransactionDataStore() {
		
		statistics = new Statistics[ 60000 / 1000 ];
		
		locks = new Object[statistics.length];
		
		cachedStatistics = new Statistics();
		
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
			
			statistics[index] = new Statistics( transaction, statistics[index] );
		}
		
		return transaction;
	}
	
	private int getTransactionStatisticsIndexForTimestamp( Long timestamp ) {
		
		return (int) (( timestamp / 1000 ) % statistics.length);
	}

	public Statistics getStats() {
		
		final long timestamp = System.currentTimeMillis();
		
		if ( cachedStatistics.getTimestamp() < timestamp ) {
			
			synchronized ( cachedTransactionStatisticsLock ) {
				
				if ( cachedStatistics.getTimestamp() < timestamp) {
					
					cachedStatistics.reset();
					
					for ( int i = 0; i < statistics.length; i++ ) {
						
						cachedStatistics.mergeTransactionStatistics( statistics[i] );
					}
					cachedStatistics.setTimestamp( timestamp );
				}
			}
		}
		return cachedStatistics;
	}
	
}
