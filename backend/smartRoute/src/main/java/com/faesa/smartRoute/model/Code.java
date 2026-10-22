package com.faesa.smartRoute.model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Code extends com.faesa.smartRoute.model.Entity {

    @Column(name = "code_sended")
    private Integer codeSended;

    @OneToOne
    @JoinColumn(name = "id_user")
    private User user;

}
