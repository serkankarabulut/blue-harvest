package com.serkankarabulut.accountservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Customer {
    private Long customerId;
    private String name;
    private String surname;
    private List<Long> accountList;
}
