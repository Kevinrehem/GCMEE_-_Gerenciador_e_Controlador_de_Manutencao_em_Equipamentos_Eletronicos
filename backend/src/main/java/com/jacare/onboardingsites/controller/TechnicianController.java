package com.jacare.onboardingsites.controller;

import com.jacare.onboardingsites.dto.Technician.TechnicianGetDTO;
import com.jacare.onboardingsites.dto.Technician.TechnicianCreateDTO;
import com.jacare.onboardingsites.dto.Technician.TechnicianUpdateDTO;
import com.jacare.onboardingsites.model.Technician;
import com.jacare.onboardingsites.service.TechnicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/app/technician")
public class TechnicianController {

    private final TechnicianService technicianService;

    @Autowired
    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @GetMapping("/select-all")
    public ResponseEntity<List<TechnicianGetDTO>> getAllTechnicians() {
        List<TechnicianGetDTO> technicians = technicianService.findAllTechnicians();
        return ResponseEntity.ok(technicians);
    }

    @PostMapping("/create")
    public ResponseEntity<?> registerTechnician(@RequestBody TechnicianCreateDTO technicianRegisterDTO) {
        boolean result = technicianService.registerTechnician(technicianRegisterDTO);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Technician registered successfully");
        } else{
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Technician could not be registered.");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateTechnician(@RequestBody TechnicianUpdateDTO technicianUpdateDTO) {
        boolean result = technicianService.updateTechnician(technicianUpdateDTO);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Technician updated successfully");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Technician could not be updated.");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteTechnician(@PathVariable Long id) {
        boolean result = technicianService.deleteTechnician(id);
        if (result) {
            return ResponseEntity.status(HttpStatus.OK).body("Technician deleted successfully");
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Technician could not be found.");
        }
    }
}
