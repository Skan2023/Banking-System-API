package com.example.banking_system_api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCustomerRequest {
    @NotNull(message = "Full name is required")
    private String fullName;
    @NotNull(message = "Email is required")
    private String email;
    @NotNull(message = "Phone number is required")
    private String phoneNumber;
}
