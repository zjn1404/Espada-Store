package com.nqt.spring_boot_espada_store.service.cleanup;

import java.util.Date;

import jakarta.transaction.Transactional;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.nqt.spring_boot_espada_store.repository.InvalidatedTokenRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpiredTokenCleanupService {

    InvalidatedTokenRepository invalidatedTokenRepository;

    @Scheduled(fixedDelay = 86400000) // ms
    @Transactional
    public void deleteExpiredTokensEveryMonth() {
        invalidatedTokenRepository.deleteAllByExpiryTimeBefore(new Date());
    }
}
