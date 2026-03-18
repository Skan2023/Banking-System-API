package com.example.banking_system_api.service;

import com.example.banking_system_api.dto.request.*;
import com.example.banking_system_api.model.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccounts();
    Account getAccountById(Integer id);
    void depositMoney(DepositRequest depositRequest);
    void withdrawMoney(WithdrawRequest withdrawRequest);
    void transferMoney(TransferRequest transferRequest);
    Account createAccount(CreateAccountRequest createAccountRequest);
    Account updateAccountById(UpdateAccountRequest updateAccountRequest, Integer id);
    boolean deleteAccountById(Integer id);
}
