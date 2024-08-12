package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
