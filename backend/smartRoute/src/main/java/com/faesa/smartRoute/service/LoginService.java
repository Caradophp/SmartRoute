package com.faesa.smartRoute.service;

import com.faesa.smartRoute.exceptions.BusinessException;
import com.faesa.smartRoute.exceptions.RecordNotFoundException;
import com.faesa.smartRoute.util.Jwt;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Jwt jwt;

    public Map<String, String> login(String email, String senha) {
        Map<String, String> result = new HashMap<>();
        UserDetails userDetails = userService.loadUserByUsername(email);

        if (!passwordEncoder.matches(senha, userDetails.getPassword())) {
            throw new BusinessException("Senha inválida");
        }

        String token = jwt.generateToken(email);
        result.put("token", token);
        return result;
    }

    public void sendEmail(String email) {

        boolean userExists = userService.existsByEmail(email);

        if (!userExists) {
            throw new RecordNotFoundException("E-mail informado não está cadastrado no sistema");
        }

        String subject = "Recuperação de senha";
        int code = (int) (Math.random() * 100000 + 1);
        String body = """
                Prezado usuário, segue abaixo o código para recuperação de senha
                do sistema. Caso não tenha sido você que solicitou, por favor ignore esse e-mail.
                
                Código: %s
                
                Esse é um e-mail automático. Por favor não responda
                """.formatted(code);

        try {
            emailService.sendEmail(subject, body, email);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de recuperação de senha. Contate o suporte técnico");
        }
    }

}
