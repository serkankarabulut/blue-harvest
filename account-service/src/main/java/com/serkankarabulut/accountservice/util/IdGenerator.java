package com.serkankarabulut.accountservice.util;

import org.springframework.stereotype.Component;

@Component
public class IdGenerator {

    private Long ACCOUNT_ID_GENERATOR = 1L;
    private Long CUSTOMER_ID_GENERATOR = 1L;

    public Long generateAccountId() {
        return ACCOUNT_ID_GENERATOR++;
    }

    public Long generateCustomerId() {
        return CUSTOMER_ID_GENERATOR++;
    }

}
