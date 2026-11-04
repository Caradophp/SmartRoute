package com.faesa.smartRoute.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "HealtyService")
@Table(name = "health_service")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthService extends com.faesa.smartRoute.model.Entity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    private String descricao;

}
