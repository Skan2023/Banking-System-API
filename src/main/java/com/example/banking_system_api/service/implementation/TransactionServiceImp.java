package com.example.banking_system_api.service.implementation;

import com.example.banking_system_api.dto.request.TransactionRequest;
import com.example.banking_system_api.exception.ResourceNotFoundException;
import com.example.banking_system_api.mapper.TransactionMapper;
import com.example.banking_system_api.model.Transaction;
import com.example.banking_system_api.service.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImp implements TransactionService {
    private final TransactionMapper transactionMapper;
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImp.class);

    public TransactionServiceImp(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionMapper.findAllTransactions();
    }

    @Override
    public Transaction getTransactionsById(Integer id) {

        return transactionMapper.findTransactionById(id);
    }

    @Override
    public Transaction createTransaction(TransactionRequest transactionRequest) {

        logger.info("Transaction created successfully!");
        return transactionMapper.saveTransaction(transactionRequest);
    }

    @Override
    public Transaction updateTransactionById(TransactionRequest transactionRequest, Integer id) {
        Transaction transaction = transactionMapper.findTransactionById(id);

        if (transaction != null) {
            logger.info("Transaction with id " + id + " updated successfully!");
            return transactionMapper.updateTransactionById(transactionRequest, id);
        }

        return null;
    }

    @Override
    public boolean deleteTransactionById(Integer id) {

        if (transactionMapper.findTransactionById(id) == null) {
            return false;
        }

        transactionMapper.deleteTransactionById(id);
        return true;
    }
}
