package com.serkankarabulut.transactionservice.service.impl;

import com.serkankarabulut.transactionservice.constant.TransactionConstants;
import com.serkankarabulut.transactionservice.model.dto.CreateCustomerDto;
import com.serkankarabulut.transactionservice.model.entity.Transaction;
import com.serkankarabulut.transactionservice.model.response.TransactionResponse;
import com.serkankarabulut.transactionservice.repository.TransactionRepository;
import com.serkankarabulut.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    @Override
    public List<TransactionResponse> getTransactionHistoryByCustomerId(Long customerId) {
        List<Transaction> transactionList = transactionRepository.findTransactionByCustomerId(customerId);
        if(Objects.isNull(transactionList)){
            return Collections.emptyList();
        }
        Map<Long, List<Transaction>> transactionsGroupByAccountId = transactionList.stream().collect(Collectors.groupingBy(Transaction::getAccountId));
        List<TransactionResponse> response = new ArrayList<>();
        transactionsGroupByAccountId.forEach((k, v) -> {
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setAccountId(k);
            for (Transaction transaction : v) {
                transactionResponse.getTransactionList().add(transaction.getCredit());
            }
            response.add(transactionResponse);
        });
        return response;
    }

    @KafkaListener(
            topics = TransactionConstants.NEW_TRANSACTION_TOPIC,
            containerFactory = "kafkaListenerContainerFactory",
            groupId = TransactionConstants.GROUP_ID)
    private void kafkaNewTransactionListener(Transaction transaction) {
        if(Objects.isNull(transaction)){
            return;
        }
        transactionRepository.addTransaction(transaction);
    }

    @KafkaListener(
            topics = TransactionConstants.NEW_CUSTOMER_TOPIC,
            containerFactory = "kafkaListenerContainerFactory2",
            groupId = TransactionConstants.GROUP_ID)
    private void kafkaNewCustomerListener(CreateCustomerDto input){
        if(Objects.isNull(input)){
            return;
        }
        transactionRepository.save(input.getCustomerId());
    }
}
