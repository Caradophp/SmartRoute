package com.faesa.smartRoute.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "route")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Route extends Entity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String origin;

    @Column(nullable = false)
    private String destination;

    private Double distance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;
}