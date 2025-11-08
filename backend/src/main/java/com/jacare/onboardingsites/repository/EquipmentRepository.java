package com.jacare.onboardingsites.repository;

import com.jacare.onboardingsites.model.Customer;
import com.jacare.onboardingsites.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Integer> {
    @Modifying
    void insert(Equipment equipment);

    @Modifying
    void update(Equipment equipment);

    @Modifying
    @DeleteMapping
    void deleteCustomerByName(String name);

    Equipment findByName(String name);
}
