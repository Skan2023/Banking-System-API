package com.example.banking_system_api.service.implementation;

import com.example.banking_system_api.dto.request.*;
import com.example.banking_system_api.exception.InsufficientBalanceException;
import com.example.banking_system_api.exception.ResourceNotFoundException;
import com.example.banking_system_api.mapper.AccountMapper;
import com.example.banking_system_api.mapper.TransactionMapper;
import com.example.banking_system_api.model.Account;
import com.example.banking_system_api.model.TransactionType;
import com.example.banking_system_api.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AccountServiceImp implements AccountService {

    private final AccountMapper accountMapper;
    private final TransactionMapper transactionMapper;

    private static final Logger logger = LoggerFactory.getLogger(AccountServiceImp.class);

    public AccountServiceImp(AccountMapper accountMapper, TransactionMapper transactionMapper) {
        this.accountMapper = accountMapper;
        this.transactionMapper = transactionMapper;
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
    public void depositMoney(DepositRequest depositRequest) {
        logger.info("Depositing {} to account {}",
                depositRequest.getAmount(),
                depositRequest.getAccountNumber());

        // 1. Check account exists
        Account account = accountMapper.findByAccountNumber(depositRequest.getAccountNumber());


        if (account == null) {
            throw new ResourceNotFoundException("Account not found");
        }

        // 2. Update balance
        accountMapper.depositMoney(depositRequest);

        // 3. Save transaction history
        TransactionRequest transaction = new TransactionRequest();
        transaction.setAccountId(account.getId());
        transaction.setType(TransactionType.DEPOSIT);
        transaction.setAmount(depositRequest.getAmount());
        transaction.setDescription("Deposit money");
        transactionMapper.saveTransaction(transaction);

        logger.info("Deposit successful");
    }

    @Override
    public void withdrawMoney(WithdrawRequest withdrawRequest) {
        logger.info("Withdawing {} from account {}",
                withdrawRequest.getAmount(),
                withdrawRequest.getAccountNumber()
                );
        Account account = accountMapper.findByAccountNumber(withdrawRequest.getAccountNumber());

        if (account == null) {
            throw new ResourceNotFoundException("Account not found");
        }

        if (account.getBalance() < withdrawRequest.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        accountMapper.withdrawMoney(withdrawRequest);

        TransactionRequest transactionRequest = new TransactionRequest();
        transactionRequest.setAccountId(account.getId());
        transactionRequest.setType(TransactionType.WITHDRAW);
        transactionRequest.setAmount(withdrawRequest.getAmount());
        transactionRequest.setDescription("Withdraw money");
        transactionMapper.saveTransaction(transactionRequest);

        logger.info("Withdraw successfully");
    }

    @Override
    @Transactional
    public void transferMoney(TransferRequest transferRequest) {
        logger.info("Transfer {} from {} to {}",
                transferRequest.getAmount(),
                transferRequest.getFromAccount(),
                transferRequest.getToAccount()
                );

        // 1. Validate same account
        if (transferRequest.getFromAccount().equals(transferRequest.getToAccount())) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }

        // 2. Find accounts
        Account sender = accountMapper.findByAccountNumber(transferRequest.getFromAccount());

        Account receiver = accountMapper.findByAccountNumber(transferRequest.getToAccount());

        if (sender == null) {
            throw new ResourceNotFoundException("Sender account not found");
        }

        if (receiver == null) {
            throw new ResourceNotFoundException("Receiver account not found");
        }

        // 3. Check balance
        if (sender.getBalance() < transferRequest.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

        // 4. Update balances
        sender.setBalance(sender.getBalance() - transferRequest.getAmount());
        receiver.setBalance(receiver.getBalance() + transferRequest.getAmount());

        accountMapper.updateBalance(sender);
        accountMapper.updateBalance(receiver);

        // 5. Save DEBIT transaction
        TransactionRequest debit = new TransactionRequest();
        debit.setAccountId(sender.getId());
        debit.setType(TransactionType.DEBIT);
        debit.setAmount(transferRequest.getAmount());
        debit.setDescription("Transfer to " + receiver.getAccountNumber());

        transactionMapper.saveTransaction(debit);

        // 6. Save CREDIT transaction
        TransactionRequest credit = new TransactionRequest();
        credit.setAccountId(receiver.getId());
        credit.setType(TransactionType.CREDIT);
        credit.setAmount(transferRequest.getAmount());
        credit.setDescription("Received from " + sender.getAccountNumber());
        transactionMapper.saveTransaction(credit);

        logger.info("Transfer successful");
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
