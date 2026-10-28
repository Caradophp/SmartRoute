package com.faesa.smartRoute.model;

import com.faesa.smartRoute.model.enums.AmbulanceStaus;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

import static com.faesa.smartRoute.model.enums.AmbulanceStaus.WAITING;
import static jakarta.persistence.EnumType.STRING;

@Entity(name = "Ambulance")
@Table(name = "ambulance", uniqueConstraints = { @UniqueConstraint(name = "unique_placa", columnNames = "placa") })
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Ambulance extends com.faesa.smartRoute.model.Entity {

    @Column(name = "placa", nullable = false)
    private String palca;

    @Column(name = "status", nullable = false)
    @Enumerated(STRING)
    private AmbulanceStaus status;

    @OneToOne
    @JoinColumn(name = "driver_id", foreignKey = @ForeignKey(name = "fk_ambulance_driver"))
    private Driver driver;

    @PrePersist
    private void setDefaultValues() {
        if (Objects.isNull(status)) {
            status = WAITING;
        }
    }

}
