package com.faesa.smartRoute.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Encrypter {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String encriptar(String texto) {
        return encoder.encode(texto);
    }

    public boolean verificar(String texto, String hash) {
        return encoder.matches(texto, hash);
    }

}
