package com.n26.challenge.transaction.exception;

public class OutDatedTransactionException extends Exception {

	private static final long serialVersionUID = 1L;

	public OutDatedTransactionException( String msg ) {
		
		super( msg );
	}
}
