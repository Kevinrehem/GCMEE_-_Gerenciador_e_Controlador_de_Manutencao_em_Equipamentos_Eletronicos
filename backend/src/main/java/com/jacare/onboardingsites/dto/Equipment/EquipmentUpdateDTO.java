package com.jacare.onboardingsites.dto.Equipment;

import com.jacare.onboardingsites.model.enums.EquipType;

public record EquipmentUpdateDTO(Long id, String name, Long customerId, EquipType equipType) {
}
