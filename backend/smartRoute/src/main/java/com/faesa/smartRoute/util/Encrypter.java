package com.faesa.smartRoute.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encrypter {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final String PEPPER = "SR_SECURE_PEPPER_2026_!@#"; // In production, this should be in an env var

    public String encriptar(String texto) {
        return encoder.encode(texto + PEPPER);
    }

    public boolean verificar(String texto, String hash) {
        return encoder.matches(texto + PEPPER, hash);
    }

}
