package com.jacare.onboardingsites.controller;

import com.jacare.onboardingsites.dto.ServiceOrder.ServiceOrderCreateDTO;
import com.jacare.onboardingsites.dto.ServiceOrder.ServiceOrderGetDTO;
import com.jacare.onboardingsites.dto.ServiceOrder.ServiceOrderUpdateDTO;
import com.jacare.onboardingsites.service.ServiceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/app/service-order")
public class ServiceOrderController {

    private final ServiceOrderService serviceOrderService;

    @Autowired
    public ServiceOrderController(ServiceOrderService serviceOrderService) {
        this.serviceOrderService = serviceOrderService;
    }

    @GetMapping("/select-all")
    public ResponseEntity<List<ServiceOrderGetDTO>> getAllServiceOrders() {
        List<ServiceOrderGetDTO> serviceOrderGetDTOS = serviceOrderService.getAllServiceOrders();
        return ResponseEntity.ok(serviceOrderGetDTOS);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createServiceOrder(@RequestBody ServiceOrderCreateDTO serviceOrderCreateDTO) {
        boolean response = serviceOrderService.createServiceOrder(serviceOrderCreateDTO);
        if (response) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Service Order Created Successfully");
        }
        return ResponseEntity.badRequest().body("Service Order Creation Failed");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateServiceOrder(@RequestBody ServiceOrderUpdateDTO serviceOrderUpdateDTO) {
        boolean response = serviceOrderService.updateServiceOrder(serviceOrderUpdateDTO);
        if (response) {
            return ResponseEntity.status(HttpStatus.OK).body("Service Order Updated Successfully");
        }
        return ResponseEntity.badRequest().body("Service Order Update Failed");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteServiceOrder(@PathVariable Long id) {
        boolean response = serviceOrderService.deleteServiceOrder(id);
        if (response) {
            return ResponseEntity.status(HttpStatus.OK).body("Service Order Deleted Successfully");
        }
        return ResponseEntity.badRequest().body("Service Order Deleted Failed");
    }

}
