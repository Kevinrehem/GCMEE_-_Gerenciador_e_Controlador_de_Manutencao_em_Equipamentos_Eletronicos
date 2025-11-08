package com.jacare.onboardingsites.repository;

import com.jacare.onboardingsites.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Modifying
    @DeleteMapping
    void deleteCustomerByName(String name);

    Customer findByName(String name);
}
