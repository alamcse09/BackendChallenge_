package com.n26.challenge.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.n26.challenge.transaction.Transaction;

import lombok.Data;

@Data
public class Statistics {

	private Double sum;
	private Double avg;
	private Double max;
	private Double min;
	private Integer count;
	
	@JsonIgnore
	private Long timestamp;
	
	public Statistics() {
		
		reset();
	}
	
	public Statistics( final Transaction transaction, final Statistics otherStatistics ) {
		
		this.timestamp = transaction.getTimestamp();
		this.sum = transaction.getAmount();
		this.count = 1;
		this.avg = transaction.getAmount();
		this.max = transaction.getAmount();
		this.min = transaction.getAmount();
		mergeTransactionStatistics( otherStatistics );
	}

	public void mergeTransactionStatistics( Statistics otherStatistics ) {
		
		if( otherStatistics == null )
			return;
		
		this.avg = (( otherStatistics.avg * otherStatistics.count ) + ( this.avg * this.count ) ) / ( otherStatistics.count + this.count );
		this.sum = otherStatistics.sum + this.sum;
		this.count = otherStatistics.count + this.count;
		this.max = Math.max(this.max, otherStatistics.max);
		this.min = Math.min(this.min, otherStatistics.min);
	}

	public void reset() {
		
		this.sum = 0.0;
		this.avg = 0.0;
		this.max = (double) Long.MIN_VALUE;
		this.min = (double) Long.MAX_VALUE;
		this.count = 0;
		this.timestamp = 0L;
	}
}
