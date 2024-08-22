package com.nqt.spring_boot_espada_store.service.verifycode;

import java.io.UnsupportedEncodingException;

import jakarta.mail.MessagingException;

public interface VerifyCodeService {

    void verify(String code, String userId) throws MessagingException, UnsupportedEncodingException;
}
