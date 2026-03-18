package com.example.banking_system_api.controller;

import com.example.banking_system_api.dto.request.TransactionRequest;
import com.example.banking_system_api.model.Transaction;
import com.example.banking_system_api.response.ApiResponse;
import com.example.banking_system_api.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ApiResponse<List<Transaction>> getAllTransactions() {
        List<Transaction> transactions = transactionService.getAllTransactions();

        ApiResponse<List<Transaction>> response = new ApiResponse<>(true, "Transactions fetched successfully!", transactions);
        return response;
    }

    @GetMapping("/{id}")
    public ApiResponse<Transaction> getTransactionById(@PathVariable Integer id) {
        Transaction transaction = transactionService.getTransactionsById(id);

        if (transaction != null) {
            ApiResponse<Transaction> response = new ApiResponse<>(true, "Transaction with id "+ id + " fetched successfully!", transaction);
            return response;
        }

        ApiResponse<Transaction> response = new ApiResponse<>(false, "Transaction with id "+ id + " Not found.", null);
        return response;
    }

    @PostMapping
    public ApiResponse<?> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest) {
        Transaction transaction = transactionService.createTransaction(transactionRequest);

        return new ApiResponse<>(true, "Transaction created successfully!", transaction);
    }

    @PutMapping("/{id}")
    public ApiResponse<?> updateTransactionById(@Valid @RequestBody TransactionRequest transactionRequest, @PathVariable Integer id) {
        Transaction updatedTransaction = transactionService.updateTransactionById(transactionRequest, id);

        if (updatedTransaction != null) {
            return new ApiResponse<>(true, "Transaction with " + id + " updated succsessfully!", updatedTransaction);
        } else {
            return new ApiResponse<>(false, "Transaction with " + id + " not found.", null);
        }
    }

    @DeleteMapping("/{id}")
    public ApiResponse<?> deleteTransactionById(@PathVariable Integer id) {

        boolean deleted = transactionService.deleteTransactionById(id);

        if (!deleted) {
            ApiResponse<?> response = new ApiResponse<>(false, "Transaction with id " + id + " not found", null);
            return response;
        }

        return new ApiResponse<>(true, "Transaction deleted successfully!", null);
    }
}
