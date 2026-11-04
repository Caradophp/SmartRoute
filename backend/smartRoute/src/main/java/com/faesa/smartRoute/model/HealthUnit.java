package com.faesa.smartRoute.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "HealthUnit")
@Table(name = "health_unit")
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

}
