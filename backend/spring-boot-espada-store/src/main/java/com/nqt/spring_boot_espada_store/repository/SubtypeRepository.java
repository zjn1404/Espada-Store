package com.nqt.spring_boot_espada_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqt.spring_boot_espada_store.entity.Subtype;
import com.nqt.spring_boot_espada_store.entity.Type;

@Repository
public interface SubtypeRepository extends JpaRepository<Subtype, String> {

    List<Subtype> findAllByType(Type type);
}
