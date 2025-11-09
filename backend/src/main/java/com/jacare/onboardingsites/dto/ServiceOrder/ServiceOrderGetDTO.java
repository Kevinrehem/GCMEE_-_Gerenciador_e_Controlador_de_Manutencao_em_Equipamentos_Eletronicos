package com.jacare.onboardingsites.dto.ServiceOrder;

import com.jacare.onboardingsites.dto.Equipment.EquipmentGetDTO;
import com.jacare.onboardingsites.dto.Procedure.ProcedureGetDTO;
import com.jacare.onboardingsites.dto.Technician.TechnicianGetDTO;
import com.jacare.onboardingsites.model.enums.ServiceOrderStatus;

import java.util.List;

public record ServiceOrderGetDTO(Long id, Double price,TechnicianGetDTO technician, EquipmentGetDTO equipment, List<ProcedureGetDTO> procedures, ServiceOrderStatus serviceOrderStatus) {
}
