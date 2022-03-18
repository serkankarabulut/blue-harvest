package com.serkankarabulut.accountservice.repository;

import com.serkankarabulut.accountservice.model.entity.Customer;
import com.serkankarabulut.accountservice.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class AccountRepository {

    private Map<Long, Customer> customerMap = new HashMap<>();
    private final IdGenerator idGenerator;

    public Long createAccount(Long customerId) {
        if (customerMap.containsKey(customerId)) {
            Long accountId = idGenerator.generateAccountId();
            customerMap.get(customerId).getAccountList().add(accountId);
            return accountId;
        }
        return null;
    }

    public Customer findById(Long customerId) {
        return customerMap.get(customerId);
    }

    public Collection<Customer> findAll(){
        return customerMap.values();
    }

    public Customer save(Customer customer) {
        customer.setCustomerId(idGenerator.generateCustomerId());
        customerMap.put(customer.getCustomerId(), customer);
        return customer;
    }

}
