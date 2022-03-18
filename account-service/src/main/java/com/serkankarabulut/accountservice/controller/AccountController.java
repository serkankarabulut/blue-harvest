package com.serkankarabulut.accountservice.controller;

import com.serkankarabulut.accountservice.model.request.CreateAccountRequest;
import com.serkankarabulut.accountservice.model.request.CreateCustomerRequest;
import com.serkankarabulut.accountservice.model.response.AccountInfoResponse;
import com.serkankarabulut.accountservice.model.response.CustomerInfoResponse;
import com.serkankarabulut.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping("createAccount")
    public ResponseEntity<Long> createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return ResponseEntity.ok(accountService.createAccount(request));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<AccountInfoResponse> getAccountInfo(@PathVariable @NotNull Long customerId) {
        return ResponseEntity.ok(accountService.getAccountInfo(customerId));
    }

    @GetMapping
    public ResponseEntity<List<AccountInfoResponse>> getAllAccountInfos(){
        return ResponseEntity.ok(accountService.getAllAccountInfos());
    }

    @PostMapping("/createCustomer")
    public ResponseEntity<CustomerInfoResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request){
        return ResponseEntity.ok(accountService.createCustomer(request));
    }

    @GetMapping("/mock")
    public ResponseEntity<Void> generateMockData(){
        accountService.generateMockData();
        return ResponseEntity.ok().build();
    }

}
