package com.serkankarabulut.transactionservice.controller;

import com.serkankarabulut.transactionservice.model.response.TransactionResponse;
import com.serkankarabulut.transactionservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/{customerId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(@PathVariable @NotNull Long customerId){
        return ResponseEntity.ok(transactionService.getTransactionHistoryByCustomerId(customerId));
    }

}
