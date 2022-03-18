package com.serkankarabulut.accountservice.mapper;

import com.serkankarabulut.accountservice.model.entity.Customer;
import com.serkankarabulut.accountservice.model.request.CreateCustomerRequest;
import com.serkankarabulut.accountservice.model.response.AccountInfoResponse;
import com.serkankarabulut.accountservice.model.response.CustomerInfoResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class ModelMapper {
    public AccountInfoResponse toAccountInfoResponse(Customer customer) {
        return AccountInfoResponse.builder()
                .customerId(customer.getCustomerId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .build();
    }

    public Customer toCustomer(CreateCustomerRequest request) {
        return Customer.builder()
                .name(request.getName())
                .surname(request.getSurname())
                .accountList(new ArrayList<>())
                .build();
    }

    public CustomerInfoResponse toCustomerInfoResponse(Customer savedCustomer) {
        return CustomerInfoResponse.builder()
                .customerId(savedCustomer.getCustomerId())
                .name(savedCustomer.getName())
                .surname(savedCustomer.getSurname())
                .build();
    }
}
