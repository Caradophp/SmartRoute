package com.faesa.smartRoute.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "emergencias")
@Getter
@Setter
public class Emergency extends com.faesa.smartRoute.model.Entity {

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @JoinColumn(name = "id_app_user")
    @ManyToOne
    private AppUser appUser;

}
