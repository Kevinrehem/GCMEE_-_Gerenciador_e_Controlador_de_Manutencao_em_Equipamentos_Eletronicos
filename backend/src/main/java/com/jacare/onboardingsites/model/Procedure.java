package com.jacare.onboardingsites.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "procedures")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Procedure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(nullable = false, unique = true,  updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double price;

    // --- CORREÇÃO AQUI ---
    // Este é o lado "espelho" (não-dono).
    @ManyToMany(
            mappedBy = "procedureList", // Correto! Mapeia para o campo em ServiceOrder.
            // 1. REMOVA O CascadeType.ALL. É perigoso no lado mappedBy.
            //    (Se você deletar um "Serviço", não quer deletar todas as "Ordens de Serviço" associadas)
            // 2. Mude o Fetch para LAZY (o padrão) para melhor performance.
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<ServiceOrder> orders = new ArrayList<>();
}