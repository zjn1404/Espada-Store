package com.nqt.spring_boot_espada_store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqt.spring_boot_espada_store.entity.CartDetail;
import com.nqt.spring_boot_espada_store.entity.CartDetailId;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, CartDetailId> {}
