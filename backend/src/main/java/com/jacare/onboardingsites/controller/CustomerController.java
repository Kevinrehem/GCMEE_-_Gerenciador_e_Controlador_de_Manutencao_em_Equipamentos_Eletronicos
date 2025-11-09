package com.jacare.onboardingsites.controller;

import com.jacare.onboardingsites.dto.Customer.CustomerCreateDTO;
import com.jacare.onboardingsites.dto.Customer.CustomerGetDTO;
import com.jacare.onboardingsites.dto.Customer.CustomerUpdateDTO;
import com.jacare.onboardingsites.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/app/customer")
public class CustomerController {
    private final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/select-all")
    public ResponseEntity<List<CustomerGetDTO>> getAllCustomers(){
        List<CustomerGetDTO> customers = customerService.findAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerCreateDTO customerCreateDTO){
        boolean result = customerService.registerCustomer(customerCreateDTO);
        if(result){
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body("Customer created successfully");
        }else{
            return ResponseEntity
                    .badRequest()
                    .body("Customer creation failed");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdateDTO customerUpdateDTO){
        boolean result = customerService.updateCustomer(customerUpdateDTO);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).body("Customer updated successfully");
        }else{
            return ResponseEntity
                    .badRequest()
                    .body("Customer could not be found");
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("id") Long id){
        boolean result = customerService.deleteCustomer(id);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).body("Customer deleted successfully");
        }else {
            return  ResponseEntity
                    .badRequest()
                    .body("Customer could not be found");
        }
    }
}
