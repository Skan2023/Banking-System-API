package com.example.banking_system_api.controller;

import com.example.banking_system_api.dto.request.CreateCustomerRequest;
import com.example.banking_system_api.dto.request.UpdateCustomerRequest;
import com.example.banking_system_api.model.Customer;
import com.example.banking_system_api.response.ApiResponse;
import com.example.banking_system_api.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ApiResponse<List<Customer>> getAllCustomers() {

        List<Customer> customers = customerService.getAllCustomers();

        ApiResponse<List<Customer>> response = new ApiResponse<>(true, "Customers fetched successfully!", customers);

        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> getCustomerById(@PathVariable Integer id) {
        Customer customer = customerService.getCustomerById(id);

        if (customer == null) {
            ApiResponse<Customer> response = new ApiResponse<>(false, "Customer with " + id + " not found.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<Customer> response = new ApiResponse<>(true, "Customer with " + id + " fetched successfully!", customer);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Customer>> createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest) {
        Customer customer = customerService.createCustomer(createCustomerRequest);

        ApiResponse<Customer> response = new ApiResponse<>(true, "Customer created successfully!", customer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Customer>> updateCustomerById(@RequestBody UpdateCustomerRequest updateCustomerRequest, Integer id) {

        Customer updatedCustomer = customerService.updateCustomerById(updateCustomerRequest, id);

        ApiResponse<Customer> response = new ApiResponse<>(true, "Customer with " + id + " updated successfully!", updatedCustomer);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomerById(@PathVariable Integer id) {
        boolean deleted = customerService.deleteCustomerById(id);

        if (!deleted) {
            ApiResponse<Void> response = new ApiResponse<>(false, "Customer with id " + id + " not found.", null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ApiResponse<Void> response = new ApiResponse<>(true, "Customer with id " + id + " deleted successfully!", null);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
