package com.faesa.smartRoute.model;

import com.faesa.smartRoute.model.enums.TerritoryType;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Territory")
@Table(name = "territorio")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Territory extends com.faesa.smartRoute.model.Entity {

    @Column(name = "nome_territorio", nullable = false)
    private String nomeTerritorio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_territorio", nullable = false)
    private TerritoryType tipoTerritorio;

}
