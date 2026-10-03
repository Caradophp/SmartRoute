package com.faesa.smartRoute.model;

import com.faesa.smartRoute.model.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends com.faesa.smartRoute.model.Entity implements UserDetails {

    @Column(length = 50)
    private String nome;

    @Column(length = 50)
    private String email;

    @Column(length = 64)
    private String senha;

    @Column(length = 11)
    private long cpf;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return email;
    }

    @Override
    public String getUsername() {
        return senha;
    }
}
