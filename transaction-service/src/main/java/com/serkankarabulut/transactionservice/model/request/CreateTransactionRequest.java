package com.serkankarabulut.transactionservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateTransactionRequest {
    @NotNull
    private Long customerId;
    @NotNull
    private Long accountId;
    @NotNull
    private BigDecimal credit;
}
