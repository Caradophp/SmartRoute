package com.faesa.smartRoute.dto;

public record EmergencyDispachDto(
        Double latitude,
        Double longitude,
        String username,
        String useremail,
        String userphonenumber
) {
}
