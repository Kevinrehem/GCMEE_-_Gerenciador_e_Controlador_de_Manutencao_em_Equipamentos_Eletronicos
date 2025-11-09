package com.jacare.onboardingsites.controller;

import com.jacare.onboardingsites.dto.Equipment.EquipmentCreateDTO;
import com.jacare.onboardingsites.dto.Equipment.EquipmentGetDTO;
import com.jacare.onboardingsites.dto.Equipment.EquipmentUpdateDTO;
import com.jacare.onboardingsites.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("app/equipment")
@CrossOrigin("*")
public class EquipmentController {
    private final EquipmentService equipmentService;

    @Autowired
    public EquipmentController(EquipmentService equipmentService) {
        this.equipmentService = equipmentService;
    }

    @GetMapping("/select-all")
    public ResponseEntity<List<EquipmentGetDTO>> getEquipments(){
        List<EquipmentGetDTO> equipments = equipmentService.findAllEquipments();
        return ResponseEntity.ok(equipments);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEquipment(@RequestBody EquipmentCreateDTO request){
        boolean result = equipmentService.createEquipment(request);
        if(result){
            return ResponseEntity.status(HttpStatus.CREATED).body("Equipment created successfully");
        }else{
            return ResponseEntity.badRequest().body("Failed to create equipment");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateEquipment(@RequestBody EquipmentUpdateDTO request){
        boolean result = equipmentService.updateEquipment(request);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).body("Equipment updated successfully");
        }else {
            return ResponseEntity.badRequest().body("Failed to update equipment");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEquipment(@PathVariable Long id){
        boolean result = equipmentService.deleteEquipment(id);
        if(result){
            return ResponseEntity.status(HttpStatus.OK).body("Equipment deleted successfully");
        }else{
            return ResponseEntity.badRequest().body("Failed to delete equipment");
        }
    }

}
