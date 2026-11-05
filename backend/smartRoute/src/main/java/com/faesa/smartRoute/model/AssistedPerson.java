package com.faesa.smartRoute.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity(name = "AssistendPerson")
@Table(name = "assisted_person")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssistedPerson extends com.faesa.smartRoute.model.Entity {

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false, columnDefinition = "DATE")
    private LocalDate dataNascimento;

    @Column(name = "telefone", nullable = false, length = 11)
    private String telefone;

    @OneToOne
    @JoinColumn(name = "adress_id", nullable = false)
    private Adress adress;

}
