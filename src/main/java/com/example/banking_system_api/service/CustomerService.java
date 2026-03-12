package com.example.banking_system_api.service;

import com.example.banking_system_api.dto.request.CreateCustomerRequest;
import com.example.banking_system_api.dto.request.UpdateCustomerRequest;
import com.example.banking_system_api.model.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();
    Customer getCustomerById(Integer id);
    Customer createCustomer(CreateCustomerRequest createCustomerRequest);
    Customer updateCustomerById(UpdateCustomerRequest updateCustomerRequest, Integer id);
    boolean deleteCustomerById(Integer id);
}
