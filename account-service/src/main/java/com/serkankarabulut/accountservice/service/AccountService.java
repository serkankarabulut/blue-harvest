package com.serkankarabulut.accountservice.service;

import com.serkankarabulut.accountservice.model.request.CreateAccountRequest;
import com.serkankarabulut.accountservice.model.request.CreateCustomerRequest;
import com.serkankarabulut.accountservice.model.response.AccountInfoResponse;
import com.serkankarabulut.accountservice.model.response.CustomerInfoResponse;

import java.util.List;

public interface AccountService {
    Long createAccount(CreateAccountRequest request);

    AccountInfoResponse getAccountInfo(Long customerId);

    List<AccountInfoResponse> getAllAccountInfos();

    CustomerInfoResponse createCustomer(CreateCustomerRequest request);

    void generateMockData();
}
