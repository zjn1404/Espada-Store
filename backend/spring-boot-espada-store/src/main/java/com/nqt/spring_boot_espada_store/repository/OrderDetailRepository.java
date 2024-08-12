package com.nqt.spring_boot_espada_store.repository;

import com.nqt.spring_boot_espada_store.entity.OrderDetail;
import com.nqt.spring_boot_espada_store.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
}
