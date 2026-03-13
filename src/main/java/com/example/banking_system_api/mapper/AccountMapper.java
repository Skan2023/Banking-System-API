package com.example.banking_system_api.mapper;

import com.example.banking_system_api.dto.request.CreateAccountRequest;
import com.example.banking_system_api.dto.request.UpdateAccountRequest;
import com.example.banking_system_api.model.Account;
import com.example.banking_system_api.service.AccountService;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AccountMapper {

    @Select("SELECT * FROM accounts")
    @Results(id = "accountMapper", value = {
            @Result(property = "accountNumber", column = "account_number"),
            @Result(property = "accountType", column = "account_type"),
            @Result(property = "customer", column = "customer_id",
                one = @One(select = "com.example.banking_system_api.mapper.CustomerMapper.findCustomerById")
            ),
    })
    List<Account> findAllAccounts();

    @Select("SELECT * FROM accounts WHERE id = #{id}")
    @ResultMap("accountMapper")
    Account findAccountById(Integer id);

    @Select("""
            INSERT INTO accounts(account_number, balance, account_type, customer_id, created_at)
            VALUES (#{account.accountNumber}, #{account.balance}, #{account.accountType}, #{account.customerId}, CURRENT_TIMESTAMP)
            returning *
            """)
    @ResultMap("accountMapper")
    Account createAccount(@Param("account") CreateAccountRequest createAccountRequest);

    @Select("""
            UPDATE accounts
            SET account_number = #{account.accountNumber},
                balance = #{account.balance},
                account_type = #{account.accountType},
                customer_id = #{account.customerId}
            WHERE id = #{id}
            RETURNING *
            """)
    @ResultMap("accountMapper")
    Account updateAccountById(@Param("account") UpdateAccountRequest updateAccountRequest, Integer id);

    @Delete("DELETE FROM accounts WHERE id = #{id}")
    void deleteAccountById(Integer id);
}
