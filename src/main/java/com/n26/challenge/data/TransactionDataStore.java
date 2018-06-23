package com.n26.challenge.data;

import org.springframework.stereotype.Component;

import com.n26.challenge.statistics.Statistics;
import com.n26.challenge.transaction.Transaction;

@Component
public class TransactionDataStore {

	private Double sum = 0.0;
	
	public Transaction insert( Transaction transaction ) {
		
		sum += transaction.getAmount();
		return transaction;
	}
	
	public Statistics getStats() {
		
		Statistics stats = new Statistics();
		stats.setSum( sum );
		
		return stats;
	}
}
