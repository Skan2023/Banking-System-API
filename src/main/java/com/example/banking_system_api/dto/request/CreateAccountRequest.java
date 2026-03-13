package com.example.banking_system_api.dto.request;

import com.example.banking_system_api.model.Customer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    @NotBlank(message = "Account number is required")
    private String accountNumber;
    @NotNull(message = "Balance is required")
    @Min(value = 0, message = "Balance cannot be negative")
    private Double balance;
    @NotBlank(message = "Account type is required")
    private String accountType;
    @NotNull(message = "Customer ID is required")
    private Integer customerId;
}
