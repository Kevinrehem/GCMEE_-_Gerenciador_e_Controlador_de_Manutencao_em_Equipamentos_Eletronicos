package com.jacare.onboardingsites;

import com.jacare.onboardingsites.model.*;
import com.jacare.onboardingsites.model.enums.EquipType;
import com.jacare.onboardingsites.model.enums.ServiceOrderStatus;
import com.jacare.onboardingsites.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.ANY)
public class CascadeIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private ServiceOrderRepository serviceOrderRepository;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private TechnicianRepository technicianRepository;

    @Test
    @Transactional
    public void whenDeleteCustomer_thenEquipmentsAndServiceOrdersDeleted() {
        // create and save a Procedure
        Procedure procedure = Procedure.builder()
                .name("Proc A")
                .description("desc")
                .price(10.0)
                .build();
        procedure = procedureRepository.save(procedure);

        // create Technician
        Technician tech = Technician.builder()
                .name("John")
                .build();
        tech = technicianRepository.save(tech);

        // create Customer
        Customer customer = Customer.builder()
                .name("Client X")
                .email("x@x.com")
                .phoneNumber("99999")
                .debt(0.0)
                .build();

        // create Equipment and persist after customer so we can attach it cleanly
        Equipment equipment = Equipment.builder()
                .name("Eq1")
                .type(EquipType.LAPTOP)
                .owner(customer) // set owner reference
                .build();

        // persist customer first to get an id
        customer = customerRepository.save(customer);

        // persist equipment and attach to customer collection
        equipment.setOwner(customer);
        equipment = equipmentRepository.save(equipment);
        customer.getEquipments().add(equipment);
        customer = customerRepository.save(customer);

        // create ServiceOrder, persist and attach to equipment and technician
        ServiceOrder so = ServiceOrder.builder()
                .price(50.0)
                .status(ServiceOrderStatus.PAID)
                .build();
        so.setEquipment(equipment);
        so.setTechnician(tech);
        so.setProcedureList(new ArrayList<>(List.of(procedure)));
        so = serviceOrderRepository.save(so);

        // maintain bidirectional lists and save equipment to store the relationship
        equipment.getServiceOrders().add(so);
        equipment = equipmentRepository.save(equipment);

        // sanity checks before delete
        assertEquals(1, customerRepository.count());
        assertEquals(1, equipmentRepository.count());
        assertEquals(1, serviceOrderRepository.count());
        assertEquals(1, procedureRepository.count());

        // delete customer
        customerRepository.deleteById(customer.getId());

        // after delete, equipments and service orders should be gone
        assertEquals(0, equipmentRepository.count(), "Equipments should be deleted via cascade");
        assertEquals(0, serviceOrderRepository.count(), "ServiceOrders should be deleted via cascade");
        // procedure remains (many-to-many), technician remains
        assertEquals(1, procedureRepository.count(), "Procedure should remain (not orphaned)");
        assertEquals(1, technicianRepository.count(), "Technician should remain (not cascade-deleted by customer)");
    }
}
