package com.serkankarabulut.accountservice.model.response;

import com.serkankarabulut.accountservice.model.dto.TransactionHistoryDto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class AccountInfoResponse {
    private Long customerId;
    private String name;
    private String surname;
    private BigDecimal balance;
    private List<TransactionHistoryDto> transactionHistory;
}
