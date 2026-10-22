package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.AppUserDto;
import com.faesa.smartRoute.dto.ChangePassDto;
import com.faesa.smartRoute.dto.UserRequestDto;
import com.faesa.smartRoute.exceptions.BusinessException;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.AppUser;
import com.faesa.smartRoute.model.Role;
import com.faesa.smartRoute.model.User;
import com.faesa.smartRoute.repository.AppUserRepository;
import com.faesa.smartRoute.repository.RoleRepository;
import com.faesa.smartRoute.repository.UserRepository;
import com.faesa.smartRoute.util.Encrypter;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService, Aplicativo {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private Encrypter encrypter;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(RecordNotFoundException::new);
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

    public void cadastrarUsuario(UserRequestDto userRequestDto) {

        User user = new User();
        user.setNome(userRequestDto.nome());
        user.setEmail(userRequestDto.email());
        user.setCpf(userRequestDto.cpf());

        String passwordTemp = createUserPasswordToFirstAccess(user.getEmail());

        user.setSenha(encrypter.encriptar(passwordTemp));
        user.setRole(roleRepository.findByNomePerfil(userRequestDto.perfil().nomePerfil()).orElseThrow());
        User saved = appUserRepository.save(user);
        sendEmailToFirstAccess(saved, passwordTemp);
    }

    @Override
    @Transactional
    public void trocarSenha(ChangePassDto changePassDto) {
        String email = changePassDto.email();
        String senha = changePassDto.senha();
        String confirmarSenha = changePassDto.confirmarSenha();

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            throw new RecordNotFoundException("E-mail não encontrado");
        }

        if (!senha.equals(confirmarSenha)) {
            throw new BusinessException("As senha informadas não conferem");
        }

        User user = userOptional.get();
        user.setSenha(encrypter.encriptar(senha));
        userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public java.util.List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(java.util.UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Usuário não encontrado com o ID: " + id));
    }

    @Transactional
    public User update(java.util.UUID id, UserRequestDto userDetails) {
        User user = findById(id);

        user.setNome(userDetails.nome());
        user.setEmail(userDetails.email());
        user.setCpf(userDetails.cpf());


        if (!checkIfCanChangeRole(user, userDetails.perfil().nomePerfil())) {
            throw new BusinessException("Usuário não pode alterar seu próprio perfil");
        }

        Optional<Role> optionalRole = roleRepository.findByNomePerfil(userDetails.perfil().nomePerfil());

        Role role = optionalRole.orElseThrow(RecordNotFoundException::new);
        user.setRole(role);

        return userRepository.save(user);
    }

    @Transactional
    public void delete(java.util.UUID id) {
        if (!userRepository.existsById(id)) {
            throw new RecordNotFoundException("Usuário não encontrado para exclusão");
        }
        userRepository.deleteById(id);
    }

    public List<User> search(String param) {
        if (param.isBlank()) {
            return this.findAll();
        }

        return userRepository.search(param);
    }

    private void sendEmailToFirstAccess(User user, String base64PasswordHash) {
        String to = user.getEmail();
        String subject = "Primeiro acesso ao sistema";
        String content = """
                    Prezado(a), <strong>%s</strong>. Segue abaixo as credenciais para o primeiro acesso ao sistema
                    <br/><br/><br/>
                    Credenciais:
                        <ul>
                            <li>Usuário: %s</li>
                            <li>Senha: %s</li>
                        <ul>
                """.formatted(user.getNome(), user.getEmail(), base64PasswordHash);

        try {
            emailService.sendEmail(subject, content, to);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private String createUserPasswordToFirstAccess(String email) {
        return String.valueOf(UUID
                .nameUUIDFromBytes(
                        email.getBytes(StandardCharsets.UTF_8)
                ));
    }

    private boolean checkIfCanChangeRole(User user, String newNomePerfil) {
        User currentUser = (User) Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        assert currentUser != null;

        if (currentUser.getId().equals(user.getId())) {
            return currentUser.getRole().getNomePerfil().equals(newNomePerfil);
        }

        return true;
    }
}
