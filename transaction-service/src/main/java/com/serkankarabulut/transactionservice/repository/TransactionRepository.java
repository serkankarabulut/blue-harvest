package com.serkankarabulut.transactionservice.repository;

import com.serkankarabulut.transactionservice.model.entity.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TransactionRepository {
    private Map<Long, List<Transaction>> transactionMap = new HashMap<>();

    public Transaction addTransaction(Transaction transaction){
       if(transactionMap.containsKey(transaction.getCustomerId())){
            List<Transaction> transactions = transactionMap.get(transaction.getCustomerId());
            transactions.add(transaction);
            transactionMap.put(transaction.getCustomerId(), transactions);
            return transaction;
        }
        return null;
    }

    public List<Transaction> findTransactionByCustomerId(Long customerId){
        return transactionMap.getOrDefault(customerId, null);
    }

    public void save(Long customerId){
        transactionMap.put(customerId, new ArrayList<>());
    }

}
