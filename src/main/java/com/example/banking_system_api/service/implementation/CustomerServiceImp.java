package com.example.banking_system_api.service.implementation;

import com.example.banking_system_api.dto.request.CreateCustomerRequest;
import com.example.banking_system_api.dto.request.UpdateCustomerRequest;
import com.example.banking_system_api.mapper.CustomerMapper;
import com.example.banking_system_api.model.Customer;
import com.example.banking_system_api.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImp implements CustomerService {
    private final CustomerMapper customerMapper;

    public CustomerServiceImp(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerMapper.findAllCustomer();
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerMapper.findCustomerById(id);
    }

    @Override
    public Customer createCustomer(CreateCustomerRequest createCustomerRequest) {
        return customerMapper.createCustomer(createCustomerRequest);
    }

    @Override
    public Customer updateCustomerById(UpdateCustomerRequest updateCustomerRequest, Integer id) {
        return customerMapper.updateCustomerById(updateCustomerRequest, id);
    }

    @Override
    public boolean deleteCustomerById(Integer id) {

        if (customerMapper.findCustomerById(id) == null) {
            return false;
        }

        customerMapper.deleteCustomerById(id);
        return true;
    }
}
