package com.faesa.smartRoute.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Check;

@Entity(name = "Driver")
@Table(name = "drivers")
@Check(constraints = "driver_license_level IN ('A', 'B', 'C', 'AB', 'BC', 'ABC')")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Driver extends com.faesa.smartRoute.model.Entity {

    @Column(name = "driver_license_level", nullable = false)
    private String driverLicenceLevel;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_driver_user"))
    private User driver;
}
