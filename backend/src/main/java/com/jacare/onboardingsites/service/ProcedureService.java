package com.jacare.onboardingsites.service;

import com.jacare.onboardingsites.dto.Procedure.ProcedureCreateDTO;
import com.jacare.onboardingsites.dto.Procedure.ProcedureGetDTO;
import com.jacare.onboardingsites.dto.Procedure.ProcedureUpdateDTO;
import com.jacare.onboardingsites.model.Procedure;
import com.jacare.onboardingsites.repository.ProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcedureService {
    private final ProcedureRepository procedureRepository;

    @Autowired
    public ProcedureService(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    @Transactional
    public boolean createProcedure(ProcedureCreateDTO request){
        if(request != null){
            Procedure procedure = Procedure.builder()
                    .name(request.name())
                    .price(request.price())
                    .description(request.description())
                    .build();
            procedureRepository.save(procedure);
            return true;
        }
        return false;
    }

    @Transactional
    public List<ProcedureGetDTO> getAllProcedures(){
        List<Procedure> procedures = procedureRepository.findAll();
        List<ProcedureGetDTO> dtos = new ArrayList<>();
        for (Procedure procedure : procedures) {
            dtos.add(convertToGetDTO(procedure));
        }
        return dtos;
    }

    @Transactional
    public boolean updateProcedure(ProcedureUpdateDTO request){
        if(request == null) return false;
        if(request.id() == null) return false;
        if(request.name() == null && request.description() == null && request.price()==null) return false;
        Procedure procedure = procedureRepository.findById(request.id()).orElse(null);
        if(procedure == null) return false;
        if(request.name()!=null && !request.name().isEmpty()) procedure.setName(request.name());
        if(request.description()!=null && !request.description().isEmpty()) procedure.setDescription(request.description());
        if(request.price()!=null)  procedure.setPrice(request.price());
        procedureRepository.save(procedure);
        return true;
    }

    @Transactional
    public boolean deleteProcedure(Long id){
        if(id == null) return false;
        if(procedureRepository.existsById(id)){
            procedureRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProcedureGetDTO convertToGetDTO(Procedure procedure){
        return new  ProcedureGetDTO(
                procedure.getId(),
                procedure.getName(),
                procedure.getDescription(),
                procedure.getPrice()
        );
    }
}
