package com.jacare.onboardingsites.repository;

import com.jacare.onboardingsites.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Integer> {
    @Modifying
    void insert(Technician Technician);

    @Modifying
    void update(Technician Technician);

    @Modifying
    @DeleteMapping
    void deleteTechnicianByName(String name);

    Technician findByName(String name);
}
