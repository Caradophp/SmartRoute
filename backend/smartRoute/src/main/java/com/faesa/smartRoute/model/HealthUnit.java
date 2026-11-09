package com.faesa.smartRoute.model;

import com.faesa.smartRoute.model.enums.HealthUnitType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity(name = "HealthUnit")
@Table(name = "health_unit", uniqueConstraints = { @UniqueConstraint(name = "unique_position", columnNames = { "longitude", "latitude" })})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthUnit extends com.faesa.smartRoute.model.Entity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "tipo", nullable = false)
    @Enumerated(EnumType.STRING)
    private HealthUnitType tipo;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

}
