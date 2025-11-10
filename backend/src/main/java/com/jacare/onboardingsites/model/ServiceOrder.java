package com.jacare.onboardingsites.model;

import com.jacare.onboardingsites.model.enums.ServiceOrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList; // Importe ArrayList
import java.util.List;

@Entity
@Table(name = "service_orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class ServiceOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "service_order_id", nullable = false, updatable = false, unique = true)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_id")
    private Technician technician;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @Column
    private Double price;

    // --- CORREÇÃO AQUI ---
    // Este é o "lado dono" do relacionamento
    @ManyToMany(
            // 1. Fetch LAZY é melhor para performance. Evita carregar dados desnecessários.
            fetch = FetchType.LAZY,
            // 2. Definimos a cascata. PERSIST e MERGE são seguros.
            //    Isso garante que ao salvar uma ServiceOrder, a relação na
            //    tabela de junção também seja salva.
            cascade = { CascadeType.PERSIST, CascadeType.MERGE }
    )
    // 3. ESTA É A ANOTAÇÃO MAIS IMPORTANTE (QUE FALTAVA):
    //    Ela diz ao Hibernate qual tabela de junção usar.
    @JoinTable(
            name = "service_orders_procedure_list", // O nome da sua tabela de junção
            // Coluna que se refere a ESTA classe (ServiceOrder)
            joinColumns = @JoinColumn(name = "service_order_service_order_id"),
            // Coluna que se refere à OUTRA classe (Procedure)
            inverseJoinColumns = @JoinColumn(name = "procedure_list_id")
    )
    @Builder.Default // Adicionado para funcionar com Lombok @Builder
    private List<Procedure> procedureList = new ArrayList<>(); // Inicialize a lista

    @Column
    private ServiceOrderStatus status;
}