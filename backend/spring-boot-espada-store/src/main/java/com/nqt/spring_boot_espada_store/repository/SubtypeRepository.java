package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.entity.Subtype;
import com.nqt.spring_boot_espada_store.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtypeRepository extends JpaRepository<Subtype, String> {

    List<Subtype> findAllByType(Type type);

}
