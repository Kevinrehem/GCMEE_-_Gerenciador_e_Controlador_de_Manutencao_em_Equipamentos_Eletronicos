package com.jacare.onboardingsites.service;

import com.jacare.onboardingsites.dto.Procedure.ProcedureGetDTO;
import com.jacare.onboardingsites.dto.ServiceOrder.ServiceOrderCreateDTO;
import com.jacare.onboardingsites.dto.ServiceOrder.ServiceOrderGetDTO;
import com.jacare.onboardingsites.dto.ServiceOrder.ServiceOrderUpdateDTO;
import com.jacare.onboardingsites.model.Procedure;
import com.jacare.onboardingsites.model.ServiceOrder;
import com.jacare.onboardingsites.model.enums.ServiceOrderStatus;
import com.jacare.onboardingsites.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceOrderService {
    private final ServiceOrderRepository serviceOrderRepository;
    private final EquipmentRepository equipmentRepository;
    private final TechnicianRepository technicianRepository;
    private final ProcedureRepository procedureRepository;
    private final TechnicianService technicianService;
    private final EquipmentService equipmentService;
    private final ProcedureService procedureService;

    @Autowired
    public ServiceOrderService(
            ServiceOrderRepository serviceOrderRepository,
            EquipmentRepository equipmentRepository,
            TechnicianRepository technicianRepository,
            ProcedureRepository procedureRepository,
            TechnicianService technicianService,
            EquipmentService equipmentService,
            ProcedureService procedureService
    ) {
        this.serviceOrderRepository = serviceOrderRepository;
        this.equipmentRepository = equipmentRepository;
        this.technicianRepository = technicianRepository;
        this.procedureRepository = procedureRepository;
        this.technicianService = technicianService;
        this.equipmentService = equipmentService;
        this.procedureService = procedureService;
    }

    @Transactional
    public boolean createServiceOrder(@RequestBody ServiceOrderCreateDTO request){
        if(request == null) return false;
        if(request.technicianId() == null || request.equipmentId() == null || request.procedureIds().isEmpty()) return false;
        List<Procedure> procedureList = new ArrayList<>();
        Double price = 0.0;
        for(Long id : request.procedureIds()){
            Procedure procedure = procedureRepository.findById(id).orElse(null);
            if(procedure != null) {
                procedureList.add(procedure);
                price += procedure.getPrice();
            }
        }
        ServiceOrderStatus serviceOrderStatus = ServiceOrderStatus.ON_HOLD;
        if(request.serviceOrderStatus() != null ){
            serviceOrderStatus = request.serviceOrderStatus();
        }
        ServiceOrder serviceOrder = ServiceOrder.builder()
                .technician(technicianRepository.findById(request.technicianId()).get())
                .equipment(equipmentRepository.findById(request.equipmentId()).get())
                .procedureList(procedureList)
                .price(price)
                .status(serviceOrderStatus)
                .build();

        serviceOrderRepository.save(serviceOrder);
        return true;

    }

    @Transactional
    public List<ServiceOrderGetDTO> getAllServiceOrders(){
        List<ServiceOrder> serviceOrders = serviceOrderRepository.findAll();
        List<ServiceOrderGetDTO> serviceOrderGetDTOs = new ArrayList<>();
        for(ServiceOrder serviceOrder : serviceOrders){
            serviceOrderGetDTOs.add(convertToGetDTO(serviceOrder));
        }
        return serviceOrderGetDTOs;
    }

    @Transactional
    public boolean updateServiceOrder(@RequestBody ServiceOrderUpdateDTO request){
        // Verifica condições de falha e instancia o objeto a ser editado
        if(request == null) return false;
        if(request.id() == null) return false;
        if(!serviceOrderRepository.existsById(request.id())) return false;
        ServiceOrder serviceOrder = serviceOrderRepository.findById(request.id()).orElse(null);



        // Atualiza lista de procedimentos se não for nula ou vazia
        if(request.procedureIds() != null && !request.procedureIds().isEmpty()) {
            Double price = 0.0;
            List<Procedure> procedureList = new ArrayList<>();
            for(Long id : request.procedureIds()){
                Procedure procedure = procedureRepository.findById(id).orElse(null);
                if(procedure != null) {
                    procedureList.add(procedure);
                    price += procedure.getPrice();
                }
            }
            serviceOrder.setProcedureList(procedureList);
            serviceOrder.setPrice(price);
        }

        if(request.technicianId() != null && technicianRepository.existsById(request.technicianId())) serviceOrder.setTechnician(technicianRepository.findById(request.technicianId()).get());
        if(request.equipmentId() != null && equipmentRepository.existsById(request.equipmentId()))  serviceOrder.setEquipment(equipmentRepository.findById(request.equipmentId()).get());
        if(request.serviceOrderStatus() != null)  serviceOrder.setStatus(request.serviceOrderStatus());

        serviceOrderRepository.save(serviceOrder);
        return true;
    }

    @Transactional
    public boolean deleteServiceOrder(@RequestBody Long id){
        if(id == null) return false;
        if(serviceOrderRepository.existsById(id)) {
            serviceOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public ServiceOrderGetDTO convertToGetDTO(ServiceOrder serviceOrder) {
        List<ProcedureGetDTO> procedures = new ArrayList<>();
        for(Procedure procedure: serviceOrder.getProcedureList()){
            procedures.add(procedureService.convertToGetDTO(procedure));
        }
        return new  ServiceOrderGetDTO(
                serviceOrder.getId(),
                serviceOrder.getPrice(),
                technicianService.convertToGetDTO(serviceOrder.getTechnician()),
                equipmentService.convertToGetDTO(serviceOrder.getEquipment()),
                procedures,
                serviceOrder.getStatus()
        );
    }




}
