package com.serkankarabulut.accountservice.service.impl;

import com.serkankarabulut.accountservice.model.dto.CreateCustomerDto;
import com.serkankarabulut.accountservice.model.dto.TransactionDto;
import com.serkankarabulut.accountservice.model.request.CreateCustomerRequest;
import com.serkankarabulut.accountservice.model.response.CustomerInfoResponse;
import com.serkankarabulut.accountservice.util.Constants;
import com.serkankarabulut.accountservice.util.NumberUtil;
import com.serkankarabulut.accountservice.mapper.ModelMapper;
import com.serkankarabulut.accountservice.model.dto.CustomerTransactionInfoDto;
import com.serkankarabulut.accountservice.model.dto.TransactionHistoryDto;
import com.serkankarabulut.accountservice.model.entity.Customer;
import com.serkankarabulut.accountservice.model.request.CreateAccountRequest;
import com.serkankarabulut.accountservice.model.response.AccountInfoResponse;
import com.serkankarabulut.accountservice.repository.AccountRepository;
import com.serkankarabulut.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final RestTemplate restTemplate;

    @Value("${account-service.get-transaction-history-url}")
    public String GET_TRANSACTION_HISTORY_URI;

    @Override
    public Long createAccount(CreateAccountRequest request) {
        Long accountId = accountRepository.createAccount(request.getCustomerId());
        if (Objects.nonNull(request.getInitialCredit()) && NumberUtil.isNotEqual(request.getInitialCredit(), BigDecimal.ZERO)) {
            kafkaTemplate.send(Constants.NEW_TRANSACTION_TOPIC, new TransactionDto(request.getCustomerId(), accountId, request.getInitialCredit()));
        }
        return accountId;
    }

    @Override
    public AccountInfoResponse getAccountInfo(Long customerId) {
        Customer customer = accountRepository.findById(customerId);
        if(Objects.isNull(customer)){
            return null;
        }
        AccountInfoResponse accountInfoResponse = modelMapper.toAccountInfoResponse(customer);

        CustomerTransactionInfoDto accountTransactionDto = getAccountTransactionDto(customerId);
        if(Objects.nonNull(accountTransactionDto)){
            accountInfoResponse.setTransactionHistory(accountTransactionDto.getTransactionHistory());
            accountInfoResponse.setBalance(accountTransactionDto.getBalance());
        }

        return accountInfoResponse;
    }

    @Override
    public List<AccountInfoResponse> getAllAccountInfos() {
        List<AccountInfoResponse> accountInfoResponseList = accountRepository.findAll().stream().map(modelMapper::toAccountInfoResponse).collect(Collectors.toList());
        for (AccountInfoResponse accountInfoResponse : accountInfoResponseList) {
            CustomerTransactionInfoDto accountTransactionDto = getAccountTransactionDto(accountInfoResponse.getCustomerId());
            if(Objects.nonNull(accountTransactionDto)){
                accountInfoResponse.setTransactionHistory(accountTransactionDto.getTransactionHistory());
                accountInfoResponse.setBalance(accountTransactionDto.getBalance());
            }
        }
        return accountInfoResponseList;
    }

    @Override
    public CustomerInfoResponse createCustomer(CreateCustomerRequest request) {
        Customer customer = modelMapper.toCustomer(request);
        Customer savedCustomer = accountRepository.save(customer);
        kafkaTemplate.send(Constants.NEW_CUSTOMER_TOPIC, new CreateCustomerDto(savedCustomer.getCustomerId()));
        return modelMapper.toCustomerInfoResponse(savedCustomer);
    }

    @Override
    public void generateMockData() {
        String[] nameList = {"Adam", "Chris", "Alex", "Sasha"};
        String[] surnameList = {"Ondra", "Sharma", "Honnold", "DiGiulian"};
        for (int i = 0; i < 4; i++) {
            Customer customer = Customer.builder()
                    .name(nameList[i])
                    .surname(surnameList[i])
                    .accountList(new ArrayList<>())
                    .build();
            Customer savedCustomer = accountRepository.save(customer);
            kafkaTemplate.send(Constants.NEW_CUSTOMER_TOPIC, new CreateCustomerDto(savedCustomer.getCustomerId()));
        }
        createAccount(new CreateAccountRequest(1L, BigDecimal.valueOf(25)));
        createAccount(new CreateAccountRequest(1L, BigDecimal.valueOf(4)));
        createAccount(new CreateAccountRequest(2L, BigDecimal.valueOf(12)));
        createAccount(new CreateAccountRequest(2L, BigDecimal.valueOf(-3)));
    }

    private CustomerTransactionInfoDto getAccountTransactionDto(Long customerId){
        String url = GET_TRANSACTION_HISTORY_URI + customerId;
        TransactionHistoryDto[] transactionHistoryDtos;
        CustomerTransactionInfoDto customerTransactionInfoDto = null;
        try {
            transactionHistoryDtos = restTemplate.getForObject(url, TransactionHistoryDto[].class);
        }catch (Exception e){
            log.error("Rest request failed.");
            transactionHistoryDtos = null;
        }
        if(Objects.nonNull(transactionHistoryDtos)){
            customerTransactionInfoDto = new CustomerTransactionInfoDto();

            List<TransactionHistoryDto> transactionHistoryDtoList = Arrays.asList(transactionHistoryDtos);
            customerTransactionInfoDto.setTransactionHistory(transactionHistoryDtoList);

            BigDecimal balance = transactionHistoryDtoList.stream().flatMap(e -> e.getTransactionList().stream()).reduce(BigDecimal.ZERO, BigDecimal::add);
            customerTransactionInfoDto.setBalance(balance);
        }
        return customerTransactionInfoDto;
    }
}
