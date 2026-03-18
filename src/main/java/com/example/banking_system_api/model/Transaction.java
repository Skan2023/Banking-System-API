package com.example.banking_system_api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    private Integer id;
    private Account account;
    private TransactionType type;
    private Double amount;
    private String description;
}
