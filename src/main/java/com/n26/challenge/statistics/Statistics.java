package com.n26.challenge.statistics;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.n26.challenge.time.Time;
import com.n26.challenge.transaction.Transaction;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class Statistics {

	private double sum;
	private double avg;
	private double max;
	private double min;
	private long count;
	
	@JsonIgnore
	private final Time time;
	
	@JsonIgnore
	private long timestamp;
	
	public Statistics( Time time ) {
		
		this.time = time;
		reset();
	}
	
	public Statistics( Time time, final Transaction transaction, final Statistics otherStatistics ) {
		
		this.time = time;
		this.timestamp = transaction.getTimestamp();
		this.sum = transaction.getAmount();
		this.count = 1;
		this.avg = transaction.getAmount();
		this.max = transaction.getAmount();
		this.min = transaction.getAmount();
		mergeTransactionStatistics( otherStatistics );
	}

	public void mergeTransactionStatistics( Statistics otherStatistics ) {
		
		if( mergeable( otherStatistics ) ) {
			
			this.avg = ( ( otherStatistics.avg * otherStatistics.count ) + ( this.avg * this.count ) ) / ( otherStatistics.count + this.count );
			this.sum = otherStatistics.sum + this.sum;
			this.count = otherStatistics.count + this.count;
			this.max = Math.max( this.max, otherStatistics.max );
			this.min = Math.min( this.min, otherStatistics.min );
		}
		else {
			
			log.debug( "Not mergable {}", otherStatistics );
		}
	}

	private boolean mergeable( final Statistics otherStatistics ) {
		
		return 	otherStatistics != null 
				&& !time.isOutdatedTransaction( otherStatistics.getTimestamp() )
				&& otherStatistics.hasData();
	}

	public void reset() {
		
		this.sum = 0.0;
		this.avg = 0.0;
		this.max = Long.MIN_VALUE;
		this.min = Long.MAX_VALUE;
		this.count = 0;
		this.timestamp = 0L;
	}
	
	public boolean hasData() {
		
		return count > 0;
	}
}
