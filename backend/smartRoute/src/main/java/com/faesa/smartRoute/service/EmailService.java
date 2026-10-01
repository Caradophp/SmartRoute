package com.faesa.smartRoute.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@Log4j2
public class EmailService {

    @Value("${config.email.host}")
    private String host;

    @Value("${config.email.port}")
    private String port;

    @Value("${config.email.username}")
    private String username;

    @Value("${config.email.password}")
    private String password;

    public void sendEmail(String title, String msg, String to) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message =  new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(title);
            message.setText(msg);

            Transport.send(message);
        } catch (MessagingException e) {
            log.error("Erro ao enviar email: " + e.getMessage());
            throw e;
        }
    }
}
