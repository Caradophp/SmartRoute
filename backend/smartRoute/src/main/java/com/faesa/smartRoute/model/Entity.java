package com.faesa.smartRoute.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/*
* Classe genérica que sera a mãe de todas as entidades do projeto
*
* Atributos:
*   - id: Atributo que representa as chaves primárias de todas as tabelas
*   - createdAt: Atributo que quarda em que momento o registro daquela entidade foi criado
*   - updatedAt: Atributo que quarda em que momento o registro daquela entidade foi atualizado
* */

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    private void setCretedAt() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void setUpdatedAt() {
        this.updatedAt = LocalDateTime.now();
    }
}
