package com.nqt.spring_boot_espada_store.service.verifycode;

import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface VerifyCodeService {

    void verify(String code, String userId) throws MessagingException, UnsupportedEncodingException;

}
