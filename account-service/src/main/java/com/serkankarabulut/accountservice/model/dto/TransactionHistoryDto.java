package com.serkankarabulut.accountservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionHistoryDto implements Serializable {
    private Long accountId;
    private List<BigDecimal> transactionList;
}
