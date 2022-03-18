package com.serkankarabulut.transactionservice.service;

import com.serkankarabulut.transactionservice.model.entity.Transaction;
import com.serkankarabulut.transactionservice.model.request.CreateTransactionRequest;
import com.serkankarabulut.transactionservice.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    List<TransactionResponse> getTransactionHistoryByCustomerId(Long customerId);

}
