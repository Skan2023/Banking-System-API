package com.example.banking_system_api.controller;

import com.example.banking_system_api.dto.request.*;
import com.example.banking_system_api.model.Account;
import com.example.banking_system_api.response.ApiResponse;
import com.example.banking_system_api.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/accounts")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Account>>> getAllAccounts() {
        List<Account> accounts = accountService.getAllAccounts();

        ApiResponse<List<Account>> response = new ApiResponse<>(true, "Accounts fetched successfully!", accounts);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<Account> getAccountById(@PathVariable Integer id) {
        Account account = accountService.getAccountById(id);

        if (account != null) {
            ApiResponse<Account> response = new ApiResponse<>(true, "Account with id "+ id + " fetched successfully!", account);

            return response;
        }

        ApiResponse<Account> response = new ApiResponse<>(false, "Account with id " + id + " not found.", null);

        return response;
    }

    @PostMapping("/deposit")
    public ApiResponse<String> depositMoney(@Valid @RequestBody DepositRequest depositRequest) {
        accountService.depositMoney(depositRequest);

        return new ApiResponse<>(true, "Deposit successfully!", depositRequest.getAmount().toString() + "$");
    }

    @PostMapping("/withdraw")
    public ApiResponse<String> withdrawMoney(@Valid @RequestBody WithdrawRequest withdrawRequest) {
        accountService.withdrawMoney(withdrawRequest);

        return new ApiResponse<>(true, "Withdraw successfully!", withdrawRequest.getAmount().toString() + "$");
    }

    @PostMapping("/transfer")
    public ApiResponse<String> transferMoney(@Valid @RequestBody TransferRequest transferRequest) {
        accountService.transferMoney(transferRequest);

        return new ApiResponse<>(true, "Transfer successfully!", transferRequest.getAmount().toString() + "$");
    }

    @PostMapping
    public ApiResponse<Account> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        Account account = accountService.createAccount(createAccountRequest);

        ApiResponse<Account> response = new ApiResponse<>(true, "Account created successfully!", account);
        return response;
    }

    @PutMapping("/{id}")
    public ApiResponse<Account> updateAccountById(@Valid @RequestBody UpdateAccountRequest updateAccountRequest, Integer id) {
        Account updatedAccount = accountService.updateAccountById(updateAccountRequest, id);

        if (updatedAccount != null) {
            ApiResponse<Account> response = new ApiResponse<>(true, "Account with " + id + " updated successfully!", updatedAccount);
            return response;
        } else {
            ApiResponse<Account> response = new ApiResponse<>(false, "Account with " + id + " not found.", null);
            return response;
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAccountById(@PathVariable Integer id) {
        boolean deleted = accountService.deleteAccountById(id);

        if (!deleted) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Account with " + id + " not fount.", null);
            return response;
        }

        ApiResponse<Void> response = new ApiResponse<>(true, "Account with " + id + " deleted successfully!", null);
        return response;
    }
}
