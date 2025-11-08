package com.jacare.onboardingsites.repository;

import com.jacare.onboardingsites.model.ServiceOrder;
import com.jacare.onboardingsites.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

@Repository
public interface ServiceOrderRepository extends JpaRepository<ServiceOrder,Long> {

    @Modifying
    @DeleteMapping
    void deleteServiceOrderById(Long id);

    ServiceOrder findByTechnician(Technician technician);
}
