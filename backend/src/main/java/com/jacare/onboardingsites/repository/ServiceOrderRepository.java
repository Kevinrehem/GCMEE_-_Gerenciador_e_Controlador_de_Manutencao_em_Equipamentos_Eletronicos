package com.jacare.onboardingsites.repository;

import com.jacare.onboardingsites.model.ServiceOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder,Long> {
    @Modifying
    void insert(ServiceOrder ServiceOrder);

    @Modifying
    void update(ServiceOrder ServiceOrder);

    @Modifying
    @DeleteMapping
    void deleteServiceOrderByName(String name);

    ServiceOrder findByName(String name);
}
