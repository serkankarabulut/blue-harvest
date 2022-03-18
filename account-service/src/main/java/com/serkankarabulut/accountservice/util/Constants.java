package com.serkankarabulut.accountservice.util;

import org.springframework.beans.factory.annotation.Value;

public class Constants {
    @Value("${account-service.get-transaction-history-url}")
    public static String GET_TRANSACTION_HISTORY_URI;
    public static final String NEW_TRANSACTION_TOPIC = "new_transaction_topic";
    public static final String NEW_CUSTOMER_TOPIC = "new_customer_topic";
}
