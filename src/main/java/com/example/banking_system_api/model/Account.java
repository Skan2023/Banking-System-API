package com.example.banking_system_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Integer id;
    private String accountNumber;
    private Double balance;
    private String accountType;
    private Customer customer;
}
