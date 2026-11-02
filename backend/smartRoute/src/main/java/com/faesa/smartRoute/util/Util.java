package com.faesa.smartRoute.util;

import com.faesa.smartRoute.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;

@Component
public class Util {

    private static String algorithm;

    private static String secretKey;

    private static String baseUrl;

    public static User getCurrentUser() {
        return (User) Objects.requireNonNull(SecurityContextHolder
                .getContext()
                .getAuthentication())
                .getPrincipal();
    }

    public static String getExpiringLink(User user) {
        long expiresAt = Instant.now().getEpochSecond() + 200000;
        String payload = "user=" + user.getId() + "&expires=" + expiresAt;
        String signature = calculateHmac(payload, secretKey);

        String encodedSignature = Base64.getUrlEncoder().withoutPadding().encodeToString(signature.getBytes());

        return baseUrl + "?" + payload + "&sig=" + encodedSignature;
    }

    public static boolean isLinkValid(String userId, long expiresAt, String incomingSignature) {
        if (Instant.now().getEpochSecond() > expiresAt) {
            return false;
        }

        String payload = "user=" + userId + "&expires=" + expiresAt;
        String expectedSignature = calculateHmac(payload, secretKey);

        byte[] decodedBytes = Base64.getUrlDecoder().decode(incomingSignature);
        String actualSignature = new String(decodedBytes, StandardCharsets.UTF_8);

        actualSignature += "=";

        return MessageDigest.isEqual(expectedSignature.getBytes(StandardCharsets.UTF_8), actualSignature.getBytes(StandardCharsets.UTF_8));
    }

    private static String calculateHmac(String data, String key) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), algorithm);
            Mac mac = Mac.getInstance(algorithm);
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hmacBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate HMAC signature: " + e.getMessage());
        }
    }

    @Value("${config.link.algorithm}")
    public void setAlgorithm(String algorithm) {
        Util.algorithm = algorithm;
    }

    @Value("${config.link.secret-key}")
    public void setSecretKey(String secretKey) {
        Util.secretKey = secretKey;
    }

    @Value("${config.link.base-url}")
    public void setBaseUrl(String baseUrl) {
        Util.baseUrl = baseUrl;
    }
}
