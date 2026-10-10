package com.faesa.smartRoute.model;

import com.faesa.smartRoute.model.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "unique_email", columnNames = {"email"}), @UniqueConstraint(name = "unique_cpf", columnNames = {"cpf"})})
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

    @Column(length = 100)
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
        if (this.role == null) return List.of();
        return List.of(new SimpleGrantedAuthority("ROLE_" + this.role.getNomePerfil()));
    }

    @Override
    public @Nullable String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return email;
    }

}
