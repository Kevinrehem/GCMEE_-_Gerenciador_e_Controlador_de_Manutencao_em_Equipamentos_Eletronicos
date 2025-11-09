package com.jacare.onboardingsites.service;

import com.jacare.onboardingsites.dto.Equipment.EquipmentCreateDTO;
import com.jacare.onboardingsites.dto.Equipment.EquipmentGetDTO;
import com.jacare.onboardingsites.dto.Equipment.EquipmentUpdateDTO;
import com.jacare.onboardingsites.model.Equipment;
import com.jacare.onboardingsites.repository.CustomerRepository;
import com.jacare.onboardingsites.repository.EquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class EquipmentService {

    private final EquipmentRepository equipmentRepository;
    private final CustomerRepository customerRepository;
    private final CustomerService customerService;

    @Autowired
    public EquipmentService(EquipmentRepository equipmentRepository,  CustomerRepository customerRepository, CustomerService customerService) {
        this.equipmentRepository = equipmentRepository;
        this.customerRepository = customerRepository;
        this.customerService = customerService;
    }

    @Transactional
    public boolean createEquipment(EquipmentCreateDTO request) {
        if(!customerRepository.existsById(request.customerId())) return false;
        Equipment equipment = Equipment.builder()
                .name(request.name())
                .owner(customerRepository.findById(request.customerId()).orElse(null))
                .type(request.equipType())
                .build();

        equipmentRepository.save(equipment);
        return true;

    }

    @Transactional
    public List<EquipmentGetDTO> findAllEquipments() {
        List<Equipment> equipments = equipmentRepository.findAll();
        List<EquipmentGetDTO> equipmentGetDTOs = new ArrayList<>();
        for (Equipment equipment : equipments) {
            equipmentGetDTOs.add(convertToGetDTO(equipment));
        }
        return equipmentGetDTOs;
    }

    @Transactional
    public boolean updateEquipment(EquipmentUpdateDTO request) {
        if(request.customerId() != null){
            if(!customerRepository.existsById(request.customerId())) return false;
        }
        Equipment equipment = equipmentRepository.findById(request.id()).orElse(null);
        if(equipment == null) return false;
        if(request.name() != null) equipment.setName(request.name());
        if(request.equipType() != null) equipment.setType(request.equipType());
        if(request.customerId() != null) equipment.setOwner(customerRepository.findById(request.customerId()).orElse(null));
        equipmentRepository.save(equipment);
        return true;
    }

    @Transactional
    public boolean deleteEquipment(Long id) {
        if(!equipmentRepository.existsById(id)) return false;
        equipmentRepository.deleteById(id);
        return true;
    }

    public EquipmentGetDTO convertToGetDTO(Equipment equipment) {
        return new  EquipmentGetDTO(equipment.getId(), equipment.getName(), equipment.getType(), customerService.convertCustomerToGetDTO(equipment.getOwner()));
    }

}
