package com.faesa.smartRoute.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "emergencias")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Emergency extends com.faesa.smartRoute.model.Entity {

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @JoinColumn(name = "id_app_user")
    @ManyToOne
    private AppUser appUser;

    @Column(name = "attended", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean attended;

    @JoinColumn(name = "ambulance_id")
    @OneToMany
    private Set<Ambulance> ambulance;

}
