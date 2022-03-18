package com.serkankarabulut.accountservice.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class CustomerInfoResponse implements Serializable {
    private Long customerId;
    private String name;
    private String surname;
}
