package com.jacare.onboardingsites.service;

import com.jacare.onboardingsites.dto.Customer.CustomerCreateDTO;
import com.jacare.onboardingsites.dto.Customer.CustomerGetDTO;
import com.jacare.onboardingsites.dto.Customer.CustomerUpdateDTO;
import com.jacare.onboardingsites.model.Customer;
import com.jacare.onboardingsites.model.Equipment;
import com.jacare.onboardingsites.repository.CustomerRepository;
import com.jacare.onboardingsites.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final EquipmentRepository equipmentRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository, EquipmentRepository equipmentRepository) {
        this.customerRepository = customerRepository;
        this.equipmentRepository = equipmentRepository;
    }

    @Transactional
    public boolean registerCustomer(CustomerCreateDTO request){
        List<Equipment> equipments = new ArrayList<>();

        Customer customer = Customer.builder()
                .name(request.name())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .debt(0.0)
                .equipments(equipments)
                .build();

        customerRepository.save(customer);

        return true;
    }

    @Transactional
    public List<CustomerGetDTO> findAllCustomers(){
        List<Customer> customers = customerRepository.findAll();
        List<CustomerGetDTO> customerGetDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            customerGetDTOs.add(convertCustomerToGetDTO(customer));
        }
        return customerGetDTOs;
    }

    @Transactional
    public boolean updateCustomer(CustomerUpdateDTO request){
        if (customerRepository.existsById(request.id())){
            Customer customer = customerRepository.findById(request.id()).orElse(null);
            if (customer == null) return false;
            if(request.name() != null && !request.name().isEmpty()){
                customer.setName(request.name());
            }
            if (request.phoneNumber() != null && !request.phoneNumber().isEmpty()){
                customer.setPhoneNumber(request.phoneNumber());
            }
            if (request.email() != null && !request.email().isEmpty()){
                customer.setEmail(request.email());
            }
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteCustomer(Long id){
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public CustomerGetDTO convertCustomerToGetDTO(Customer customer){
        return new CustomerGetDTO(
                customer.getId(),
                customer.getName(),
                customer.getPhoneNumber(),
                customer.getEmail()
        );
    }


}
