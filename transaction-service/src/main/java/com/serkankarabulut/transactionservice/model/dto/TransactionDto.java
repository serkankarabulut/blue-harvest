package com.serkankarabulut.transactionservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class TransactionDto implements Serializable {

    private Long customerId;
    private Long accountId;
    private BigDecimal initialCredit;
}
