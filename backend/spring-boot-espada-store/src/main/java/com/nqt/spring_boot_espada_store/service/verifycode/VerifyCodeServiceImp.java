package com.nqt.spring_boot_espada_store.service.verifycode;

import com.nqt.spring_boot_espada_store.utils.Utils;
import com.nqt.spring_boot_espada_store.entity.User;
import com.nqt.spring_boot_espada_store.entity.VerifyCode;
import com.nqt.spring_boot_espada_store.exception.AppException;
import com.nqt.spring_boot_espada_store.exception.ErrorCode;
import com.nqt.spring_boot_espada_store.repository.UserRepository;
import com.nqt.spring_boot_espada_store.repository.VerifyCodeRepository;
import jakarta.mail.MessagingException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VerifyCodeServiceImp implements VerifyCodeService{

    VerifyCodeRepository verifyCodeRepository;
    UserRepository userRepository;
    Utils utils;

    @Override
    public void verify(String code, String userId) throws MessagingException, UnsupportedEncodingException {
        VerifyCode verifyCode = verifyCodeRepository.findByVerifyCodeAndUserId(code, userId)
                .orElseThrow(() -> new AppException(ErrorCode.VERIFY_CODE_INCORRECT));

        if (verifyCode.getExpiryDate().before(new Date())) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

            verifyCodeRepository.deleteById(verifyCode.getVerifyCode());
            VerifyCode regenVerifyCode = utils.generateVerifyCode(user);
            utils.sendVerificationEmail(regenVerifyCode.getVerifyCode(), user);
            verifyCodeRepository.save(regenVerifyCode);

            throw new AppException(ErrorCode.VERIFY_CODE_EXPIRED);
        }

        verifyCodeRepository.delete(verifyCode);
    }
}
