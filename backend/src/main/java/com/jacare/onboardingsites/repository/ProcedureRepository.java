package com.jacare.onboardingsites.repository;

import com.jacare.onboardingsites.model.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;

@Repository
public interface ProcedureRepository extends JpaRepository<Procedure,Long> {

    @Modifying
    @DeleteMapping
    void deleteProcedureByName(String name);

    Procedure findByName(String name);
}
