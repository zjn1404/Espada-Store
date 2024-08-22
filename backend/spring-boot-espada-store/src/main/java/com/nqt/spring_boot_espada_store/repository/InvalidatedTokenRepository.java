package com.nqt.spring_boot_espada_store.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nqt.spring_boot_espada_store.entity.InvalidatedToken;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    boolean existsByRfId(String rfId);

    void deleteAllByExpiryTimeBefore(Date date);
}
