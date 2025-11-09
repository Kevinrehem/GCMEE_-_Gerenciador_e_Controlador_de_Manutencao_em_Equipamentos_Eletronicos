package com.jacare.onboardingsites.service;

import com.jacare.onboardingsites.dto.Technician.TechnicianGetDTO;
import com.jacare.onboardingsites.dto.Technician.TechnicianCreateDTO;
import com.jacare.onboardingsites.dto.Technician.TechnicianUpdateDTO;
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
    public boolean registerTechnician(TechnicianCreateDTO request) {
        List<ServiceOrder> serviceOrders = new ArrayList<>();

        Technician technician = Technician.builder()
                .name(request.name())
                .serviceOrders(serviceOrders)
                .build();

        technicianRepository.save(technician);
        return true;
    }

    @Transactional
    public List<TechnicianGetDTO> findAllTechnicians() {
        List<Technician> technicians = technicianRepository.findAll();
        List<TechnicianGetDTO> technicianGetDTOs = new ArrayList<>();
        for(Technician technician : technicians) {
            technicianGetDTOs.add(convertToGetDTO(technician));
        }
        return technicianGetDTOs;
    }

    @Transactional
    public boolean updateTechnician(TechnicianUpdateDTO request) {
        Technician technician = technicianRepository.findById(request.id()).orElse(null);
        if(technician == null) {
            return false;
        }
        technician.setName(request.name());
        technicianRepository.save(technician);
        return true;
    }

    @Transactional
    public boolean deleteTechnician(Long id){
        if(technicianRepository.existsById(id)) {
            technicianRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public TechnicianGetDTO convertToGetDTO(Technician technician) {
        return new TechnicianGetDTO(technician.getId(), technician.getName());
    }


}
