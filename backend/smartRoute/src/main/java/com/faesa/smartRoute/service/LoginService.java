package com.faesa.smartRoute.service;

import com.faesa.smartRoute.dto.ChangePassDto;
import com.faesa.smartRoute.dto.response.UserResponseDto;
import com.faesa.smartRoute.exceptions.BusinessException;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.model.Code;
import com.faesa.smartRoute.model.User;
import com.faesa.smartRoute.repository.CodeRepository;
import com.faesa.smartRoute.util.Encrypter;
import com.faesa.smartRoute.util.Jwt;
import com.faesa.smartRoute.util.Util;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.faesa.smartRoute.model.enums.UserStatus.*;

@Service
public class LoginService {

    @Value("${config.link.base-url}")
    private String baseUrl;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private Encrypter encrypter;

    @Autowired
    private Jwt jwt;

    public Map<String, String> login(String email, String senha, boolean isAplicativo) {
        Map<String, String> result = new HashMap<>();
        UserDetails userDetails = userService.loadUserByUsername(email);

        User userToLogin = (User) userDetails;
        boolean aplicativo = userService.isAplicativo(userToLogin);

        if (!isAplicativo && aplicativo) {
            throw new BusinessException("Usuário não encontrado");
        }

        if (ACTIVE.equals(userToLogin.getStatus())) {
            if (!encrypter.verificar(senha, userDetails.getPassword())) {
                Number attemptLoginNumber = userToLogin.getAttemptLoginNumber();

                if (Objects.nonNull(attemptLoginNumber) && attemptLoginNumber.equals(5)) {
                    userToLogin.setStatus(BLOCKED);
                    userService.save(userToLogin);
                    handleBlockedUser(userToLogin);
                    throw new BusinessException("Usuário está bloqueado. Enviamos um e-mail com informações de como desbloquear");
                } else {
                    int i = Objects.isNull(userToLogin.getAttemptLoginNumber()) ? 1 : userToLogin.getAttemptLoginNumber().intValue() + 1;
                    userToLogin.setAttemptLoginNumber(i);
                    userService.save(userToLogin);
                    throw new BusinessException("Senha inválida");
                }
            }

            String token = jwt.generateToken(email);
            result.put("token", token);
            return result;
        } else if (BLOCKED.equals(userToLogin.getStatus())) {
            handleBlockedUser(userToLogin);
            throw new BusinessException("Usuário está bloqueado. Enviamos um e-mail com informações de como desbloquear");
        } else if (DESABLE.equals(userToLogin.getStatus())) {
            handleDesableUser(userToLogin);
            throw new BusinessException("Usuário está detativado. Enviamos um e-mail com informações de como ativar");
        } else {
            throw new BusinessException("Status do usuário é inválido");
        }
    }

    public void sendEmail(String email) {

        boolean userExists = userService.existsByEmail(email);

        if (!userExists) {
            throw new RecordNotFoundException("E-mail informado não está cadastrado no sistema");
        }

        SecureRandom secureRandom = new SecureRandom();
        int code = 100000 + secureRandom.nextInt(900000);
        String subject = "Recuperação de senha";
        String body = """
                Prezado usuário, segue abaixo o código para recuperação de senha
                do sistema. Caso não tenha sido você que solicitou, por favor ignore esse e-mail.

                Código: %s

                Esse é um e-mail automático. Por favor não responda.
                """.formatted(code);

        try {
            emailService.sendEmail(subject, body, email);
            Code code1 = new Code(code, userService.findByEmail(email));
            codeRepository.save(code1);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de recuperação de senha. Contate o suporte técnico");
        }
    }

    public void checkCode(Integer codigo, String email) {
        Code code = codeRepository.findByCodeSendedAndUserEmailAndCreatedAtBetween(
                        codigo,
                        email,
                        LocalDateTime.now().minusHours(1),
                        LocalDateTime.now().plusMinutes(1))
                .orElseThrow(() -> new BusinessException("Código informado é inválido ou expirado"));

        codeRepository.delete(code);
    }

    @Transactional
    public void changePass(ChangePassDto changePassDto) {
        String email = changePassDto.email();
        String senha = changePassDto.senha();
        String confirmarSenha = changePassDto.confirmarSenha();

        User user = userService.findByEmail(email);

        if (user == null) {
            throw new RecordNotFoundException("E-mail não encontrado");
        }

        if (!senha.equals(confirmarSenha)) {
            throw new BusinessException("As senha informadas não conferem");
        }

        if (BLOCKED.equals(user.getStatus()) || DESABLE.equals(user.getStatus())) {
            user.setStatus(ACTIVE);
        }

        user.setSenha(encrypter.encriptar(senha));
        userService.save(user);
    }

    public boolean isValidLink(String link) {
        link = link.replace("?", "");

        int i = link.indexOf("&");
        int sigInx = link.indexOf("sig");
        int expiresIdx = link.indexOf("expires");

        String userId = link.substring(0, i).replace("user=", "");
        String sig = link.substring(sigInx, link.length() - 1).replaceAll("sig=", "");
        String expires = link.substring(expiresIdx, sigInx - 1).replaceAll("expires=", "");

        return Util.isLinkValid(userId, Long.parseLong(expires), sig);
    }

    public UserResponseDto loadUser(UUID id) {
        User byId = userService.findById(id);
        return new UserResponseDto(byId);
    }

    private void handleBlockedUser(User user) {

        if (user == null) {
            throw new BusinessException("Usuário não pode ser nulo");
        }

        String to = user.getEmail();
        String subject = "Desbloquear usuário";
        String body = """
                Prezado <b>%s</b>, informamos que seu usuário no sistema está bloqueado, segue abaixo um link para desbloquear seu usuário
                <br>
                <br>
                Link: %s
                """
                .formatted(user.getNome(), Util.getExpiringLink(user));

        try {
            emailService.sendEmail(subject, body, to);
        } catch (MessagingException exception) {
            throw new RuntimeException("Erro ao enviar e-mail: " + exception.getMessage());
        }

    }

    private void handleDesableUser(User user) {
        if (user == null) {
            throw new BusinessException("Usuário não pode ser nulo");
        }

        String to = user.getEmail();
        String subject = "Desbloquear usuário";
        String body = """
                Prezado <b>%s</b>, informamos que seu usuário no sistema está desativado, segue abaixo um link para desbloquear seu usuário
                <br>
                <br>
                Link: %s
                """
                .formatted(user.getNome(), Util.getExpiringLink(user));

        try {
            emailService.sendEmail(subject, body, to);
        } catch (MessagingException exception) {
            throw new RuntimeException("Erro ao enviar e-mail: " + exception.getMessage());
        }
    }
}
