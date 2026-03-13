package com.example.banking_system_api.service;

import com.example.banking_system_api.dto.request.CreateAccountRequest;
import com.example.banking_system_api.dto.request.UpdateAccountRequest;
import com.example.banking_system_api.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    Account getAccountById(Integer id);
    Account createAccount(CreateAccountRequest createAccountRequest);
    Account updateAccountById(UpdateAccountRequest updateAccountRequest, Integer id);
    boolean deleteAccountById(Integer id);
}
