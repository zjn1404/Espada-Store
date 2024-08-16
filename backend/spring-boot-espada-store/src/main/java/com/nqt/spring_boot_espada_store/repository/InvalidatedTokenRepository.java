package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    boolean existsByRfId(String rfId);

    void deleteAllByExpiryTimeBefore(Date date);
}
