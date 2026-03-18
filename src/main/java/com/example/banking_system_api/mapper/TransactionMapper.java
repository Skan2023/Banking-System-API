package com.example.banking_system_api.mapper;

import com.example.banking_system_api.dto.request.TransactionRequest;
import com.example.banking_system_api.model.Transaction;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TransactionMapper {

    @Select("SELECT * FROM transactions")
    @Results(id = "TransactionMapper", value = {
            @Result(property = "account", column = "account_id",
                one = @One(select = "com.example.banking_system_api.mapper.AccountMapper.findAccountById")
            )
    })
    List<Transaction> findAllTransactions();

    @Select("SELECT * FROM transactions WHERE id=#{id}")
    @ResultMap("TransactionMapper")
    Transaction findTransactionById(Integer id);

    @Select("""
            INSERT INTO transactions(account_id, type, amount, description)
            VALUES(#{accountId}, #{type}, #{amount}, #{description})
            returning *
            """)
    Transaction saveTransaction(TransactionRequest transactionRequest);


    @Select("""
            UPDATE transactions
            SET account_id = #{transaction.accountId},
                type = #{transaction.type},
                amount = #{transaction.amount},
                description = #{transaction.description}
            WHERE id = #{id}
            returning *
            """)
    @ResultMap("TransactionMapper")
    Transaction updateTransactionById(@Param("transaction") TransactionRequest transactionRequest, @Param("id") Integer id);


    @Delete("DELETE FROM transactions WHERE id=#{id}")
    void deleteTransactionById(Integer id);
}
