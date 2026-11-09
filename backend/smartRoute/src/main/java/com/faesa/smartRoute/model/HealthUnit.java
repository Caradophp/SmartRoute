package com.faesa.smartRoute.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
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

    // TODO: Mudar para um enum
    @Column(name = "tipo", nullable = false)
    private String tipo;

    @Column(name = "longitude", nullable = false)
    private BigDecimal longitude;

    @Column(name = "latitude", nullable = false)
    private BigDecimal latitude;

}
