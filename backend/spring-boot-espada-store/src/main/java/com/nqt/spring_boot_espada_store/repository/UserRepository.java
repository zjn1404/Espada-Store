package com.nqt.spring_boot_espada_store.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqt.spring_boot_espada_store.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsUserByUsername(String username);

    Optional<User> findByUsername(String username);

    List<User> findAllByCustomerDetail_RegisterToGetMail(boolean customerDetail_registerToGetMail);
}
