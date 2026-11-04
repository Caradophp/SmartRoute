package com.faesa.smartRoute.model;

import com.faesa.smartRoute.model.enums.RoutePlanStatus;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "RoutePlan")
@Table(name = "route_plan")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoutePlan extends com.faesa.smartRoute.model.Entity {

    @Column(name = "data", nullable = false, columnDefinition = "DATE")
    private LocalDate data;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RoutePlanStatus status;

    @OneToMany
    @JoinColumn(name = "agent_id", nullable = false)
    private HealthAgent healthAgent;

}
