package com.example.banking_system_api.service;

import com.example.banking_system_api.dto.request.TransactionRequest;
import com.example.banking_system_api.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions();
    Transaction getTransactionsById(Integer id);
    Transaction createTransaction(TransactionRequest transactionRequest);
    Transaction updateTransactionById(TransactionRequest transactionRequest, Integer id);
    boolean deleteTransactionById(Integer id);
}
