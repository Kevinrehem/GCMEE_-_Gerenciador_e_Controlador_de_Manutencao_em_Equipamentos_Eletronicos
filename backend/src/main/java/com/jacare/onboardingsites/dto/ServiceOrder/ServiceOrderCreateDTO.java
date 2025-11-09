package com.jacare.onboardingsites.dto.ServiceOrder;

import com.jacare.onboardingsites.model.enums.ServiceOrderStatus;

import java.util.List;

public record ServiceOrderCreateDTO(Long technicianId, Long equipmentId, List<Long> procedureIds, ServiceOrderStatus serviceOrderStatus) {
}
