package com.serkankarabulut.transactionservice.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class TransactionResponse {
    private Long accountId;
    private List<BigDecimal> transactionList=new ArrayList<>();
}
