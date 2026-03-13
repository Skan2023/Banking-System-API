package com.example.banking_system_api.mapper;

import com.example.banking_system_api.dto.request.CreateCustomerRequest;
import com.example.banking_system_api.dto.request.UpdateCustomerRequest;
import com.example.banking_system_api.model.Customer;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CustomerMapper {

    @Select("SELECT * FROM customers")
    @Results(id = "customerMapper", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "email", column = "email"),
            @Result(property = "phoneNumber", column = "phone"),
            @Result(property = "createdAt", column = "created_at")
    })
    List<Customer> findAllCustomer();

    @Select("SELECT * FROM customers WHERE id=#{id}")
    @ResultMap("customerMapper")
    Customer findCustomerById(Integer id);

    @Select("""
            INSERT INTO customers(full_name, email, phone, created_at)
            VALUES(#{fullName}, #{email}, #{phoneNumber}, CURRENT_TIMESTAMP)
            returning *
            """)
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @ResultMap("customerMapper")
    Customer createCustomer(CreateCustomerRequest createCustomerRequest);

    @Select("""
            UPDATE customers
            SET full_name = #{customer.fullName},
                email = #{customer.email},
                phone = #{customer.phoneNumber}
            WHERE id = #{id}
            RETURNING *
            """)
    @ResultMap("customerMapper")
    Customer updateCustomerById(@Param("customer") UpdateCustomerRequest updateCustomerRequest, Integer id);

    @Delete("DELETE FROM customers WHERE id = #{id}")
    void deleteCustomerById(Integer id);
}
