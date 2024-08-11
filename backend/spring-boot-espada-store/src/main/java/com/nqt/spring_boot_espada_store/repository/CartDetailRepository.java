package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.entity.CartDetail;
import com.nqt.spring_boot_espada_store.entity.CartDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailId> {
}
