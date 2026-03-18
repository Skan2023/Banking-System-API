package com.example.banking_system_api.dto.request;

import com.example.banking_system_api.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequest {
    @NotNull(message = "Account ID is required")
    private Integer accountId;
    @NotNull(message = "Transaction type is required")
    private TransactionType type;
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than 0")
    private Double amount;


    private String description;
}
