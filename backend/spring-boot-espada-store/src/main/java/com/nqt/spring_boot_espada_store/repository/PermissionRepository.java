package com.nqt.spring_boot_espada_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqt.spring_boot_espada_store.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
