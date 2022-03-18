package com.serkankarabulut.transactionservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private Long customerId;
    private Long accountId;
    private BigDecimal credit;
}
