package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.entity.InvalidatedToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {
    boolean existsByRfId(String rfId);
}
