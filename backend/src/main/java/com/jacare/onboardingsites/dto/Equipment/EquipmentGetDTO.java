package com.jacare.onboardingsites.dto.Equipment;

import com.jacare.onboardingsites.dto.Customer.CustomerGetDTO;
import com.jacare.onboardingsites.model.enums.EquipType;

public record EquipmentGetDTO(Long id, String name, EquipType equipType, CustomerGetDTO owner) {
}
