package com.faesa.smartRoute.service;

import com.faesa.smartRoute.exceptions.BusinessException;
import com.faesa.smartRoute.util.Jwt;
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
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Jwt jwt;

    public Map<String, String> login(String email, String senha) {
        Map<String, String> result = new HashMap<>();
        UserDetails userDetails = userService.loadUserByUsername(email);

        if (passwordEncoder.matches(senha, userDetails.getPassword())) {
            throw new BusinessException("Senha inválida");
        }

        String token = jwt.generateToken(email);
        result.put("token", token);
        return result;
    }

}
