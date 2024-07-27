package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {
}
