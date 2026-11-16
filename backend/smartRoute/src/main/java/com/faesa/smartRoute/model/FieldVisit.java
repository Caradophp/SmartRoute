package com.faesa.smartRoute.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "FieldVisit")
@Table(name = "field_visit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldVisit extends com.faesa.smartRoute.model.Entity {

    @Column(name = "data_visita", nullable = false, columnDefinition = "DATE")
    private LocalDate dataVisita;

    @Column(name = "observacao", columnDefinition = "TEXT")
    private String observacao;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false)
    private AssistedPerson assistedPerson;

    @OneToOne
    @JoinColumn(name = "route_id", nullable = false)
    private RoutePlan routePlan;

}
