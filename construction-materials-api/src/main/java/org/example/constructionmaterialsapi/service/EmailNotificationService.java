package org.example.constructionmaterialsapi.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailNotificationService {

    JavaMailSender javaMailSender;

    public void sendLoginSuccessEmail(String userEmail) {
        log.info("Asinxron email gonderme prosesi basladi. Thread: {}", Thread.currentThread().getName());

        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userEmail);
            mailMessage.setSubject("Construction Materials API - Ugurlu giris");
            mailMessage.setText("Hormetli istifadeci hesabiniza ugurla giris olundu. Eger bu siz deyilsinizse derhal sifrenizi deyismeyinizi tovsiyye edirik");

            javaMailSender.send(mailMessage);
            log.info("E-poct ugurla [{}] unvanina gonderildi.", userEmail);
        } catch (Exception ex) {
            log.error("E-poct gonderilerken xeta bas verdi", userEmail, ex.getMessage());
        }
    }
}
