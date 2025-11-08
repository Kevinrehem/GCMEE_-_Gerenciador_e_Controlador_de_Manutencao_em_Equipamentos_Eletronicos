package com.jacare.onboardingsites.controller;

import com.jacare.onboardingsites.dto.Technician.TechnicianGetDTO;
import com.jacare.onboardingsites.dto.Technician.TechnicianRegisterDTO;
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

    @PostMapping("/register")
    public ResponseEntity<?> registerTechnician(@RequestBody TechnicianRegisterDTO technicianRegisterDTO) {
        boolean result = technicianService.registerTechnician(technicianRegisterDTO);
        if (result) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else{
          return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Technician could not be registered.");
        }
    }
}
