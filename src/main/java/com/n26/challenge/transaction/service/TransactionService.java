package com.n26.challenge.transaction.service;

import com.n26.challenge.models.Transaction;
import com.n26.challenge.transaction.exception.OutDatedTransactionException;

public interface TransactionService {

	Transaction insert( Transaction transaction ) throws OutDatedTransactionException;
}
