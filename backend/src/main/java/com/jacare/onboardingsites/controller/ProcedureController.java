package com.jacare.onboardingsites.controller;

import com.jacare.onboardingsites.dto.Customer.CustomerCreateDTO;
import com.jacare.onboardingsites.dto.Procedure.ProcedureCreateDTO;
import com.jacare.onboardingsites.dto.Procedure.ProcedureGetDTO;
import com.jacare.onboardingsites.dto.Procedure.ProcedureUpdateDTO;
import com.jacare.onboardingsites.service.ProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/procedure")
@CrossOrigin("*")
public class ProcedureController {
    private final ProcedureService procedureService;

    @Autowired
    public ProcedureController(ProcedureService procedureService){
        this.procedureService = procedureService;
    }

    @GetMapping("/select-all")
    public ResponseEntity<List<ProcedureGetDTO>> getAllProcedures(){
        List<ProcedureGetDTO> response = procedureService.getAllProcedures();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProcedure(@RequestBody ProcedureCreateDTO procedureCreateDTO){
        boolean response = procedureService.createProcedure(procedureCreateDTO);
        if(response){
            return ResponseEntity.status(HttpStatus.CREATED).body("Procedures created");
        }
        return ResponseEntity.badRequest().body("Procedure could not created");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProcedure(@RequestBody ProcedureUpdateDTO procedureUpdateDTO){
        boolean response = procedureService.updateProcedure(procedureUpdateDTO);
        if(response){
            return ResponseEntity.status(HttpStatus.OK).body("Procedures updated");
        }
        return ResponseEntity.badRequest().body("Procedure could not updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProcedure(@PathVariable("id") long id){
        boolean response = procedureService.deleteProcedure(id);
        if(response){
            return ResponseEntity.status(HttpStatus.OK).body("Procedures deleted");
        }
        return ResponseEntity.badRequest().body("Procedure could not deleted");
    }



}
