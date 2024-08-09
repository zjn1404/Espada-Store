package com.nqt.spring_boot_espada_store;

import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.entity.VerifyCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Utils {

    @NonFinal
    @Value("${verify-code.valid-duration}")
    int VERIFY_CODE_VALID_DURATION;

    JavaMailSender mailSender;

    public VerifyCode generateVerifyCode(User user) {


        Date expiryDate = new Date(Instant.now().plus(VERIFY_CODE_VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli());

        return VerifyCode.builder()
                .verifyCode(UUID.randomUUID().toString())
                .user(user)
                .expiryDate(expiryDate)
                .build();
    }

    public void sendVerificationEmail(String verifyCode, User user)
            throws MessagingException, UnsupportedEncodingException {
        final String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

        String subject = "Please verify your registration";
        String sender = "Espada Team";
        String mailContent = "<p>Dear " + user.getLastName() + " " + user.getFirstName() + ",</p>";
        mailContent += "<p> Please click the link below to verify your registration.</p>";

        String verifyUrl = baseUrl + "/verify?code=" + verifyCode + "&user=" + user.getId();
        mailContent += "<h3><a href=\""+ verifyUrl +"\">VERIFY</a></h3>";
        mailContent += "<p> Thank you! <br> The Espada Team <p>";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom("nqt460@gmail.com", sender);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);

        mailSender.send(message);
    }

}
