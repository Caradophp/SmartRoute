package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.AppUserDto;
import com.faesa.smartRoute.exceptions.BusinessException;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.AppUser;
import com.faesa.smartRoute.model.User;
import com.faesa.smartRoute.repository.AppUserRepository;
import com.faesa.smartRoute.repository.RoleRepository;
import com.faesa.smartRoute.repository.UserRepository;
import com.faesa.smartRoute.util.Encrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService, Aplicativo {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private Encrypter encrypter;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        return user.orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public void cadastrarUsuario(AppUserDto appUserDto) {

        if (!appUserDto.email().equals(appUserDto.confirmarEmail())) {
            throw new BusinessException("Os e-mails informados devem ser iguais");
        }

        if (!appUserDto.senha().equals(appUserDto.confirmarSenha())) {
            throw new BusinessException("As senha informadas devem ser iguais");
        }

        AppUser appUser = new AppUser();
        appUser.setNome(appUserDto.nome());
        appUser.setEmail(appUserDto.email());
        appUser.setCpf(appUserDto.cpf());
        appUser.setTelefone(appUserDto.telefone());
        appUser.setSenha(encrypter.encriptar(appUserDto.senha()));
        appUser.setRole(roleRepository.findDefaultRole());
        appUserRepository.save(appUser);
    }
}
