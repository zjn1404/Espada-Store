package com.nqt.spring_boot_espada_store.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqt.spring_boot_espada_store.entity.VerifyCode;

@Repository
public interface VerifyCodeRepository extends JpaRepository<VerifyCode, String> {

    Optional<VerifyCode> findByVerifyCodeAndUserId(String verifyCode, String userId);

    boolean existsByUserId(String userId);
}
