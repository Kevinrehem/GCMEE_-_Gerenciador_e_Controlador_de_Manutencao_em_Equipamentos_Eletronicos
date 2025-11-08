package com.jacare.onboardingsites.service;

import com.jacare.onboardingsites.dto.Technician.TechnicianRegisterDTO;
import com.jacare.onboardingsites.model.ServiceOrder;
import com.jacare.onboardingsites.model.Technician;
import com.jacare.onboardingsites.repository.ServiceOrderRepository;
import com.jacare.onboardingsites.repository.TechnicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TechnicianService {
    private final TechnicianRepository technicianRepository;
    private final ServiceOrderRepository serviceOrderRepository;

    @Autowired
    public TechnicianService(TechnicianRepository technicianRepository, ServiceOrderRepository serviceOrderRepository) {
        this.technicianRepository = technicianRepository;
        this.serviceOrderRepository = serviceOrderRepository;
    }

    // Cria serviço para receber requisição HTTP e registrar um novo técnico no banco de dados
    @Transactional
    public boolean registerTechnician(TechnicianRegisterDTO request) {
        List<ServiceOrder> serviceOrders = new ArrayList<>();

        Technician technician = Technician.builder()
                .name(request.name())
                .serviceOrders(serviceOrders)
                .build();

        technicianRepository.save(technician);
        return true;
    }

    @Transactional
    public List<Technician> findAllTechnicians() {
        return technicianRepository.findAll();
    }
}
