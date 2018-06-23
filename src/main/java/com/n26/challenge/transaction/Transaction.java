package com.n26.challenge.transaction;

import lombok.Data;

@Data
public class Transaction {

	private Double amount;
	private Long timestamp;
	
	public Transaction() {}
	
	public Transaction( Double amount, Long timestamp ) {
		
		this.amount = amount;
		this.timestamp = timestamp;
	}
}
