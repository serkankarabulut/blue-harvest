package com.serkankarabulut.accountservice.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
public class CustomerTransactionInfoDto implements Serializable {
    private List<TransactionHistoryDto> transactionHistory;
    private BigDecimal balance;
}
