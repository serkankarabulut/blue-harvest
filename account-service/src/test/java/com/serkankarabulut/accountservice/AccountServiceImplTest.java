package com.serkankarabulut.accountservice;

import com.serkankarabulut.accountservice.mapper.ModelMapper;
import com.serkankarabulut.accountservice.model.dto.CreateCustomerDto;
import com.serkankarabulut.accountservice.model.dto.TransactionDto;
import com.serkankarabulut.accountservice.model.dto.TransactionHistoryDto;
import com.serkankarabulut.accountservice.model.entity.Customer;
import com.serkankarabulut.accountservice.model.request.CreateAccountRequest;
import com.serkankarabulut.accountservice.model.request.CreateCustomerRequest;
import com.serkankarabulut.accountservice.model.response.AccountInfoResponse;
import com.serkankarabulut.accountservice.model.response.CustomerInfoResponse;
import com.serkankarabulut.accountservice.repository.AccountRepository;
import com.serkankarabulut.accountservice.service.AccountService;
import com.serkankarabulut.accountservice.service.impl.AccountServiceImpl;
import com.serkankarabulut.accountservice.util.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountServiceImplTest {

    private AccountService accountService;

    private AccountRepository accountRepository;
    private ModelMapper modelMapper;
    private KafkaTemplate kafkaTemplate;
    private RestTemplate restTemplate;

    @Value("${account-service.get-transaction-history-url}")
    public String GET_TRANSACTION_HISTORY_URI;

    @BeforeEach
    public void setup() {
        accountRepository = Mockito.mock(AccountRepository.class);
        modelMapper = Mockito.mock(ModelMapper.class);
        kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        restTemplate = Mockito.mock(RestTemplate.class);

        accountService = new AccountServiceImpl(accountRepository, modelMapper, kafkaTemplate, restTemplate);
    }

    @Test
    public void whenCreateAccountCalledWithValidRequestWithoutInitialBalance_itShouldReturnAccountId() {
        CreateAccountRequest request = new CreateAccountRequest(1L, BigDecimal.ZERO);
        Mockito.when(accountRepository.createAccount(1L)).thenReturn(1L);

        Long result = accountService.createAccount(request);

        Assertions.assertEquals(result, 1L);
        Mockito.verify(accountRepository).createAccount(1L);
    }

    @Test
    public void whenCreateAccountCalledWithValidRequestWithInitialBalance_itShouldReturnAccountId() {
        CreateAccountRequest request = new CreateAccountRequest(1L, BigDecimal.TEN);
        Mockito.when(accountRepository.createAccount(1L)).thenReturn(1L);

        Long result = accountService.createAccount(request);

        Assertions.assertEquals(result, 1L);
        Mockito.verify(accountRepository).createAccount(1L);
        Mockito.verify(kafkaTemplate).send(Constants.NEW_TRANSACTION_TOPIC, new TransactionDto(1L, 1L, BigDecimal.TEN));
    }

    @Test
    public void whenGetAccountInfoCalledWithCustomerIdAndFailedTransaction_ItShouldReturnAccountInfoResponseWithoutTransaction() {
        Customer customer = Customer.builder()
                .customerId(1L)
                .name("name")
                .surname("surname")
                .accountList(Collections.emptyList())
                .build();
        AccountInfoResponse accountInfoResponseWithoutTransaction = AccountInfoResponse.builder()
                .name("name")
                .surname("surname")
                .build();

        String url = GET_TRANSACTION_HISTORY_URI + 1L;

        Mockito.when(accountRepository.findById(1L)).thenReturn(customer);
        Mockito.when(modelMapper.toAccountInfoResponse(customer)).thenReturn(accountInfoResponseWithoutTransaction);
        Mockito.when(restTemplate.getForObject(url, TransactionHistoryDto[].class)).thenReturn(null);

        AccountInfoResponse result = accountService.getAccountInfo(1L);
        Assertions.assertEquals(result, accountInfoResponseWithoutTransaction);
        Mockito.verify(accountRepository).findById(1L);
        Mockito.verify(modelMapper).toAccountInfoResponse(customer);
        Mockito.verify(restTemplate).getForObject(url, TransactionHistoryDto[].class);
    }

    @Test
    public void whenGetAccountInfoCalledWithCustomerId_ItShouldReturnAccountInfoResponse() {
        Customer customer = Customer.builder()
                .customerId(1L)
                .name("name")
                .surname("surname")
                .accountList(Collections.emptyList())
                .build();
        AccountInfoResponse accountInfoResponseWithoutTransaction = AccountInfoResponse.builder()
                .customerId(1L)
                .name("name")
                .surname("surname")
                .build();
        List<BigDecimal> transactionList = List.of(BigDecimal.valueOf(4L), BigDecimal.valueOf(25L), BigDecimal.valueOf(-2L));
        AccountInfoResponse accountInfoResponse = AccountInfoResponse.builder()
                .customerId(1L)
                .name("name")
                .surname("surname")
                .balance(BigDecimal.valueOf(27L))
                .transactionHistory(List.of(new TransactionHistoryDto(1L, transactionList)))
                .build();
        TransactionHistoryDto transactionHistoryDto[] = {new TransactionHistoryDto(1L, transactionList)};
        String url = GET_TRANSACTION_HISTORY_URI + 1L;

        Mockito.when(accountRepository.findById(1L)).thenReturn(customer);
        Mockito.when(modelMapper.toAccountInfoResponse(customer)).thenReturn(accountInfoResponseWithoutTransaction);
        Mockito.when(restTemplate.getForObject(url, TransactionHistoryDto[].class)).thenReturn(transactionHistoryDto);

        AccountInfoResponse result = accountService.getAccountInfo(1L);
        Assertions.assertEquals(result, accountInfoResponse);
        Mockito.verify(accountRepository).findById(1L);
        Mockito.verify(modelMapper).toAccountInfoResponse(customer);
        Mockito.verify(restTemplate).getForObject(url, TransactionHistoryDto[].class);
    }

    @Test
    public void whenGetAllAccountInfosCalled_ItShouldReturnAccountInfoResponseList() {
        List<Customer> customerList = new ArrayList<>();
        String name = "name";
        String surname = "surname";
        for (int i = 1; i < 5; i++) {
            Customer customer = Customer.builder()
                    .customerId(Long.valueOf(i))
                    .name(name + i)
                    .surname(surname + i)
                    .build();
            customerList.add(customer);
        }
        List<AccountInfoResponse> accountInfoResponseList = new ArrayList<>();
        for (Customer customer : customerList) {
            AccountInfoResponse accountInfoResponse = AccountInfoResponse.builder()
                    .customerId(customer.getCustomerId())
                    .name(customer.getName())
                    .surname(customer.getSurname())
                    .build();
            accountInfoResponseList.add(accountInfoResponse);
        }
        List<BigDecimal> transactionListCustomer1 = List.of(BigDecimal.valueOf(4L), BigDecimal.valueOf(25L), BigDecimal.valueOf(-2L));
        List<BigDecimal> transactionListCustomer2 = List.of(BigDecimal.valueOf(4L), BigDecimal.valueOf(-2L));
        List<BigDecimal> transactionListCustomer3 = List.of(BigDecimal.valueOf(6L), BigDecimal.valueOf(-5L));
        List<AccountInfoResponse> accountInfoResponseListWithTransaction = new ArrayList<>();
        for (AccountInfoResponse accountInfoResponse : accountInfoResponseList) {
            AccountInfoResponse accountInfoResponseWithTransaction = AccountInfoResponse.builder()
                    .customerId(accountInfoResponse.getCustomerId())
                    .name(accountInfoResponse.getName())
                    .surname(accountInfoResponse.getSurname())
                    .build();
            if (accountInfoResponse.getCustomerId().equals(1L)) {
                accountInfoResponseWithTransaction.setTransactionHistory(List.of(new TransactionHistoryDto(1L, transactionListCustomer1)));
            } else if (accountInfoResponse.getCustomerId().equals(2L)) {
                accountInfoResponseWithTransaction.setTransactionHistory(List.of(new TransactionHistoryDto(2L, transactionListCustomer2)));
            } else if (accountInfoResponse.getCustomerId().equals(3L)) {
                accountInfoResponseWithTransaction.setTransactionHistory(List.of(new TransactionHistoryDto(3L, transactionListCustomer3)));
            }
            accountInfoResponseListWithTransaction.add(accountInfoResponse);
        }

        Mockito.when(accountRepository.findAll()).thenReturn(customerList);
        for (int i = 0; i < customerList.size(); i++) {
            Mockito.when(modelMapper.toAccountInfoResponse(customerList.get(i))).thenReturn(accountInfoResponseList.get(i));
            Long customerId = customerList.get(i).getCustomerId();
            String url = GET_TRANSACTION_HISTORY_URI + customerId;
            TransactionHistoryDto[] transactionHistoryDtoArray;
            if (customerId.equals(1L)) {
                transactionHistoryDtoArray = new TransactionHistoryDto[]{new TransactionHistoryDto(1L, transactionListCustomer1)};
                Mockito.when(restTemplate.getForObject(url, TransactionHistoryDto[].class)).thenReturn(transactionHistoryDtoArray);
            } else if (customerId.equals(2L)) {
                transactionHistoryDtoArray = new TransactionHistoryDto[]{new TransactionHistoryDto(2L, transactionListCustomer2)};
                Mockito.when(restTemplate.getForObject(url, TransactionHistoryDto[].class)).thenReturn(transactionHistoryDtoArray);
            } else if (customerId.equals(3L)) {
                transactionHistoryDtoArray = new TransactionHistoryDto[]{new TransactionHistoryDto(3L, transactionListCustomer3)};
                Mockito.when(restTemplate.getForObject(url, TransactionHistoryDto[].class)).thenReturn(transactionHistoryDtoArray);
            }
        }

        List<AccountInfoResponse> result = accountService.getAllAccountInfos();

        Assertions.assertEquals(result, accountInfoResponseListWithTransaction);
        Mockito.verify(accountRepository).findAll();
        for (int i = 0; i < customerList.size(); i++) {
            Long customerId = customerList.get(i).getCustomerId();
            String url = GET_TRANSACTION_HISTORY_URI + customerId;
            Mockito.verify(restTemplate).getForObject(url, TransactionHistoryDto[].class);
        }
    }

    @Test
    public void whenCreateCustomerCalledWithValidCreateCustomerRequest_itShouldReturnCustomerInfo() {
        CreateCustomerRequest request = new CreateCustomerRequest("Adam", "Ondra");
        Customer customer = Customer.builder()
                .name("Adam")
                .surname("Ondra")
                .accountList(new ArrayList<>())
                .build();
        Customer savedCustomer = new Customer(1L, "Adam", "Ondra", new ArrayList<>());
        CustomerInfoResponse customerInfoResponse = new CustomerInfoResponse(1L, "Adam", "Ondra");

        Mockito.when(modelMapper.toCustomer(request)).thenReturn(customer);
        Mockito.when(accountRepository.save(customer)).thenReturn(savedCustomer);
        Mockito.when(modelMapper.toCustomerInfoResponse(savedCustomer)).thenReturn(customerInfoResponse);

        CustomerInfoResponse result = accountService.createCustomer(request);

        Assertions.assertEquals(result, customerInfoResponse);
        Mockito.verify(modelMapper).toCustomer(request);
        Mockito.verify(accountRepository).save(customer);
        Mockito.verify(kafkaTemplate).send(Constants.NEW_CUSTOMER_TOPIC, new CreateCustomerDto(1L));
        Mockito.verify(modelMapper).toCustomerInfoResponse(savedCustomer);

    }

}
