package com.example.banking_system_api.service.implementation;

import com.example.banking_system_api.dto.request.CreateAccountRequest;
import com.example.banking_system_api.dto.request.UpdateAccountRequest;
import com.example.banking_system_api.mapper.AccountMapper;
import com.example.banking_system_api.model.Account;
import com.example.banking_system_api.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImp implements AccountService {

    private final AccountMapper accountMapper;

    public AccountServiceImp(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountMapper.findAllAccounts();
    }

    @Override
    public Account getAccountById(Integer id) {
        Account account = accountMapper.findAccountById(id);

        if (account == null) {
            return null;
        }
        return accountMapper.findAccountById(id);
    }

    @Override
    public Account createAccount(CreateAccountRequest createAccountRequest) {
        return accountMapper.createAccount(createAccountRequest);
    }

    @Override
    public Account updateAccountById(UpdateAccountRequest updateAccountRequest, Integer id) {

        Account account = accountMapper.findAccountById(id);

        if (account != null) {
            return accountMapper.updateAccountById(updateAccountRequest, id);
        }

        return null;
    }

    @Override
    public boolean deleteAccountById(Integer id) {

        Account account = accountMapper.findAccountById(id);

        if (account != null) {
            accountMapper.deleteAccountById(id);
            return true;
        }

        return false;
    }
}
