package com.jacare.onboardingsites.dto.Equipment;

import com.jacare.onboardingsites.model.enums.EquipType;

public record EquipmentCreateDTO(String name, Long customerId, EquipType equipType) {
}
