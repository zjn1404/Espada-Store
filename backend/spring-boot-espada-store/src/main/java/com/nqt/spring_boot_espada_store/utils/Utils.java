package com.nqt.spring_boot_espada_store.utils;

import com.nqt.spring_boot_espada_store.entity.Product;
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

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Utils {

    @NonFinal
    @Value("${verify-code.valid-duration}")
    int VERIFY_CODE_VALID_DURATION;

    @NonFinal
    @Value("${client.home-page}")
    String CLIENT_HOME_PAGE;

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

        MimeMessage message = configMessage(user, sender, subject, mailContent);

        mailSender.send(message);
    }

    public void sendNewProductNotification(User user, Product product) throws MessagingException, UnsupportedEncodingException {
        String subject = "New Product Notification";
        String sender = "Espada Team";

        String dayOfWeek = new SimpleDateFormat("EEEE", Locale.ENGLISH).format(Calendar.getInstance().getTime());

        String mailContent = "<h2 style=\"color: #4CAF50;\">Say hello to " + product.getName() + " :)</h2>";
        mailContent += "<p>Hey " +  user.getLastName() + " " + user.getFirstName() + ",</p>";
        mailContent += "<p>Happy " + dayOfWeek + "! " + product.getName() + " is here at last, and we wanted to make sure you'd be the first to know.</p>";

        mailContent += "<h3>Product Information</h3>";
        mailContent += "<h4>" + product.getName() + "</h4>";
        mailContent += "<p>Price: $" + product.getPrice() + "</p>";
        mailContent += "<p>Material: " + product.getMaterial() + "</p>";
        mailContent += "<p>Form: " + product.getForm() + "</p>";
        mailContent += "<p>Color: " + product.getColor() + "</p>";
        mailContent += "<p>Gender: " + product.getGender() + "</p>";
        mailContent += "<p>Description: " + product.getDescription() + "</p>";

        mailContent += "<p>You can order the product <a href=\"" + CLIENT_HOME_PAGE + "/product/" + product.getId() + "\" style=\"color: #4CAF50; text-decoration: none;\">here</a>.</p>";
        mailContent += "<p>We look forward to seeing you wearing " + product.getName() + "!</p>";
        mailContent += "<p>Thank you!<br>The Espada Team</p>";

        MimeMessage message = configMessage(user, sender, subject, mailContent);
        mailSender.send(message);
    }

    private MimeMessage configMessage(User user, String sender, String subject, String mailContent) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(message);

        messageHelper.setFrom("nqt460@gmail.com", sender);
        messageHelper.setTo(user.getEmail());
        messageHelper.setSubject(subject);
        messageHelper.setText(mailContent, true);

        return message;
    }
}
