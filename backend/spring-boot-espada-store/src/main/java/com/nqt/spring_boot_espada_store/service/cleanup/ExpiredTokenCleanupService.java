package com.nqt.spring_boot_espada_store.service.cleanup;

import com.nqt.spring_boot_espada_store.repository.InvalidatedTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ExpiredTokenCleanupService {

    InvalidatedTokenRepository invalidatedTokenRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredTokensEveryMonth() {
        invalidatedTokenRepository.deleteAllByExpiryTimeBefore(new Date());
    }
}
