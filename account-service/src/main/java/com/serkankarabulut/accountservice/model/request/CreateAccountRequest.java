package com.serkankarabulut.accountservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CreateAccountRequest implements Serializable {
    @NotNull
    private Long customerId;
    private BigDecimal initialCredit;

}
