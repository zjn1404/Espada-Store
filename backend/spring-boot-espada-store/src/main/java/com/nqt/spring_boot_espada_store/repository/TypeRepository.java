package com.nqt.spring_boot_espada_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqt.spring_boot_espada_store.entity.Type;

@Repository
public interface TypeRepository extends JpaRepository<Type, String> {}
