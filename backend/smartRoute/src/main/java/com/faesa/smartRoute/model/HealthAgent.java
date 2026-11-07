package com.faesa.smartRoute.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "HealthAgent")
@Table(name = "health_agent")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HealthAgent extends com.faesa.smartRoute.model.Entity {

    @Column(name = "telefone", nullable = false, length = 11)
    private String telefone;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
