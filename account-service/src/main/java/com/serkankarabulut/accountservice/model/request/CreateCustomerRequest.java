package com.serkankarabulut.accountservice.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class CreateCustomerRequest implements Serializable {
    private String name;
    private String surname;
}
