package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.entity.VerifyCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerifyCodeRepository extends JpaRepository<VerifyCode, String> {

    Optional<VerifyCode> findByVerifyCodeAndUserId(String verifyCode, String userId);

    boolean existsByUserId(String userId);

}
