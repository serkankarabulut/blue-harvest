package com.serkankarabulut.transactionservice;

import com.serkankarabulut.transactionservice.model.entity.Transaction;
import com.serkankarabulut.transactionservice.model.response.TransactionResponse;
import com.serkankarabulut.transactionservice.repository.TransactionRepository;
import com.serkankarabulut.transactionservice.service.TransactionService;
import com.serkankarabulut.transactionservice.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class TransactionServiceImplTest {

    private TransactionService transactionService;

    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
        transactionService = new TransactionServiceImpl(transactionRepository);
    }

    @Test
    void whenGetTransactionHistoryByCustomerIdCalledWithValidCustomerId_itShouldReturnListOfTransactionResponse() {
        List<Transaction> transactionList = List.of(
                new Transaction(2L, 3L, BigDecimal.valueOf(4)),
                new Transaction(2L, 3L, BigDecimal.valueOf(-1)),
                new Transaction(2L, 4L, BigDecimal.valueOf(2))
        );
        List<TransactionResponse> transactionResponseList = new ArrayList<>();
        TransactionResponse transactionResponse1 = new TransactionResponse();
        transactionResponse1.setAccountId(3L);
        transactionResponse1.setTransactionList(List.of(
                BigDecimal.valueOf(4),
                BigDecimal.valueOf(-1)
        ));
        TransactionResponse transactionResponse2 = new TransactionResponse();
        transactionResponse2.setAccountId(4L);
        transactionResponse2.setTransactionList(List.of(
                BigDecimal.valueOf(2)
        ));
        transactionResponseList.add(transactionResponse1);
        transactionResponseList.add(transactionResponse2);

        Mockito.when(transactionRepository.findTransactionByCustomerId(2L)).thenReturn(transactionList);

        List<TransactionResponse> result = transactionService.getTransactionHistoryByCustomerId(2L);

        Assertions.assertEquals(result, transactionResponseList);
        Mockito.verify(transactionRepository).findTransactionByCustomerId(2L);
    }

    @Test
    void whenGetTransactionHistoryByCustomerIdCalledWithInvalidCustomerId_itShouldReturnEmptyList(){
        Mockito.when(transactionRepository.findTransactionByCustomerId(2L)).thenReturn(null);

        List<TransactionResponse> result = transactionService.getTransactionHistoryByCustomerId(2L);

        Assertions.assertEquals(result, Collections.emptyList());
    }
}